package tn.iit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.LignePieceCommerciale;
import tn.iit.exception.LignePieceCommercialeNotFoundException;
import tn.iit.repository.LignePieceCommercialeRepository;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class LignePieceCommercialeService {

    private final LignePieceCommercialeRepository ligneRepository;
    

    public LignePieceCommerciale saveOrUpdate(LignePieceCommerciale ligne) {
        
        return ligneRepository.save(ligne);
    }

    public List<LignePieceCommerciale> findAll() {
       
        return ligneRepository.findAll();
    }

    public LignePieceCommerciale getById(Long id) throws LignePieceCommercialeNotFoundException {
      
        return ligneRepository.findById(id)
                .orElseThrow(() -> new LignePieceCommercialeNotFoundException("LignePieceCommerciale with id " + id + " not found"));
    }

    public void deleteById(Long id) throws LignePieceCommercialeNotFoundException {
  
        LignePieceCommerciale ligne = ligneRepository.findById(id)
                .orElseThrow(() -> new LignePieceCommercialeNotFoundException("LignePieceCommerciale with id " + id + " not found"));
        ligneRepository.delete(ligne);
    }
}
