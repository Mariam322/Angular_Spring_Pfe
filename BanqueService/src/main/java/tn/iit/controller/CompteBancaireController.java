package tn.iit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import tn.iit.entites.OperationBancaire;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.CompteBancaire;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.services.CompteBancaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Gestion des comptes bancaires", description = "API pour les operations sur les comptes bancaires")
public class CompteBancaireController {

    private final CompteBancaireService compteService;

  @Operation(summary = "Lister tous les comptes", description = "Recuperer la liste de tous les comptes bancaires")
    @ApiResponse(responseCode = "200", description = "Liste des comptes recuperee avec succes")
    @ApiResponse(responseCode = "401", description = "Non autorisé")
    @GetMapping("/comptes")
    public List<CompteBancaire> getAllComptes() {
        return compteService.findAll();
    }
 @Operation(summary = "Obtenir un compte par ID", description = "Recuperer les details d'un compte bancaire en utilisant son identifiant")
    @ApiResponse(responseCode = "200", description = "Compte trouve")
    @ApiResponse(responseCode = "404", description = "Compte non trouve")
    @ApiResponse(responseCode = "401", description = "Non autorise")

    @GetMapping("/comptes/{id}")
    public CompteBancaire getCompteById(@PathVariable("id") Long id) throws CompteNotFoundException {
        return compteService.getById(id);
    }
    
     @Operation(summary = "Creer un nouveau compte", description = "Ajouter un nouveau compte bancaire")
    @ApiResponse(responseCode = "200", description = "Compte cree avec succes")
    @ApiResponse(responseCode = "400", description = "Requete invalide")
    @ApiResponse(responseCode = "401", description = "Non autorise")

    @PostMapping("/comptes")
    public CompteBancaire createOrUpdateCompte(@RequestBody CompteBancaire compte) {
        return compteService.saveOrUpdate(compte);
    }

@PutMapping("/comptes/{id}")
 @Operation(summary = "Mettre a jour un compte", description = "Modifier les informations d’un compte existant")
    @ApiResponse(responseCode = "200", description = "Compte mis e jour avec succes")
    @ApiResponse(responseCode = "404", description = "Compte non trouve")
    @ApiResponse(responseCode = "401", description = "Non autorise")
public CompteBancaire updateCompte(@PathVariable("id") Long compteId, @RequestBody CompteBancaire updatedCompte)
        throws CompteNotFoundException {
    
    CompteBancaire existingCompte = compteService.getById(compteId);

    // Mise à jour des champs autorisés
    existingCompte.setNumeroCompte(updatedCompte.getNumeroCompte());
    existingCompte.setNomBanque(updatedCompte.getNomBanque());

    // Optionnel : si tu veux permettre la mise à jour des opérations
    // existingCompte.setOperationBancaire(updatedCompte.getOperationBancaire());

    return compteService.saveOrUpdate(existingCompte);
}


    @DeleteMapping("/comptes/delete/{id}")
     @Operation(summary = "Supprimer un compte", description = "Supprimer un compte bancaire existant")
    @ApiResponse(responseCode = "200", description = "Compte supprime avec succes")
    @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    @ApiResponse(responseCode = "401", description = "Non autorisé")
    public void deleteCompte(@PathVariable("id") Long id) throws CompteNotFoundException {
        compteService.delete(id);
    }
    
    
    @GetMapping("/comptes/{id}/operations")
   
public List<OperationBancaire> getOperationsByCompte(@PathVariable("id") Long id) throws CompteNotFoundException {
    return compteService.getOperationsByCompteId(id);
}
}
