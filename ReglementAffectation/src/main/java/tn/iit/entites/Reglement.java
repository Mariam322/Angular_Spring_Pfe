package tn.iit.entites;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.iit.entites.ModePaiement;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reglement implements Serializable {

	
	    private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
	    private Long id;

		private LocalDate  dateReglement;

	    private Double  montantReglemnt;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
	    private String description;
	    private Long idCompaign;
	    @OneToMany (mappedBy = "reglement")
              @JsonProperty(access = Access.WRITE_ONLY)
	    private List<Affectation> affectations;
	    

	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public LocalDate getDateReglement() {
			return dateReglement;
		}
		public void setDateReglement(LocalDate dateReglement) {
			this.dateReglement = dateReglement;
		}
		public Double getMontantReglemnt() {
			return montantReglemnt;
		}
		public void setMontantReglemnt(Double montantReglemnt) {
			this.montantReglemnt = montantReglemnt;
		}
	   public ModePaiement getModePaiement() {
        return this.modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Long getIdCompaign() {
			return idCompaign;
		}
		public void setIdCompaign(Long idCompaign) {
			this.idCompaign = idCompaign;
		}
	    
	    
	    

}
