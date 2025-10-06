package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import tn.iit.entites.CompteBancaire;

public interface CompteRepository extends JpaRepository<CompteBancaire, Long> {

}
