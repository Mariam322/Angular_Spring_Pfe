package tn.iit.services;

import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tn.iit.entites.CompteBancaire;
import tn.iit.entites.OperationBancaire;

import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.OperationNotFoundException;
import tn.iit.repository.CompteRepository;
import tn.iit.repository.OperationRepository;

@Service
//inject dependance
@RequiredArgsConstructor
public class OperationBancaireService {
  private final CompteRepository compteRepository ;
  private final OperationRepository operationRepository;
  

	public OperationBancaire saveOperationBnacaireByIdCompte (Long idOperation,OperationBancaire operationBancaire) throws CompteNotFoundException {
		//recuperation de compte
		CompteBancaire compte=compteRepository.findById(idOperation)
				.orElseThrow(() -> new CompteNotFoundException("Client with ID " + idOperation + " not found"));
		
		return operationRepository.save(operationBancaire);
		
	}
	@Transactional
	public OperationBancaire saveOrUpdate(OperationBancaire operation) {
		return operationRepository.save(operation);
	}
	
	public List<OperationBancaire>findAll(){
		return operationRepository.findAll();
	}
	public void delete(Long idOperation) {

		operationRepository.deleteById(idOperation);

	}
	public void deleteOperationByIdCompte(Long idOperation) throws  OperationNotFoundException {
	    // Récupérer l'operation bancaire par son id
	    OperationBancaire operation = operationRepository.findById(idOperation)
	            .orElseThrow(() -> new OperationNotFoundException("operation avec id " + idOperation + " non trouvé"));

	    // Supprimer le compte
	    operationRepository.delete(operation);
	}
	
	public OperationBancaire getById(Long idOperation) throws OperationNotFoundException {

		return operationRepository.findById(idOperation).orElseThrow(() -> new OperationNotFoundException(idOperation + "operation Not Found"));

	}
	  /* public List<OperationBancaire> getOperationByCompteId(Long clientId) throws CompteNotFoundException {
	        List<OperationBancaire> operations = operationRepository.find(clientId);
	        if (comptes.isEmpty()) {
	            throw new ClientNotFoundException("No accounts found for client with ID: " + clientId);
	        }*/
	      
}
