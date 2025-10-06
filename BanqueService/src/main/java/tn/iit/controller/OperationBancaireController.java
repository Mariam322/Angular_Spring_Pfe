package tn.iit.controller;


import java.util.List;


import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.OperationBancaire;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.OperationNotFoundException;
import tn.iit.services.OperationBancaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j

@Tag(name = "Gestion des operations bancaires", description = "API pour effectuer des operations bancaires liees aux comptes")
public class OperationBancaireController {

    private final OperationBancaireService operationService;

 
 


	@GetMapping("/operations")
 @Operation(summary = "Lister toutes les operations", description = "Permet d’obtenir la liste de toutes les operations bancaires")
    @ApiResponse(responseCode = "200", description = "Liste des operations recuperee avec succes")
    public List<OperationBancaire> getAllOperations() {
        return operationService.findAll();
    }


    @GetMapping("/operations/{id}")
    
    @Operation(summary = "Obtenir une opération par ID", description = "Recupere une operation bancaire par son identifiant")
    @ApiResponse(responseCode = "200", description = "Operation trouvee")
    @ApiResponse(responseCode = "404", description = "Opération non trouvee")
    public OperationBancaire getById(@PathVariable("id") Long id) throws OperationNotFoundException {
        return operationService.getById(id);
    }

   
    @PostMapping("/operations")
     @Operation(summary = "Creer ou mettre a jour une operation", description = "Ajoute ou met a jour une oparation bancaire")
    @ApiResponse(responseCode = "200", description = "Operation creee ou mise a jour avec succes")
    @ApiResponse(responseCode = "400", description = "Données invalides fournies")
    public OperationBancaire createOrUpdateOperation(@RequestBody OperationBancaire operation) {
        return operationService.saveOrUpdate(operation);
    }


    @DeleteMapping("/operations/delete/{id}")
    @Operation(summary = "Supprimer une operation", description = "Supprime une operation bancaire par son identifiant")
    @ApiResponse(responseCode = "200", description = "Operation supprimee avec succes")
    @ApiResponse(responseCode = "404", description = "Operation non trouvee")
    public void deleteOperation(@PathVariable("id") Long id) throws OperationNotFoundException {
        operationService.deleteOperationByIdCompte(id);
    }


    @PostMapping("/comptes/{id}/operations")
     @Operation(summary = "Ajouter une operation a un compte", description = "Ajoute une nouvelle operation à un compte bancaire donne")
    @ApiResponse(responseCode = "200", description = "Operation enregistree avec succes pour le compte")
    @ApiResponse(responseCode = "404", description = "Compte non trouve")
    public OperationBancaire saveOperationForCompte(@PathVariable("id") Long id,
                                                    @RequestBody OperationBancaire operation) throws CompteNotFoundException {
        return operationService.saveOperationBnacaireByIdCompte(id, operation);
    }
}
