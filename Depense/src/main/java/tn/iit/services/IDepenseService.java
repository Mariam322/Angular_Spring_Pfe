package tn.iit.services;

import tn.iit.request.CreateDepenseRequest;
import tn.iit.request.UpdateDepenseRequest;
import tn.iit.response.DepenseResponse;

import java.util.List;

public interface IDepenseService {

    // Créer une dépense
    DepenseResponse createDepense(CreateDepenseRequest createDepenseRequest);

    // Mettre à jour une dépense
    DepenseResponse updateDepense(Long id, UpdateDepenseRequest updateDepenseRequest);

    // Supprimer une dépense
    void deleteDepense(Long id);

    // Récupérer une dépense par ID
    DepenseResponse getDepenseById(Long id);

    // Récupérer toutes les dépenses
    List<DepenseResponse> getAllDepenses();
}
