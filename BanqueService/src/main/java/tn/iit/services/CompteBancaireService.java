package tn.iit.services;



import java.util.List;

import org.springframework.stereotype.Service;
import tn.iit.entites.OperationBancaire;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.CompteBancaire;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.repository.CompteRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompteBancaireService {

    private final CompteRepository compteRepository;

  
	// Créer ou mettre à jour un compte
    public CompteBancaire saveOrUpdate(CompteBancaire compte) {
     
        return compteRepository.save(compte);
    }

    // Obtenir tous les comptes
    public List<CompteBancaire> findAll() {
        return compteRepository.findAll();
    }

    // Supprimer un compte par ID
    public void delete(Long idCompte) throws CompteNotFoundException {
        if (!compteRepository.existsById(idCompte)) {
            throw new CompteNotFoundException("Compte avec ID " + idCompte + " non trouvé");
        }
        compteRepository.deleteById(idCompte);
    }

    // Obtenir un compte par ID
    public CompteBancaire getById(Long idCompte) throws CompteNotFoundException {
        return compteRepository.findById(idCompte)
                .orElseThrow(() -> new CompteNotFoundException("Compte avec ID " + idCompte + " non trouvé"));
    }
    
    public List<OperationBancaire> getOperationsByCompteId(Long idCompte) throws CompteNotFoundException {
    CompteBancaire compte = getById(idCompte); // utilise la méthode existante
    return compte.getOperationBancaire();      // récupère la liste liée
}

    
    
 

   
}
