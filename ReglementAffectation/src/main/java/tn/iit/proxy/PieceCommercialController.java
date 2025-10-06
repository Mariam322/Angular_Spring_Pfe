package tn.iit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateFactureRequest;
import tn.iit.request.UpdateFactureRequest;
import tn.iit.response.FactureResponse;

import java.util.List;

@FeignClient(
     name = "APIGATWAYCOMPAIN", // Nom unique
      path = "/facturation",
    configuration = FeignConfig.class
)
public interface PieceCommercialController {

    // Créer une nouvelle facture
    @PostMapping("/facture/create")
    FactureResponse createFacture(@RequestBody CreateFactureRequest createFactureRequest);

    // Récupérer une facture par ID
    @GetMapping("/facture/getById/{id}")
    FactureResponse getById(@PathVariable("id") Long id);

    // Récupérer toutes les factures
    @GetMapping("/facture/getAll")
    List<FactureResponse> getAll();

    // Mettre à jour une facture
    @PutMapping("/facture/update/{id}")
    FactureResponse updateFacture(@PathVariable("id") Long id, @RequestBody UpdateFactureRequest updateFactureRequest);

    // Supprimer une facture
    @DeleteMapping("/facture/delete/{id}")
    void deleteFacture(@PathVariable("id") Long id);
}
