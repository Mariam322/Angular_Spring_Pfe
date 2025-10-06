package tn.iit.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.LigneDepense;
import tn.iit.exception.LigneDepenseNotFoundException;
import tn.iit.services.LigneDepenseService;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j
public class LigneDepenseController {

    private final LigneDepenseService ligneDepenseService;
    

    @GetMapping("/ligne-depenses")
    public List<LigneDepense> getAllLigneDepenses() {
      
        return ligneDepenseService.findAll();
    }

   
    @GetMapping("/ligne-depenses/{id}")
    public LigneDepense getLigneDepenseById(@PathVariable("id") Long id) throws LigneDepenseNotFoundException {
        return ligneDepenseService.getById(id);
    }


    @PostMapping("/ligne-depenses/add")
    public LigneDepense createLigneDepense(@RequestBody LigneDepense ligneDepense) {
        return ligneDepenseService.saveOrUpdate(ligneDepense);
    }


    @PutMapping("/ligne-depenses/{id}")
    public LigneDepense updateLigneDepense(@PathVariable("id") Long id, @RequestBody LigneDepense updatedLigne) throws LigneDepenseNotFoundException {
        LigneDepense existingLigne = ligneDepenseService.getById(id);
        existingLigne.setDesignation(updatedLigne.getDesignation());
        existingLigne.setQuantite(updatedLigne.getQuantite());
        existingLigne.setPrix(updatedLigne.getPrix());
        existingLigne.setTotal(updatedLigne.getTotal());

        // Ajoute d'autres champs à mettre à jour si nécessaire
        return ligneDepenseService.saveOrUpdate(existingLigne);
    }

   



	@DeleteMapping("/ligne-depenses/delete/{id}")
    public void deleteLigneDepense(@PathVariable("id") Long id) throws LigneDepenseNotFoundException {
        
        ligneDepenseService.deleteById(id);
    }
}
