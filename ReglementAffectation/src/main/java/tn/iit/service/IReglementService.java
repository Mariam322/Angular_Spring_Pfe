package tn.iit.service;

import tn.iit.request.CreateReglementRequest;
import tn.iit.request.UpdateReglementRequest;
import tn.iit.response.ReglementResponse;
import tn.iit.entites.TypeClientFournisseur;
import java.util.List;

public interface IReglementService {

    // Créer un règlement
    ReglementResponse createReglement(CreateReglementRequest createReglementRequest);

    // Mettre à jour un règlement
    ReglementResponse updateReglement(Long id, UpdateReglementRequest updateReglementRequest);

    // Supprimer un règlement
    void deleteReglement(Long id);

    // Récupérer un règlement par ID
    ReglementResponse getReglementById(Long id);

    // Récupérer tous les règlements
    List<ReglementResponse> getAllReglements();
    
     List<ReglementResponse> getReglementsByType(TypeClientFournisseur type);
    
    
}
