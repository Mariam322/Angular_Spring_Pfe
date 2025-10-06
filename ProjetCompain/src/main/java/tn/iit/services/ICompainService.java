package tn.iit.services;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import tn.iit.request.UpdateCompainRequest;
import tn.iit.request.CreateCompainRequest;
import tn.iit.response.CompainResponse;
import tn.iit.exception.CompainNotFoundException;
import tn.iit.entites.TypeClientFournisseur;
public interface ICompainService {

    CompainResponse createCompain(@RequestBody CreateCompainRequest createCompainRequest);

    CompainResponse updateCompain(@PathVariable long id, @RequestBody UpdateCompainRequest updateCompainRequest);

    CompainResponse getById(@PathVariable long id);

    void deleteCompain(@PathVariable long id);

    List<CompainResponse> getAllCompains();
      List<CompainResponse> getCompainsByType(TypeClientFournisseur type);
    
}
