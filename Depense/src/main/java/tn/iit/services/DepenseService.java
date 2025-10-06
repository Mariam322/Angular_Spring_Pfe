package tn.iit.services;

import java.util.ArrayList;
import java.util.List;
import tn.iit.entites.LigneDepense;
import tn.iit.entites.Depense;
import tn.iit.proxy.CompaignController;
import tn.iit.repository.DepenseRepository;
import tn.iit.request.CreateCompaignRequest;
import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.UpdateDepenseRequest;
import tn.iit.response.CompaignResponse;
import tn.iit.response.DepenseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tn.iit.entites.TypeClientFournisseur;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Data
@Service
public class DepenseService implements IDepenseService {
	 private static final Logger logger = LoggerFactory.getLogger(DepenseService.class);
	    @Autowired
	    private DepenseRepository depenseRepository;
  @Autowired
	    private CompaignController compaignController;
@Override

public DepenseResponse createDepense(CreateDepenseRequest createDepenseRequest) {
    // Validate required fields
    if (createDepenseRequest.getIdCompaign() == null) {
        throw new IllegalArgumentException("Campaign ID is required");
    }

    CompaignResponse compaign = compaignController.getById(createDepenseRequest.getIdCompaign());
    if (compaign == null) {
        throw new RuntimeException("Campaign not found with ID: " + createDepenseRequest.getIdCompaign());
    }

    Depense depense = new Depense();
    depense.setNumDepense(createDepenseRequest.getNumDepense());
    depense.setDateDepense(createDepenseRequest.getDateDepense());
    depense.setDatePaiementDepense(createDepenseRequest.getDatePaiementDepense());
    depense.setDateReceptionDepense(createDepenseRequest.getDateReceptionDepense());
    depense.setTvaDepense(createDepenseRequest.getTvaDepense());
    depense.setType(createDepenseRequest.getType());
    depense.setIdCompaign(createDepenseRequest.getIdCompaign());

    // Handle the two cases separately with proper validation
    if (createDepenseRequest.getLignes() != null && !createDepenseRequest.getLignes().isEmpty()) {
        // Case 1: Calculate from line items
        List<LigneDepense> lignes = new ArrayList<>();
        for (LigneDepense ligneReq : createDepenseRequest.getLignes()) {
            if (ligneReq.getPrix() == null) {
                throw new IllegalArgumentException("Line item price cannot be null");
            }
            if (ligneReq.getQuantite() == null) {
                throw new IllegalArgumentException("Line item quantity cannot be null");
            }
            
            LigneDepense ligne = new LigneDepense();
            ligne.setDesignation(ligneReq.getDesignation());
            ligne.setQuantite(ligneReq.getQuantite());
            ligne.setPrix(ligneReq.getPrix());
            ligne.setTotal(ligneReq.getQuantite() * ligneReq.getPrix());
            ligne.setDepense(depense);
            lignes.add(ligne);
        }
        depense.setLignes(lignes);

        double montantHt = lignes.stream().mapToDouble(LigneDepense::getTotal).sum();
        depense.setMontantHtDepense(montantHt);

        double tva = createDepenseRequest.getTvaDepense() != null ? createDepenseRequest.getTvaDepense() : 0;
        double montantTtc = montantHt * (1 + tva / 100);
        depense.setMontantTtcDepense(montantTtc);
        depense.setTotalGeneralDepense(montantTtc);
    } else {
        // Case 2: Use direct amounts
        if (createDepenseRequest.getMontantHtDepense() == null) {
            throw new IllegalArgumentException("HT amount is required when no line items are provided");
        }
        if (createDepenseRequest.getTvaDepense() == null) {
            throw new IllegalArgumentException("VAT rate is required when no line items are provided");
        }
        
        depense.setMontantHtDepense(createDepenseRequest.getMontantHtDepense());
        
        // Calculate TTC if not provided
        if (createDepenseRequest.getMontantTtcDepense() == null) {
            double ttc = createDepenseRequest.getMontantHtDepense() * 
                        (1 + createDepenseRequest.getTvaDepense() / 100);
            depense.setMontantTtcDepense(ttc);
            depense.setTotalGeneralDepense(ttc);
        } else {
            depense.setMontantTtcDepense(createDepenseRequest.getMontantTtcDepense());
            depense.setTotalGeneralDepense(createDepenseRequest.getTotalGeneralDepense());
        }
    }

    depense = depenseRepository.save(depense);
    return new DepenseResponse(depense, compaign);
}
	@Override
	public DepenseResponse updateDepense(Long id, UpdateDepenseRequest updateDepenseRequest) {
		  Depense depense = depenseRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

		        depense.setNumDepense(updateDepenseRequest.getNumDepense());
		        depense.setDateDepense(updateDepenseRequest.getDateDepense());
		        depense.setDatePaiementDepense(updateDepenseRequest.getDatePaiementDepense());
		        depense.setDateReceptionDepense(updateDepenseRequest.getDateReceptionDepense());
		        depense.setMontantHtDepense(updateDepenseRequest.getMontantHtDepense());
		        depense.setTvaDepense(updateDepenseRequest.getTvaDepense());
		        depense.setMontantTtcDepense(updateDepenseRequest.getMontantTtcDepense());
		        depense.setTotalGeneralDepense(updateDepenseRequest.getTotalGeneralDepense());

		        depense = depenseRepository.save(depense);

		        CompaignResponse compaign = compaignController.getById(depense.getId());

		        return new DepenseResponse(depense, compaign);
	}

	@Override
	public void deleteDepense(Long id) {
		 try {
	            logger.info("Suppression de la dépense avec ID : {}", id);
	            Depense depense = depenseRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Dépense non trouvée avec ID : " + id));

	            compaignController.deleteCompain(depense.getId());
	            depenseRepository.delete(depense);
	            logger.info("Dépense supprimée avec succès");
	        } catch (Exception e) {
	            logger.error("Erreur lors de la suppression de la dépense : {}", e.getMessage());
	            throw e;
	        }
	    }


	@Override
	public DepenseResponse getDepenseById(Long id) {
		 Depense depense = depenseRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Dépense non trouvée"));

		        CompaignResponse compaign = compaignController.getById(depense.getId());

		        return new DepenseResponse(depense, compaign);	}

	@Override
	public List<DepenseResponse> getAllDepenses() {
		logger.info("Récupération de toutes les dépenses");

        List<Depense> depenses = depenseRepository.findAll();
        List<DepenseResponse> responses = new ArrayList<>();

        for (Depense depense : depenses) {
            CompaignResponse compaign = null;
            if (depense.getId() != null) {
                compaign = compaignController.getById(depense.getId());
            }
            responses.add(new DepenseResponse(depense, compaign));
        }

        return responses;
    }

}
