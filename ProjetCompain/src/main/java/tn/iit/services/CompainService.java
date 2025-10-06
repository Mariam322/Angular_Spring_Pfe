package tn.iit.services;

import java.util.List;
import java.util.stream.Collectors;
import tn.iit.entites.TypeClientFournisseur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import tn.iit.entites.Compain;
import tn.iit.repositories.CompainRepository;
import tn.iit.request.CreateCompainRequest;
import tn.iit.request.UpdateCompainRequest;
import tn.iit.response.CompainResponse;

@Service
public class CompainService implements ICompainService {

    private static final Logger logger = LoggerFactory.getLogger(CompainService.class);

    @Autowired
    private CompainRepository compainRepository;

    @Override
    public CompainResponse createCompain(CreateCompainRequest createCompainRequest) {
        Compain compain = new Compain();
        compain.setReference(createCompainRequest.getReference());
       compain.setNom(createCompainRequest.getNom());
    compain.setAddress(createCompainRequest.getAddress());
    compain.setEmail(createCompainRequest.getEmail());
    compain.setTelephone(createCompainRequest.getTelephone());
    compain.setPays(createCompainRequest.getPays());
    compain.setVille(createCompainRequest.getVille());
    compain.setType(createCompainRequest.getType());
        compain.setCreatedAt(createCompainRequest.getCreatedAt());
        compain.setUpdatedAt(createCompainRequest.getUpdatedAt());

        compain = compainRepository.save(compain);

        return new CompainResponse(compain);
    }

    @Override
    public CompainResponse updateCompain(long id, UpdateCompainRequest updateCompainRequest) {
        logger.info("Updating compain with ID: {}", id);

        Compain compain = compainRepository.findById(id)
            .orElseThrow(() -> {
                logger.error("Compain with ID {} not found for update", id);
                return new RuntimeException("Compain not found");
            });
 if (updateCompainRequest.getReference() != null) {
        compain.setReference(updateCompainRequest.getReference());
        logger.info("Updated Reference: {}", updateCompainRequest.getReference());
    }
    if (updateCompainRequest.getNom() != null) {
        compain.setNom(updateCompainRequest.getNom());
        logger.info("Updated Nom: {}", updateCompainRequest.getNom());
    }
    if (updateCompainRequest.getAddress() != null) {
        compain.setAddress(updateCompainRequest.getAddress());
        logger.info("Updated Address: {}", updateCompainRequest.getAddress());
    }
    if (updateCompainRequest.getEmail() != null) {
        compain.setEmail(updateCompainRequest.getEmail());
        logger.info("Updated Email: {}", updateCompainRequest.getEmail());
    }
    if (updateCompainRequest.getTelephone() != null) {
        compain.setTelephone(updateCompainRequest.getTelephone());
        logger.info("Updated Telephone: {}", updateCompainRequest.getTelephone());
    }
    if (updateCompainRequest.getPays() != null) {
        compain.setPays(updateCompainRequest.getPays());
        logger.info("Updated Pays: {}", updateCompainRequest.getPays());
    }
    if (updateCompainRequest.getVille() != null) {
        compain.setVille(updateCompainRequest.getVille());
        logger.info("Updated Ville: {}", updateCompainRequest.getVille());
    }
    if (updateCompainRequest.getType() != null) {
        compain.setType(updateCompainRequest.getType());
        logger.info("Updated Type: {}", updateCompainRequest.getType());
    }
        if (updateCompainRequest.getUpdatedAt() != null) {
            compain.setUpdatedAt(updateCompainRequest.getUpdatedAt());
            logger.info("Updated UpdatedAt: {}", updateCompainRequest.getUpdatedAt());
        }

        compain = compainRepository.save(compain);

        return new CompainResponse(compain);
    }

    @Override
    public CompainResponse getById(long id) {
        return new CompainResponse(
            compainRepository.findById(id).orElseThrow(() -> new RuntimeException("Compain not found"))
        );
    }

    @Override
    public void deleteCompain(long id) {
        try {
            logger.info("Attempting to delete compain with ID: {}", id);
            Compain compain = compainRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compain not found"));

            compainRepository.delete(compain);
            logger.info("Compain with ID {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Failed to delete compain with ID: {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<CompainResponse> getAllCompains() {
        return CompainResponse.toArrayList(compainRepository.findAll());
    }
    @Override
    public List<CompainResponse> getCompainsByType(TypeClientFournisseur type) {
        List<Compain> compains = compainRepository.findByType(type);

        return compains.stream()
                      .map(CompainResponse::new)
                      .collect(Collectors.toList());
    }
}
