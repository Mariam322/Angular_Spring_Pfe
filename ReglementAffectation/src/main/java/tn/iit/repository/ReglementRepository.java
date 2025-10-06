package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import tn.iit.entites.Reglement;

public interface ReglementRepository extends JpaRepository<Reglement,Long> {

}
