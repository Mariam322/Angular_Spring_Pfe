package tn.iit.entites;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import tn.iit.entites.TypeClientFournisseur;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Depense {
	 // Identifiants
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    
    private String numDepense;
    private LocalDate dateDepense;
    private LocalDate datePaiementDepense;
    private LocalDate dateReceptionDepense;
    private Double montantHtDepense;
    private Double tvaDepense;
    private Double montantTtcDepense;
    private Double totalGeneralDepense;
        private TypeClientFournisseur type; 
    private Long idCompaign;
    @OneToMany(mappedBy = "depense", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneDepense> lignes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumDepense() {
		return numDepense;
	}
	public void setNumDepense(String numDepense) {
		this.numDepense = numDepense;
	}
	public LocalDate getDateDepense() {
		return dateDepense;
	}
	public void setDateDepense(LocalDate dateDepense) {
		this.dateDepense = dateDepense;
	}
	public LocalDate getDatePaiementDepense() {
		return datePaiementDepense;
	}
	public void setDatePaiementDepense(LocalDate datePaiementDepense) {
		this.datePaiementDepense = datePaiementDepense;
	}
	public LocalDate getDateReceptionDepense() {
		return dateReceptionDepense;
	}
	public void setDateReceptionDepense(LocalDate dateReceptionDepense) {
		this.dateReceptionDepense = dateReceptionDepense;
	}
	public Double getMontantHtDepense() {
		return montantHtDepense;
	}
	public void setMontantHtDepense(Double montantHtDepense) {
		this.montantHtDepense = montantHtDepense;
	}
	public Double getTvaDepense() {
		return tvaDepense;
	}
	public void setTvaDepense(Double tvaDepense) {
		this.tvaDepense = tvaDepense;
	}
	public Double getMontantTtcDepense() {
		return montantTtcDepense;
	}
	public void setMontantTtcDepense(Double montantTtcDepense) {
		this.montantTtcDepense = montantTtcDepense;
	}
	public Double getTotalGeneralDepense() {
		return totalGeneralDepense;
	}
	public void setTotalGeneralDepense(Double totalGeneralDepense) {
		this.totalGeneralDepense = totalGeneralDepense;
	}
	public Long getIdCompaign() {
		return idCompaign;
	}
	public void setIdCompaign(Long idCompaign) {
		this.idCompaign = idCompaign;
	}
	public List<LigneDepense> getLignes() {
		return lignes;
	}
	public void setLignes(List<LigneDepense> lignes) {
		this.lignes = lignes;
	}
	    public TypeClientFournisseur getType() {
        return type;
    }

    public void setType(TypeClientFournisseur type) {
        this.type = type;
    }

    
    

}
