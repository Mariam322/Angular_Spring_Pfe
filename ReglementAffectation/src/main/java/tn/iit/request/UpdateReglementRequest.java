package tn.iit.request;

import java.sql.Date;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import tn.iit.entites.ModePaiement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.iit.entites.TypeClientFournisseur;

@Getter
@Setter


public class UpdateReglementRequest {
	 private LocalDate  dateReglement;

	    private Double  montantReglemnt;
private ModePaiement modePaiement;
	    private String description;
	    private String reference;
	    private String nom;
	    private String address;
	    private String email;
	    private String telephone;
	    private String pays;
	    private String ville;
	    private TypeClientFournisseur type; 
	    private Date createdAt;
		public UpdateReglementRequest(LocalDate dateReglement, Double montantReglemnt, ModePaiement modePaiement,
				String description, String reference, String nom, String address, String email, String telephone,
				String pays, String ville, TypeClientFournisseur type, Date createdAt) {
			super();
			this.dateReglement = dateReglement;
			this.montantReglemnt = montantReglemnt;
		    this.modePaiement = modePaiement;
			this.description = description;
			this.reference = reference;
			this.nom = nom;
			this.address = address;
			this.email = email;
			this.telephone = telephone;
			this.pays = pays;
			this.ville = ville;
			this.type = type;
			this.createdAt = createdAt;
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
	    
	    
	    
}
