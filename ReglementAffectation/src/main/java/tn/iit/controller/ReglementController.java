package tn.iit.controller;

import java.util.List;
import tn.iit.entites.TypeClientFournisseur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.iit.request.CreateReglementRequest;
import tn.iit.request.UpdateReglementRequest;
import tn.iit.response.ReglementResponse;
import tn.iit.service.ReglementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Reglement Management", description = "API pour la gestion des reglements")
public class ReglementController {

    private static final Logger logger = LoggerFactory.getLogger(ReglementController.class);

    @Autowired
    private ReglementService reglementService;

    @PostMapping("/reglement/create")
    @Operation(summary = "Creer un nouveau reglement",
               description = "Permet de creer un reglement avec les informations fournies")
    @ApiResponse(responseCode = "200", description = "Reglement cree avec succes",
                content = @Content(schema = @Schema(implementation = ReglementResponse.class)))
    @ApiResponse(responseCode = "400", description = "Donnees invalides fournies")
    public ReglementResponse createReglement(@RequestBody CreateReglementRequest request) {
        logger.info("Réception de la requête de création de règlement : {}", request);
        ReglementResponse response = reglementService.createReglement(request);
        return response;
    }

    @GetMapping("/reglement/getById/{id}")
     @Operation(summary = "Recuperer un reglement par ID",
               description = "Obtenir les details d'un reglement specifique par son identifiant")
    @ApiResponse(responseCode = "200", description = "Reglement trouve",
                content = @Content(schema = @Schema(implementation = ReglementResponse.class)))
    @ApiResponse(responseCode = "404", description = "Reglement non trouve")
    public ReglementResponse getReglementById(@PathVariable Long id) {
        logger.info("Récupération du règlement avec ID : {}", id);
        return reglementService.getReglementById(id);
    }

    @GetMapping("/reglement/getAll")
    @Operation(summary = "Lister tous les reglements",
               description = "Obtenir la liste complete de tous les reglements disponibles")
    @ApiResponse(responseCode = "200", description = "Liste des reglements recuperes avec succes",
                content = @Content(schema = @Schema(implementation = ReglementResponse.class)))
    public List<ReglementResponse> getAllReglements() {
        logger.info("Récupération de tous les règlements");
        return reglementService.getAllReglements();
    }

    @PutMapping("/reglement/update/{id}")
     @Operation(summary = "Modifier un reglement",
               description = "Mettre a jour les informations d'un reglement existant")
    @ApiResponse(responseCode = "200", description = "Reglement mis a jour avec succes",
                content = @Content(schema = @Schema(implementation = ReglementResponse.class)))
    @ApiResponse(responseCode = "400", description = "Donnees invalides fournies")
    @ApiResponse(responseCode = "404", description = "Reglement non trouve")
    public ReglementResponse updateReglement(@PathVariable Long id, @RequestBody UpdateReglementRequest request) {
        logger.info("Mise à jour du règlement avec ID : {}", id);
        return reglementService.updateReglement(id, request);
    }

    @DeleteMapping("/reglement/delete/{id}")
     @Operation(summary = "Supprimer un reglement",
               description = "Supprimer un reglement existant par son identifiant")
    @ApiResponse(responseCode = "200", description = "Reglement supprime avec succes")
    @ApiResponse(responseCode = "404", description = "Reglement non trouve")
    public void deleteReglement(@PathVariable Long id) {
        logger.info("Suppression du règlement avec ID : {}", id);
        reglementService.deleteReglement(id);
        logger.info("Règlement avec ID {} supprimé avec succès", id);
    }
    
    @GetMapping("/reglement/getByType/{type}")
        @Operation(summary = "Recuperer les reglements par type",
               description = "Obtenir la liste des reglements filtres par type (client/fournisseur)")
    @ApiResponse(responseCode = "200", description = "Liste des reglements filtres par type",
                content = @Content(schema = @Schema(implementation = ReglementResponse.class)))
    @ApiResponse(responseCode = "400", description = "Type invalide")

public List<ReglementResponse> getReglementsByType(@PathVariable TypeClientFournisseur type) {
    logger.info("Récupération des règlements par type : {}", type);
    return reglementService.getReglementsByType(type);
}
}
