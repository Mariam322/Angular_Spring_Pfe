package tn.iit.request;
import java.util.Date;
import java.time.LocalDate;
import tn.iit.entites.TypeClientFournisseur;
public class UpdateDepenseRequest {
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
	public UpdateDepenseRequest(String numDepense, LocalDate dateDepense, LocalDate datePaiementDepense,
			LocalDate dateReceptionDepense, Double montantHtDepense, Double tvaDepense, Double montantTtcDepense,
			Double totalGeneralDepense,String reference, String nom, String address, String email,
			String telephone, String pays, String ville, TypeClientFournisseur type, Date createdAt) {
		super();
		this.numDepense = numDepense;
		this.dateDepense = dateDepense;
		this.datePaiementDepense = datePaiementDepense;
		this.dateReceptionDepense = dateReceptionDepense;
		this.montantHtDepense = montantHtDepense;
		this.tvaDepense = tvaDepense;
		this.montantTtcDepense = montantTtcDepense;
		this.totalGeneralDepense = totalGeneralDepense;
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
