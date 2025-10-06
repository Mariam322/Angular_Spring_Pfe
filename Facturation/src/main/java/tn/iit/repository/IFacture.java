package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entites.Facture;
import java.util.List;
import tn.iit.entites.StatutFacture;


public interface IFacture extends JpaRepository<Facture,Long> {
 List<Facture> findByStatut(StatutFacture statut);
 List<Facture> findByIdCompaign(Long idCompaign);


}
