package tn.iit.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateOperationBancaireRequest;
import tn.iit.request.UpdateOperationBancaireRequest;
import tn.iit.response.OperationBancaireResponse;

import java.util.List;

@FeignClient(
    name = "BANQUESERVICE",

    configuration = FeignConfig.class
)
public interface OperationBnacaireController {

    // Créer une nouvelle opération bancaire
    @PostMapping("/operations")
    OperationBancaireResponse createOperation(@RequestBody CreateOperationBancaireRequest createOperationRequest);

    // Récupérer une opération par ID
    @GetMapping("/operations/{id}")
    OperationBancaireResponse getById(@PathVariable("id") Long id);

    // Récupérer toutes les opérations
    @GetMapping("/operations")
    List<OperationBancaireResponse> getAll();

    // Mettre à jour une opération
    @PutMapping("/operations/{id}")
    OperationBancaireResponse updateOperation(@PathVariable("id") Long id, @RequestBody UpdateOperationBancaireRequest updateOperationRequest);

    // Supprimer une opération
    @DeleteMapping("/operations/delete/{id}")
    void deleteOperation(@PathVariable("id") Long id);
}
