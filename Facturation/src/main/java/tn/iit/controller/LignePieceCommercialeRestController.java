package tn.iit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.LignePieceCommerciale;
import tn.iit.exception.LignePieceCommercialeNotFoundException;
import tn.iit.service.LignePieceCommercialeService;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j
public class LignePieceCommercialeRestController {
 
    private final LignePieceCommercialeService ligneService;
    

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/lignes")
    public List<LignePieceCommerciale> getAllLignes() {
      
        return ligneService.findAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/lignes/{id}")
    public LignePieceCommerciale getLigneById(@PathVariable("id") Long id) throws LignePieceCommercialeNotFoundException {
      
        return ligneService.getById(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/lignes/add")
    public LignePieceCommerciale createLigne(@RequestBody LignePieceCommerciale ligne) {
       
        return ligneService.saveOrUpdate(ligne);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/lignes/{id}")
    public LignePieceCommerciale updateLigne(@PathVariable("id") Long id, @RequestBody LignePieceCommerciale updatedLigne) throws LignePieceCommercialeNotFoundException {
      
        LignePieceCommerciale existingLigne = ligneService.getById(id);
        existingLigne.setQuantite(updatedLigne.getQuantite());
        existingLigne.setPrixUnitaire(updatedLigne.getPrixUnitaire());
      
        // Ajoute d'autres champs � mettre � jour si n�cessaire
        return ligneService.saveOrUpdate(existingLigne);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/lignes/delete/{id}")
    public void deleteLigne(@PathVariable("id") Long id) throws LignePieceCommercialeNotFoundException {
      
        ligneService.deleteById(id);
    }
}
