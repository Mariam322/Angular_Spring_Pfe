package tn.iit.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.iit.request.CreateAffectationRequest;
import tn.iit.request.UpdateAffectationRequest;
import tn.iit.response.AffectationResponse;
import tn.iit.service.AffecattionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@Tag(name = "Affectation Management", description = "API pour la gestion des affectations")
public class AffectationController {

    private static final Logger logger = LoggerFactory.getLogger(AffectationController.class);

    @Autowired
    private AffecattionService affectationService;
    
    

    public AffectationController(AffecattionService affectationService) {
		super();
		this.affectationService = affectationService;
	}

	@PostMapping("/affectation/create")
  @Operation(summary = "Creer une nouvelle affectation", 
               description = "Permet de creer une affectation avec les informations fournies")
    @ApiResponse(responseCode = "200", description = "Affectation creee avec succes",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public AffectationResponse createAffectation(@RequestBody CreateAffectationRequest request) {
        logger.info("Received request to create affectation: {}", request);
        AffectationResponse response = affectationService.createAffectation(request);
        logger.info("Created affectation for ID: {}", response.getId());
        return response;
    }

    @GetMapping("/affectation/getById/{id}")
    @Operation(summary = "Recuperer une affectation par ID",
               description = "Obtenir les details d'une affectation specifique")
    @ApiResponse(responseCode = "200", description = "Affectation trouvee",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public AffectationResponse getAffectationById(@PathVariable Long id) {
        logger.info("Fetching affectation with ID: {}", id);
        return affectationService.getAffectationById(id);
    }

    @GetMapping("/affectation/getAll")
     @Operation(summary = "Lister toutes les affectations", 
               description = "Obtenir la liste complete des affectations")
    @ApiResponse(responseCode = "200", description = "Liste des affectations recuperee",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public List<AffectationResponse> getAllAffectations() {
        logger.info("Fetching all affectations");
        return affectationService.getAllAffectations();
    }

    @PutMapping("/affectation/update/{id}")
    @Operation(summary = "Modifier une affectation",
               description = "Mettre a jour une affectation existante")
    @ApiResponse(responseCode = "200", description = "Affectation mise a jour avec succes",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public AffectationResponse updateAffectation(@PathVariable Long id, @RequestBody UpdateAffectationRequest request) {
        logger.info("Updating affectation with ID: {}", id);
        return affectationService.updateAffectation(id, request);
    }

    @DeleteMapping("/affectation/delete/{id}")
      @Operation(summary = "Supprimer une affectation", 
               description = "Supprimer une affectation par son ID")
    @ApiResponse(responseCode = "200", description = "Affectation supprimee avec succes")
    public void deleteAffectation(@PathVariable Long id) {
        logger.info("Deleting affectation with ID: {}", id);
        affectationService.deleteAffectation(id);
        logger.info("Affectation with ID {} deleted successfully", id);
    }
    @PostMapping("/affectation/affecter-facture")
     @Operation(summary = "Affecter un reglement a une facture", 
               description = "Associer un reglement a une facture avec un montant")
    @ApiResponse(responseCode = "200", description = "Affectation realisee avec succes",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public AffectationResponse affecterAFacture(
            @RequestParam Long reglementId,
            @RequestParam Long factureId,
            @RequestParam Double montant) {
        return affectationService.affecterAFacture(reglementId, factureId, montant);
    }

    @PostMapping("/affectation/affecter-depense")
     @Operation(summary = "Affecter un reglement a une depense", 
               description = "Associer un reglement a une depense avec un montant")
    @ApiResponse(responseCode = "200", description = "Affectation realisee avec succes",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
    public AffectationResponse affecterADepense(
            @RequestParam Long reglementId,
            @RequestParam Long depenseId,
            @RequestParam Double montant) {
        return affectationService.affecterADepense(reglementId, depenseId, montant);
    }

  @PostMapping("/affectation/affecter-operation")
   @Operation(summary = "Affecter un reglement a une operation bancaire", 
               description = "Associer un reglement a une operation bancaire avec un montant")
    @ApiResponse(responseCode = "200", description = "Affectation realisee avec succes",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
public AffectationResponse affecterAOperationBancaire(
        @RequestParam("reglementId") Long reglementId,
        @RequestParam("operationId") Long operationId,
        @RequestParam("montant") Double montant) {
    
    logger.info("Reçu - reglementId: {}, operationId: {}, montant: {}", 
        reglementId, operationId, montant);
    
    if (reglementId == null || operationId == null || montant == null) {
        throw new IllegalArgumentException("Paramètres manquants");
    }
    
    return affectationService.affecterAOperationBancaire(reglementId, operationId, montant);
}
    @GetMapping("/affectation/by-reglement/{reglementId}")
     @Operation(summary = "Lister les affectations d'un reglement", 
               description = "Obtenir toutes les affectations liees a un reglement donne")
    @ApiResponse(responseCode = "200", description = "Liste des affectations recuperee",
                content = @Content(schema = @Schema(implementation = AffectationResponse.class)))
public List<AffectationResponse> getAffectationsByReglement(@PathVariable Long reglementId) {
    logger.info("Request to get affectations by reglement ID: {}", reglementId);
    return affectationService.getAffectationsByReglement(reglementId);
}
}
