package tn.iit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.LigneDepense;
import tn.iit.exception.LigneDepenseNotFoundException;
import tn.iit.repository.LigneDepenseRepository;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class LigneDepenseService {
private final LigneDepenseRepository ligneRepository;




public LigneDepense saveOrUpdate(LigneDepense ligne) {
    return ligneRepository.save(ligne);
}

public List<LigneDepense> findAll() {
    return ligneRepository.findAll();
}

public LigneDepense getById(Long id) throws LigneDepenseNotFoundException {
    return ligneRepository.findById(id)
            .orElseThrow(() -> new LigneDepenseNotFoundException("LignePieceCommerciale with id " + id + " not found"));
}

public void deleteById(Long id) throws LigneDepenseNotFoundException {
    LigneDepense ligne = ligneRepository.findById(id)
            .orElseThrow(() -> new LigneDepenseNotFoundException("LignePieceCommerciale with id " + id + " not found"));
    ligneRepository.delete(ligne);
}
}

