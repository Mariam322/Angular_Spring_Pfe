package tn.iit.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class LigneDepense {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String designation;

    private Integer quantite;

    private Double prix;

    private Double total;

   

    @ManyToOne
    @JoinColumn(name = "depense_id") // clé étrangère dans la table LignePieceCommerciale vers PieceCommerciale
    private Depense depense;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDesignation() {
		return designation;
	}



	public void setDesignation(String designation) {
		this.designation = designation;
	}



	public Integer getQuantite() {
		return quantite;
	}



	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}



	public Double getPrix() {
		return prix;
	}



	public void setPrix(Double prix) {
		this.prix = prix;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public Depense getDepense() {
		return depense;
	}



	public void setDepense(Depense depense) {
		this.depense = depense;
	}
    
    
    
}