package tn.iit.repositories;
import tn.iit.entites.TypeClientFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import tn.iit.entites.Compain;

public interface CompainRepository extends JpaRepository<Compain, Long> {
       List<Compain> findByType(TypeClientFournisseur type);


}
