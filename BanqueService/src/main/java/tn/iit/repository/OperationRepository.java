package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import tn.iit.entites.OperationBancaire;

public interface OperationRepository extends JpaRepository<OperationBancaire, Long> {

}
