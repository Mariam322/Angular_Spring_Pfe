package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entites.Affectation;
import java.util.List;


public interface AffectationRepository extends JpaRepository<Affectation,Long>  {
List<Affectation> findByReglementId(Long reglementId);

}
