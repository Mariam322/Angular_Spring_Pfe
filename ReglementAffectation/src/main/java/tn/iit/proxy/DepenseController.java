package tn.iit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.UpdateDepenseRequest;
import tn.iit.response.DepenseResponse;

import java.util.List;

@FeignClient(
    name = "DEPENSE",
   path = "/depense",
    configuration = FeignConfig.class
)
public interface DepenseController {

    // Créer une nouvelle dépense
    @PostMapping("/create")
    DepenseResponse createDepense(@RequestBody CreateDepenseRequest createDepenseRequest);

    // Récupérer une dépense par ID
    @GetMapping("/getById/{id}")
    DepenseResponse getById(@PathVariable("id") Long id);

    // Récupérer toutes les dépenses
    @GetMapping("/getAll")
    List<DepenseResponse> getAll();

    // Mettre à jour une dépense
    @PutMapping("/update/{id}")
    DepenseResponse updateDepense(@PathVariable("id") Long id, @RequestBody UpdateDepenseRequest updateDepenseRequest);

    // Supprimer une dépense
    @DeleteMapping("/delete/{id}")
    void deleteDepense(@PathVariable("id") Long id);
}
