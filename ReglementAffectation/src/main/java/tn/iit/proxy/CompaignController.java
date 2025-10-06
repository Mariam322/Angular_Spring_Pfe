package tn.iit.proxy;


import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import tn.iit.config.FeignConfig;
import tn.iit.request.CreateCompaignRequest;
import tn.iit.response.CompaignResponse;

@FeignClient(
	    name = "PROJETCOMPAIN",
	 
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
    CompaignResponse updateCompain(@PathVariable("id") long id, @RequestBody CreateCompaignRequest updateRequest);
    
    // Supprimer une campagne par son ID
    @DeleteMapping("/compain/delete/{id}")
    void deleteCompain(@PathVariable("id") long id);
    
    @GetMapping("/compain/type/{type}")
    List<CompaignResponse> getCompainsByType(@PathVariable("type") String type);
    
}
