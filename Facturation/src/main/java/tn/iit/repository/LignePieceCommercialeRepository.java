package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.iit.entites.LignePieceCommerciale;

@Repository
public interface LignePieceCommercialeRepository extends JpaRepository<LignePieceCommerciale, Long> {
    // Tu peux ajouter ici des m�thodes personnalis�es si besoin
}

