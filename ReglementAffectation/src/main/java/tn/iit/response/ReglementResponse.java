package tn.iit.response;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.iit.entites.Reglement;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.ModePaiement;
@Getter
@Setter

public class ReglementResponse {
	   private Long id;
    private LocalDate  dateReglement;

    private Double  montantReglemnt;
private ModePaiement modePaiement;
    private String description;
	  private Long idCompaign;
	 private String reference;
	    private String nom;
	    private String address;
	    private String email;
	    private String telephone;
	    private String pays;
	    private String ville;
	    private TypeClientFournisseur type;
	    private Date createdAt;
	    
	    
	    public ReglementResponse(Reglement reglement) {
	        this.id = reglement.getId();
	        this.dateReglement=reglement.getDateReglement();
	        this.montantReglemnt=reglement.getMontantReglemnt();
	      this.modePaiement = reglement.getModePaiement();
	        this.description=reglement.getDescription();
           
	        
	    }
	    public ReglementResponse(Reglement reglement,CompaignResponse compaign) {
	    	this(reglement);
	    	 if (compaign != null) {
             this.idCompaign=compaign.getId();
	    	        this.reference=compaign.getReference();
	    	           
	    	            this.nom = compaign.getNom();
	    	            this.address = compaign.getAddress();
	    	            this.email = compaign.getEmail();
	    	            this.telephone = compaign.getTelephone();
	    	            this.pays = compaign.getPays();
	    	            this.ville = compaign.getVille();
	    	            this.type = compaign.getType();
	    	            this.createdAt=compaign.getCreatedAt();
	    	        }
	    	 
	    	 
	    	 
	    }
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
    	public Long getIdCompaign() {
			return idCompaign;
		}
		public void setIdCompaign(Long idCompaign) {
			this.idCompaign = idCompaign;
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
			description = description;
		}
		public String getReference() {
			return reference;
		}
		public void setReference(String reference) {
			this.reference = reference;
		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getPays() {
			return pays;
		}
		public void setPays(String pays) {
			this.pays = pays;
		}
		public String getVille() {
			return ville;
		}
		public void setVille(String ville) {
			this.ville = ville;
		}
		public TypeClientFournisseur getType() {
			return type;
		}
		public void setType(TypeClientFournisseur type) {
			this.type = type;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
	    
		public static List<ReglementResponse> toArrayList(List<Reglement> reglements) {
		    List<ReglementResponse> list = new ArrayList<>();
		    for (Reglement reglement : reglements) {
		        list.add(new ReglementResponse(reglement));
		    }
		    return list;
		}

}
