package tn.iit.proxy;
import tn.iit.config.FeignConfig;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import tn.iit.request.CreateCompaignRequest;
import tn.iit.request.UpdateCompainRequest;
import tn.iit.response.CompaignResponse;

@FeignClient(
    name = "APIGATWAYCOMPAIN",
    path = "/projetcompain",
  configuration = FeignConfig.class
)
public interface CompaignController {

    // Créer une nouvelle campagne
    @PostMapping("/compain/add")
    CompaignResponse createCompaign(@RequestBody CreateCompaignRequest createCompaignRequest);
    
    // Récupérer une campagne par son ID
    @GetMapping("/compain/{id}")
    CompaignResponse getById(@PathVariable("id") long id);
    
    // Récupérer toutes les campagnes
    @GetMapping("/compain")
    List<CompaignResponse> getAll();
    
    // Mettre à jour une campagne
    @PutMapping("/compain/{id}")
    CompaignResponse updateCompain(@PathVariable("id") long id, @RequestBody UpdateCompainRequest updateRequest);
    
    // Supprimer une campagne par son ID
    @DeleteMapping("/compain/delete/{id}")
    void deleteCompain(@PathVariable("id") long id);
}
