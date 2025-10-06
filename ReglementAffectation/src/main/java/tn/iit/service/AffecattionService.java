package tn.iit.service;

import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import tn.iit.entites.Reglement;
import tn.iit.proxy.CompaignController;

import tn.iit.request.CreateCompaignRequest;
import tn.iit.request.CreateReglementRequest;
import tn.iit.request.UpdateReglementRequest;
import tn.iit.response.CompaignResponse;
import tn.iit.response.FactureResponse;
import tn.iit.response.ReglementResponse;
import tn.iit.entites.AffectationType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tn.iit.entites.Affectation;
import tn.iit.proxy.DepenseController;
import tn.iit.proxy.OperationBnacaireController;
import tn.iit.proxy.PieceCommercialController;
import tn.iit.repository.AffectationRepository;
import tn.iit.repository.ReglementRepository;
import tn.iit.request.CreateAffectationRequest;
import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.CreateFactureRequest;
import tn.iit.request.CreateOperationBancaireRequest;
import tn.iit.request.UpdateAffectationRequest;
import tn.iit.response.AffectationResponse;
import tn.iit.response.DepenseResponse;
import tn.iit.response.FactureResponse;
import tn.iit.response.OperationBancaireResponse;
import tn.iit.entites.StatutFacture;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class AffecattionService implements IAffectationService {
    private static final Logger logger = LoggerFactory.getLogger(ReglementService.class);
    @Autowired
    private AffectationRepository affectationRepository;
    @Autowired
    private PieceCommercialController pieceCommercialController;
    @Autowired
    private DepenseController depenseController;
    @Autowired
    private OperationBnacaireController operationBnacaireController;
    @Autowired
    private ReglementRepository reglementRepository;
    
	public AffecattionService(AffectationRepository affectationRepository,
			PieceCommercialController pieceCommercialController, DepenseController depenseController,
			OperationBnacaireController operationBnacaireController) {
		super();
		this.affectationRepository = affectationRepository;
		this.pieceCommercialController = pieceCommercialController;
		this.depenseController = depenseController;
		this.operationBnacaireController = operationBnacaireController;
	}


	@Override
	public AffectationResponse createAffectation(CreateAffectationRequest createAffectationRequest) {
	    // Création de la facture
	    CreateFactureRequest factureRequest = new CreateFactureRequest(
	          createAffectationRequest.getNumeroFacture(),
            createAffectationRequest.getDateFacture(),
            createAffectationRequest.getDatePaiement(),
            createAffectationRequest.getMontantHt(),
            createAffectationRequest.getTva(),
            createAffectationRequest.getMontantTtc(),
            createAffectationRequest.getRemise(),
      
           StatutFacture.valueOf(createAffectationRequest.getStatut()),
             createAffectationRequest.getPdfPath(),
            createAffectationRequest.getTypePieceCommerciale(),
            createAffectationRequest.getReference(), 
            createAffectationRequest.getNom(), 
            createAffectationRequest.getAddress(),
            createAffectationRequest.getEmail(), 
            createAffectationRequest.getTelephone(), 
            createAffectationRequest.getPays(), 
            createAffectationRequest.getVille(), 
            createAffectationRequest.getType(), 
            new Date() 
	    );
	    FactureResponse facture = pieceCommercialController.createFacture(factureRequest);

	    // Création de la dépense
	    CreateDepenseRequest depenseRequest = new CreateDepenseRequest(
	            createAffectationRequest.getNumDepense(),
	            createAffectationRequest.getDateDepense(),
	            createAffectationRequest.getDatePaiementDepense(),
	            createAffectationRequest.getDateReceptionDepense(),
	            createAffectationRequest.getMontantHtDepense(),
	            createAffectationRequest.getTvaDepense(),
	            createAffectationRequest.getMontantTtcDepense(),
	            createAffectationRequest.getTotalGeneralDepense(),
                          createAffectationRequest.getReference(), 
            createAffectationRequest.getNom(), 
            createAffectationRequest.getAddress(),
            createAffectationRequest.getEmail(), 
            createAffectationRequest.getTelephone(), 
            createAffectationRequest.getPays(), 
            createAffectationRequest.getVille(), 
            createAffectationRequest.getType(), 
             new Date() 
	    );
	    DepenseResponse depense = depenseController.createDepense(depenseRequest);

	    // Création de l'opération bancaire
	    CreateOperationBancaireRequest operationRequest = new CreateOperationBancaireRequest(
	            createAffectationRequest.getDateOperation(),
	            createAffectationRequest.getNumCheque(),
	            createAffectationRequest.getDebit(),
	            createAffectationRequest.getCredit(),
	            createAffectationRequest.getSolde()
	    );
	    OperationBancaireResponse operation = operationBnacaireController.createOperation(operationRequest);

	    // Création de l'affectation
	    Affectation affectation = new Affectation();
	    affectation.setMontantAffectaion(createAffectationRequest.getMontantAffectaion());
	    affectation.setDateAffectation(createAffectationRequest.getDateAffectation());
            affectation.setTypeaffect(createAffectationRequest.getTypeaffect());
	    affectation = affectationRepository.save(affectation);

	    return new AffectationResponse(affectation, facture, operation, depense);
	}


	@Override
	public AffectationResponse updateAffectation(Long id, UpdateAffectationRequest updateAffectationRequest) {
	    Affectation affectation = affectationRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Affectation non trouvée"));

	    affectation.setDateAffectation(updateAffectationRequest.getDateAffectation());
	    affectation.setMontantAffectaion(updateAffectationRequest.getMontantAffectaion());

	    affectation = affectationRepository.save(affectation);

	    // Exemple si tu veux récupérer une entité liée (comme facture) avec un ID
	    FactureResponse facture = pieceCommercialController.getById(affectation.getIdPieceCommercial());
DepenseResponse depense =depenseController.getById(affectation.getIdDepense());
OperationBancaireResponse operation =operationBnacaireController.getById(affectation.getIdOperationBancaire());
	    return new AffectationResponse(affectation, facture, operation,depense ); // Complète les autres paramètres si tu veux
	}


	@Override
	public void deleteAffectation(Long id) {
	    try {
	        logger.info("Suppression de l'affectation avec ID : {}", id);

	        Affectation affectation = affectationRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Affectation non trouvée"));


	        affectationRepository.delete(affectation);

	        logger.info("Affectation avec ID {} supprimée avec succès", id);
	    } catch (Exception e) {
	        logger.error("Échec de la suppression de l'affectation avec l'ID : {}. Erreur : {}", id, e.getMessage());
	        throw e;
	    }
	}


	@Override
	public AffectationResponse getAffectationById(Long id) {
	    Affectation affectation = affectationRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Affectation non trouvée"));

	    FactureResponse facture = pieceCommercialController.getById(affectation.getIdPieceCommercial());
	    DepenseResponse depense = depenseController.getById(affectation.getIdDepense());
	    OperationBancaireResponse operation = operationBnacaireController.getById(affectation.getIdOperationBancaire());

	    return new AffectationResponse(affectation, facture, operation,depense );
	}

// Dans AffecattionService.java
@Override
public List<AffectationResponse> getAllAffectations() {
    List<Affectation> affectations = affectationRepository.findAll();
    return affectations.stream()
        .map(affectation -> {
            FactureResponse facture = null;
            DepenseResponse depense = null;
            OperationBancaireResponse operation = null;
            AffectationType typeaffect = null;

            if (affectation.getIdOperationBancaire() != null) {
                operation = operationBnacaireController.getById(affectation.getIdOperationBancaire());
                typeaffect = AffectationType.OPERATION_BANCAIRE;
            } 
            else if (affectation.getIdDepense() != null) {
                depense = depenseController.getById(affectation.getIdDepense());
                typeaffect = AffectationType.DEPENSE;
            }
            else if (affectation.getIdPieceCommercial() != null) {
                facture = pieceCommercialController.getById(affectation.getIdPieceCommercial());
                typeaffect = AffectationType.FACTURE;
            }

            AffectationResponse response = new AffectationResponse(affectation, facture, operation, depense);
            response.setTypeaffect(typeaffect);
            return response;
        })
        .collect(Collectors.toList());
}
 @Override
public AffectationResponse affecterAFacture(Long reglementId, Long factureId, Double montant) {
    Affectation affectation = new Affectation();
    affectation.setMontantAffectaion(montant);
    affectation.setDateAffectation(LocalDate.now());
       affectation.setTypeaffect(AffectationType.FACTURE);
    affectation.setIdPieceCommercial(factureId);
    // Assurez-vous que les autres IDs sont null
    affectation.setIdDepense(null);
    affectation.setIdOperationBancaire(null);
    
    Reglement reglement = reglementRepository.findById(reglementId)
        .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));
    affectation.setReglement(reglement);
    
    affectation = affectationRepository.save(affectation);
    
    FactureResponse facture = pieceCommercialController.getById(factureId);
    return new AffectationResponse(affectation, facture, null, null);
}

@Override
public AffectationResponse affecterAOperationBancaire(Long reglementId, Long operationId, Double montant) {
    Affectation affectation = new Affectation();
    affectation.setMontantAffectaion(montant);
    affectation.setDateAffectation(LocalDate.now());
       affectation.setTypeaffect(AffectationType.OPERATION_BANCAIRE);
    affectation.setIdOperationBancaire(operationId);
    // Assurez-vous que les autres IDs sont null
    affectation.setIdPieceCommercial(null);
    affectation.setIdDepense(null);
    
    Reglement reglement = reglementRepository.findById(reglementId)
        .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));
    affectation.setReglement(reglement);
    
    affectation = affectationRepository.save(affectation);
    
    OperationBancaireResponse operation = operationBnacaireController.getById(operationId);
    return new AffectationResponse(affectation, null, operation, null);
}
    @Override
    public AffectationResponse affecterADepense(Long reglementId, Long depenseId, Double montant) {
        Affectation affectation = new Affectation();
        affectation.setMontantAffectaion(montant);
        affectation.setDateAffectation(LocalDate.now());
               affectation.setTypeaffect(AffectationType.DEPENSE);
        affectation.setIdDepense(depenseId);
        
        Reglement reglement = reglementRepository.findById(reglementId)
            .orElseThrow(() -> new RuntimeException("Règlement non trouvé"));
        affectation.setReglement(reglement);
        
        affectation = affectationRepository.save(affectation);
        
        DepenseResponse depense = depenseController.getById(depenseId);
        return new AffectationResponse(affectation, null, null, depense);
    }

   
    @Override
public List<AffectationResponse> getAffectationsByReglement(Long reglementId) {
    logger.info("Fetching affectations for reglement ID: {}", reglementId);
    
    // Vérifier si le règlement existe
    if (!reglementRepository.existsById(reglementId)) {
        logger.warn("Reglement with ID {} not found", reglementId);
        return Collections.emptyList();
    }
    
    // Récupérer les affectations pour ce règlement
    List<Affectation> affectations = affectationRepository.findByReglementId(reglementId);
    
    // Convertir en AffectationResponse
    return affectations.stream()
        .map(affectation -> {
            FactureResponse facture = affectation.getIdPieceCommercial() != null ? 
                pieceCommercialController.getById(affectation.getIdPieceCommercial()) : null;
                
            DepenseResponse depense = affectation.getIdDepense() != null ? 
                depenseController.getById(affectation.getIdDepense()) : null;
                
            OperationBancaireResponse operation = affectation.getIdOperationBancaire() != null ? 
                operationBnacaireController.getById(affectation.getIdOperationBancaire()) : null;
                
            return new AffectationResponse(affectation, facture, operation, depense);
        })
        .collect(Collectors.toList());
}


}
