package tn.iit.response;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tn.iit.entites.TypeClientFournisseur;
public class DepenseResponse {
	 private Long id;
	    
	    private String numDepense;
	    private LocalDate dateDepense;
	    private LocalDate datePaiementDepense;
	    private LocalDate dateReceptionDepense;
	    private Double montantHtDepense;
	    private Double tvaDepense;
	    private Double montantTtcDepense;
	    private Double totalGeneralDepense;
         private String reference;
	    private String nom;
	    private String address;
	    private String email;
	    private String telephone;
	    private String pays;
	    private String ville;
	    private TypeClientFournisseur type;
	    private Date createdAt;
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
