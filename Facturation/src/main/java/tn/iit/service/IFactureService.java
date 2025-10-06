package tn.iit.service;

import tn.iit.request.CreateFactureRequest;
import tn.iit.request.UpdateFactureRequest;
import tn.iit.response.FactureResponse;
import tn.iit.entites.StatutFacture;
import java.util.List;

public interface IFactureService {

    // Créer une facture
    FactureResponse createFacture(CreateFactureRequest createFactureRequest);

    // Mettre à jour une facture
    FactureResponse updateFacture(Long id, UpdateFactureRequest updateFactureRequest);

    // Supprimer une facture
    void deleteFacture(Long id);

    // Récupérer une facture par ID
    FactureResponse getFactureById(Long id);

    // Récupérer toutes les factures
    List<FactureResponse> getAllFactures();
     
    FactureResponse updateStatut(Long id, StatutFacture statut);
    
    FactureResponse updateMontantRestant(Long id, Double montant);
     List<FactureResponse> getFacturesByCompanyId(Long companyId);
}
