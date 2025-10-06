package tn.iit.entites;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.iit.entites.AffectationType;
import jakarta.persistence.Enumerated; // Ajoutez cette ligne
import jakarta.persistence.EnumType; 
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Affectation implements Serializable {

	
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;
    private Double montantAffectaion;
    private LocalDate dateAffectation;
        @Enumerated(EnumType.STRING) // Stocke le nom de l'enum en base
    private AffectationType typeaffect;
    @ManyToOne
    private Reglement reglement;
    private Long idPieceCommercial;
    private Long idOperationBancaire;
    private Long idDepense;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMontantAffectaion() {
		return montantAffectaion;
	}
	public void setMontantAffectaion(Double montantAffectaion) {
		this.montantAffectaion = montantAffectaion;
	}
	public LocalDate getDateAffectation() {
		return dateAffectation;
	}
	public void setDateAffectation(LocalDate dateAffectation) {
		this.dateAffectation = dateAffectation;
	}
	public Reglement getReglement() {
		return reglement;
	}
	public void setReglement(Reglement reglement) {
		this.reglement = reglement;
	}
  public AffectationType getTypeaffect() {
        return typeaffect;
    }

    public void setTypeaffect(AffectationType typeaffect) {
        this.typeaffect = typeaffect;
    }
	public Long getIdPieceCommercial() {
		return idPieceCommercial;
	}
	public void setIdPieceCommercial(Long idPieceCommercial) {
		this.idPieceCommercial = idPieceCommercial;
	}
	public Long getIdOperationBancaire() {
		return idOperationBancaire;
	}
	public void setIdOperationBancaire(Long idOperationBancaire) {
		this.idOperationBancaire = idOperationBancaire;
	}
    public Long getIdDepense() {
    return idDepense;
}

public void setIdDepense(Long idDepense) {
    this.idDepense = idDepense;
}
    
}
