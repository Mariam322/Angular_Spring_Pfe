package tn.iit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Collections;
import org.springframework.core.io.ClassPathResource;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import lombok.NoArgsConstructor;
import tn.iit.entites.Facture;
import tn.iit.entites.LignePieceCommerciale;
import tn.iit.proxy.CompaignController;
import tn.iit.publisher.FactureEventPublisher;
import tn.iit.repository.IFacture;
import tn.iit.request.CreateCompaignRequest;
import tn.iit.request.CreateFactureRequest;
import tn.iit.request.UpdateFactureRequest;
import tn.iit.response.CompaignResponse;
import tn.iit.response.FactureResponse;
import org.springframework.context.annotation.Bean;
import java.util.List;
import lombok.RequiredArgsConstructor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.ByteArrayResource;
import tn.iit.entites.StatutFacture;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Base64;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.stream.Collectors;
@Service



public class FactureService implements IFactureService {

    private static final Logger logger = LoggerFactory.getLogger(FactureService.class);
       @Value("${pdf.storage.path}")
    private String pdfStoragePath;

    @Autowired
    private IFacture factureRepository;

    @Autowired
    private CompaignController compaignController;
 @Autowired
    private  StreamBridge streamBridge;

    @Autowired
    private FactureEventPublisher eventPublisher;
  @Autowired
private GoogleDriveService googleDriveService;

    @Override
    public FactureResponse createFacture(CreateFactureRequest createFactureRequest) {
         // 1. Vérifier qu'un ID de campagne est fourni
    if (createFactureRequest.getIdCompaign() == null) {
        throw new IllegalArgumentException("L'ID de la campagne est obligatoire");
    }

    // 2. Récupérer la campagne existante
    CompaignResponse compaign = compaignController.getById(createFactureRequest.getIdCompaign());
    if (compaign == null) {
        throw new RuntimeException("Campagne non trouvée avec ID: " + createFactureRequest.getIdCompaign());
    }

        Facture facture = new Facture();
        facture.setNumeroFacture(createFactureRequest.getNumeroFacture());
        facture.setDateFacture(createFactureRequest.getDateFacture());
        facture.setDatePaiement(createFactureRequest.getDatePaiement());
        facture.setMontantHt(createFactureRequest.getMontantHt());
        facture.setTva(createFactureRequest.getTva());
        facture.setMontantTtc(createFactureRequest.getMontantTtc());
        facture.setRemise(createFactureRequest.getRemise());
        facture.setStatut(createFactureRequest.getStatut());
        facture.setPdfPath(createFactureRequest.getPdfPath());
        facture.setType(createFactureRequest.getTypePieceCommerciale());
        facture.setMontantRestant(createFactureRequest.getMontantRestant());
          facture.setIdCompaign(createFactureRequest.getIdCompaign()); // Lier à la campagne créée
 
        if (createFactureRequest.getLignes() != null) {
            for (LignePieceCommerciale ligneRequest : createFactureRequest.getLignes()) {
                LignePieceCommerciale ligne = new LignePieceCommerciale();
                ligne.setDescription(ligneRequest.getDescription());
                ligne.setQuantite(ligneRequest.getQuantite());
                ligne.setPrixUnitaire(ligneRequest.getPrixUnitaire());
                ligne.setFacture(facture);
                facture.getLignes().add(ligne);
            }

            if (facture.getMontantHt() == null) {
                facture.setMontantHt(
                    facture.getLignes().stream()
                        .mapToDouble(LignePieceCommerciale::getTotal)
                        .sum()
                );
            }

            if (facture.getMontantTtc() == null && facture.getMontantHt() != null && facture.getTva() != null) {
                facture.setMontantTtc(facture.getMontantHt() * (1 + facture.getTva() / 100));
            }
        }

        facture = factureRepository.save(facture);

       try {
            generateAndSendPdf(facture.getId());
        } catch (IOException e) {
            logger.error("Erreur lors de la génération du PDF", e);
        }
        
        return new FactureResponse(facture, compaign);
    }
 public void generateAndSendPdf(Long factureId) throws IOException {
        // 1. Générer le PDF
        byte[] pdfContent = generateStyledPdf(factureId);
        
        // 2. Créer le répertoire de stockage s'il n'existe pas
        Path storagePath = Paths.get(pdfStoragePath);
        Files.createDirectories(storagePath);
        
        // 3. Sauvegarder temporairement le fichier
        String fileName = "facture_" + factureId + ".pdf";
        Path filePath = storagePath.resolve(fileName);
        Files.write(filePath, pdfContent);
        
        // 4. Envoyer l'événement Kafka
        Map<String, Object> event = new HashMap<>();
        event.put("factureId", factureId);
        event.put("fileName", fileName);
        event.put("filePath", filePath.toString());
        event.put("content", Base64.getEncoder().encodeToString(pdfContent));
        event.put("type", "FACTURE");
        
        streamBridge.send("pdf-ready-out-0", event);
    }

    private byte[] generateStyledPdf(Long factureId) throws IOException {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Ajouter le contenu stylisé
            document.add(new Paragraph("FACTURE N°: " + facture.getNumeroFacture()));
            document.add(new Paragraph("Date: " + facture.getDateFacture()));
          
            
            // Ajouter tableau des lignes
            PdfPTable table = new PdfPTable(4);
            table.addCell("Description");
            table.addCell("Quantité");
            table.addCell("Prix Unitaire");
            table.addCell("Total");
            
            for (LignePieceCommerciale ligne : facture.getLignes()) {
                table.addCell(ligne.getDescription());
                table.addCell(String.valueOf(ligne.getQuantite()));
                table.addCell(String.valueOf(ligne.getPrixUnitaire()));
                table.addCell(String.valueOf(ligne.getQuantite() * ligne.getPrixUnitaire()));
            }
            
            document.add(table);
            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new IOException("Erreur génération PDF", e);
        }
    }

    @Override
    public FactureResponse updateFacture(Long id, UpdateFactureRequest updateFactureRequest) {
        Facture facture = factureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture non trouvee"));

        facture.setNumeroFacture(updateFactureRequest.getNumeroFacture());
        facture.setDateFacture(updateFactureRequest.getDateFacture());
        facture.setDatePaiement(updateFactureRequest.getDatePaiement());
        facture.setMontantHt(updateFactureRequest.getMontantHt());
        facture.setTva(updateFactureRequest.getTva());
        facture.setMontantTtc(updateFactureRequest.getMontantTtc());
        facture.setRemise(updateFactureRequest.getRemise());
        facture.setStatut(updateFactureRequest.getStatut());
        facture.setPdfPath(updateFactureRequest.getPdfPath());
        facture.setType(updateFactureRequest.getTypePieceCommerciale());
  facture.setMontantRestant(updateFactureRequest.getMontantRestant());
        facture = factureRepository.save(facture);

        CompaignResponse compaign = compaignController.getById(facture.getId());

        return new FactureResponse(facture, compaign);
    }

    @Override
    public void deleteFacture(Long id) {
        try {
            logger.info("Tentative de suppression de la facture avec l'ID : {}", id);
            Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture non trouvee avec l'ID : " + id));

            compaignController.deleteCompain(facture.getId());
            factureRepository.delete(facture);
            logger.info("Facture avec l'ID {} supprimée avec succes", id);
        } catch (Exception e) {
            logger.error("echec de la suppression de la facture avec l'ID : {}. Erreur : {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public FactureResponse getFactureById(Long id) {
        Facture facture = factureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture non trouvee"));

        CompaignResponse compaign = compaignController.getById(facture.getId());

        return new FactureResponse(facture, compaign);
    }

    @Override
public List<FactureResponse> getAllFactures() {
    logger.info("Début de la récupération de toutes les factures");

    List<Facture> factures = factureRepository.findAll();
    List<FactureResponse> factureResponses = new ArrayList<>();

    for (Facture facture : factures) {
        CompaignResponse compaign = null;
        if (facture.getIdCompaign() != null) {  // Utilisez getIdCompaign() ici
            compaign = compaignController.getById(facture.getIdCompaign());  // Et ici
        }
        factureResponses.add(new FactureResponse(facture, compaign));
    }

    return factureResponses;
}
    public String generateAndStorePdf(Long factureId) throws IOException {
        // 1. Générer le contenu PDF (à adapter)
        byte[] pdfContent = generatePdfContent(factureId);
        
        // 2. Upload vers Google Drive
        String fileName = "facture_" + factureId + ".pdf";
        String driveFileId = googleDriveService.uploadPdfContent(pdfContent, fileName);
        
        // 3. Sauvegarder l'ID Drive dans la base
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
        facture.setDriveFileId(driveFileId);
        factureRepository.save(facture);
        
        return driveFileId;
    }

   public ResponseEntity<Resource> downloadPdf(Long factureId) throws IOException {
    Facture facture = factureRepository.findById(factureId)
            .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    
    if (facture.getDriveFileId() == null) {
        throw new RuntimeException("Aucun PDF associé à cette facture");
    }

    Resource resource = googleDriveService.downloadFile(facture.getDriveFileId());
    
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
            .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
            .header(HttpHeaders.CONTENT_DISPOSITION, 
                   "inline; filename=facture_" + factureId + ".pdf")
            .body(resource);
}
   private byte[] generatePdfContent(Long factureId) throws IOException {
    Facture facture = factureRepository.findById(factureId)
            .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        
        document.open();
        
        // Ajoutez le contenu du PDF
        document.add(new Paragraph("Facture #" + facture.getNumeroFacture()));
        document.add(new Paragraph("Date: " + facture.getDateFacture()));
        document.add(new Paragraph("Montant TTC: " + facture.getMontantTtc()));
        // Ajoutez d'autres détails de la facture...
        
        document.close();
        return baos.toByteArray();
    } catch (DocumentException e) {
        throw new IOException("Erreur génération PDF", e);
    }
}
@Override
public FactureResponse updateStatut(Long id, StatutFacture statut) {
    // 1. Récupérer la facture
    Facture facture = factureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    
    // 2. Mettre à jour le statut
    facture.setStatut(statut);
    facture = factureRepository.save(facture);
    
    // 3. Récupérer les données de campagne associées
    CompaignResponse compaign = compaignController.getById(facture.getId());
    
    // 4. Retourner la réponse combinée
    return new FactureResponse(facture, compaign);
}

@Override
public FactureResponse updateMontantRestant(Long id, Double montant) {
    // 1. Récupérer la facture
    Facture facture = factureRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture non trouvée"));
    
    // 2. Valider et mettre à jour le montant
    if (montant < 0) {
        throw new IllegalArgumentException("Le montant restant ne peut pas être négatif");
    }
    
    facture.setMontantRestant(montant);
    
    // 3. Mettre à jour automatiquement le statut si payé intégralement
    if (montant != null && montant == 0) {
        facture.setStatut(StatutFacture.PAYEE);
    }
    
    facture = factureRepository.save(facture);
    
    // 4. Récupérer les données de campagne
    CompaignResponse compaign = compaignController.getById(facture.getId());
    
    // 5. Retourner la réponse
    return new FactureResponse(facture, compaign);
}

  @Override
public List<FactureResponse> getFacturesByCompanyId(Long idCompaign) {
    List<Facture> factures = factureRepository.findByIdCompaign(idCompaign);
    
    return factures.stream()
            .map(facture -> {
                CompaignResponse compaign = compaignController.getById(facture.getId()); // Utilisez getId()
                return new FactureResponse(facture, compaign);
            })
            .collect(Collectors.toList());
}
}
