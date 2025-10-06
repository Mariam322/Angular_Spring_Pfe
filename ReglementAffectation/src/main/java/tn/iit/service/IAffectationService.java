package tn.iit.service;

import tn.iit.request.CreateAffectationRequest;
import tn.iit.request.UpdateAffectationRequest;
import tn.iit.response.AffectationResponse;

import java.util.List;

public interface IAffectationService {

    // Créer une affectation
    AffectationResponse createAffectation(CreateAffectationRequest createAffectationRequest);

    // Mettre à jour une affectation
    AffectationResponse updateAffectation(Long id, UpdateAffectationRequest updateAffectationRequest);

    // Supprimer une affectation
    void deleteAffectation(Long id);

    // Récupérer une affectation par ID
    AffectationResponse getAffectationById(Long id);

    // Récupérer toutes les affectations
    List<AffectationResponse> getAllAffectations();
    AffectationResponse affecterAFacture(Long reglementId, Long factureId, Double montant);
    AffectationResponse affecterADepense(Long reglementId, Long depenseId, Double montant);
    AffectationResponse affecterAOperationBancaire(Long reglementId, Long operationId, Double montant);
    List<AffectationResponse> getAffectationsByReglement(Long reglementId);
}
