package tn.iit.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import tn.iit.entites.Reglement;
import tn.iit.proxy.CompaignController;
import tn.iit.repository.ReglementRepository;
import tn.iit.request.CreateCompaignRequest;
import tn.iit.request.CreateReglementRequest;
import tn.iit.request.UpdateReglementRequest;
import tn.iit.response.CompaignResponse;
import tn.iit.response.FactureResponse;
import tn.iit.response.ReglementResponse;
import tn.iit.entites.TypeClientFournisseur;
@Service
@NoArgsConstructor
@AllArgsConstructor
public class ReglementService implements IReglementService {

    private static final Logger logger = LoggerFactory.getLogger(ReglementService.class);

    @Autowired
    private ReglementRepository reglementRepository;

    @Autowired
    private CompaignController compaignController;

    @Override
public ReglementResponse createReglement(CreateReglementRequest createReglementRequest) {
    // 1. Vérification obligatoire de l'ID campagne
    if (createReglementRequest.getIdCompaign() == null) {
        throw new IllegalArgumentException("L'ID de la campagne est obligatoire");
    }

    // 2. Récupération de la campagne existante
    CompaignResponse compaign = compaignController.getById(createReglementRequest.getIdCompaign());
    if (compaign == null) {
        throw new RuntimeException("Campagne non trouvée avec ID: " + createReglementRequest.getIdCompaign());
    }


        // Création du règlement
        Reglement reglement = new Reglement();
        reglement.setDateReglement(createReglementRequest.getDateReglement());
        reglement.setMontantReglemnt(createReglementRequest.getMontantReglemnt());
        reglement.setModePaiement(createReglementRequest.getModePaiement());
        reglement.setDescription(createReglementRequest.getDescription());
        reglement.setIdCompaign(createReglementRequest.getIdCompaign()); 


        reglement = reglementRepository.save(reglement);

        return new ReglementResponse(reglement,compaign);
    }

    @Override
    public ReglementResponse updateReglement(Long id, UpdateReglementRequest updateReglementRequest) {
        Reglement reglement = reglementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));

        reglement.setDateReglement(updateReglementRequest.getDateReglement());
        reglement.setMontantReglemnt(updateReglementRequest.getMontantReglemnt());
        reglement.setModePaiement(updateReglementRequest.getModePaiement());
        reglement.setDescription(updateReglementRequest.getDescription());
       

        reglement = reglementRepository.save(reglement);

        CompaignResponse compaign = compaignController.getById(reglement.getId());

        return new ReglementResponse(reglement, compaign);
    }
    
    @Override
    public void deleteReglement(Long id) {
        try {
            logger.info("Suppression du règlement avec ID : {}", id);
            Reglement reglement = reglementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));

            compaignController.deleteCompain(reglement.getIdCompaign());
            reglementRepository.delete(reglement);
            logger.info("Règlement avec ID {} supprimé avec succès", id);
        } catch (Exception e) {
        	  logger.error("Échec de la suppression de la facture avec l'ID : {}. Erreur : {}", id, e.getMessage());
              throw e;
          }
    }
    @Override
    public ReglementResponse getReglementById(Long id) {
        Reglement reglement = reglementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        CompaignResponse compaign = compaignController.getById(reglement.getId());

        return new ReglementResponse(reglement, compaign);
    }
    @Override
    public List<ReglementResponse> getAllReglements() {
        logger.info("Début de la récupération de toutes les factures");

        List<Reglement> factures = reglementRepository.findAll();
        List<ReglementResponse> reglementResponses = new ArrayList<>();

        for (Reglement reglement : factures) {
            CompaignResponse compaign = null;
            if (reglement.getId() != null) {
                compaign = compaignController.getById(reglement.getId());
            }
            reglementResponses.add(new ReglementResponse(reglement, compaign));
        }

        return reglementResponses;
    }
    
    @Override
public List<ReglementResponse> getReglementsByType(TypeClientFournisseur type) {
    logger.info("Récupération des règlements par type : {}", type);
    
    // Récupérer les campagnes par type via Feign Client
    List<CompaignResponse> compaigns = compaignController.getCompainsByType(type.toString());
    
    // Récupérer les règlements correspondants
    List<ReglementResponse> reglementResponses = new ArrayList<>();
    
    for (CompaignResponse compaign : compaigns) {
        // Supposons que l'ID de la campagne est lié à l'ID du règlement
        // Vous devrez peut-être adapter cette logique selon votre modèle de données
        reglementRepository.findById(compaign.getId()).ifPresent(reglement -> {
            reglementResponses.add(new ReglementResponse(reglement, compaign));
        });
    }
    
    return reglementResponses;
}
    
    }