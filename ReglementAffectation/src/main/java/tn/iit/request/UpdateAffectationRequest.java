package tn.iit.request;

import java.time.LocalDate;
import java.util.Date;

import tn.iit.entites.Reglement;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.TypePieceCommerciale;

public class UpdateAffectationRequest {
	 
	 private Double montantAffectaion;
	    private LocalDate dateAffectation;
	
	    private Reglement reglement;
	    
	    private String numeroFacture;

		// Dates
		private LocalDate dateFacture;

		private LocalDate datePaiement;

		// Montants et taxes
		private Double montantHt;

		private Double tva;

		private Double montantTtc;

		private Double remise;

		private String statut;

		private TypePieceCommerciale typePieceCommerciale;
		 private String reference;
		    private String nom;
		    private String address;
		    private String email;
		    private String telephone;
		    private String pays;
                       private String pdfPath;
		    private String ville;
		    private TypeClientFournisseur type;
		    private Date createdAt;
		private Date dateOperation ;
		private String numCheque;
		private Long debit;
		private Long credit;
		private Long solde;
		private String numDepense;
	    private LocalDate dateDepense;
	    private LocalDate datePaiementDepense;
	    private LocalDate dateReceptionDepense;
	    private Double montantHtDepense;
	    private Double tvaDepense;
	    private Double montantTtcDepense;
	    private Double totalGeneralDepense;
		public UpdateAffectationRequest(Double montantAffectaion, LocalDate dateAffectation, Reglement reglement, String pdfPath,
				String numeroFacture, LocalDate dateFacture, LocalDate datePaiement, Double montantHt, Double tva,
				Double montantTtc, Double remise, String statut, TypePieceCommerciale typePieceCommerciale,
				String reference, String nom, String address, String email, String telephone, String pays, String ville,
				TypeClientFournisseur type, Date createdAt, Date dateOperation, String numCheque, Long debit,
				Long credit, Long solde, String numDepense, LocalDate dateDepense, LocalDate datePaiementDepense,
				LocalDate dateReceptionDepense, Double montantHtDepense, Double tvaDepense, Double montantTtcDepense,
				Double totalGeneralDepense) {
			super();
			this.montantAffectaion = montantAffectaion;
			this.dateAffectation = dateAffectation;
			this.reglement = reglement;
			this.numeroFacture = numeroFacture;
			this.dateFacture = dateFacture;
			this.datePaiement = datePaiement;
			this.montantHt = montantHt;
			this.tva = tva;
			this.montantTtc = montantTtc;
			this.remise = remise;
			this.statut = statut;
			this.typePieceCommerciale = typePieceCommerciale;
			this.reference = reference;
			this.nom = nom;
			this.address = address;
			this.email = email;
			this.telephone = telephone;
			this.pays = pays;
			this.ville = ville;
			this.type = type;
			this.createdAt = createdAt;
			this.dateOperation = dateOperation;
			this.numCheque = numCheque;
			this.debit = debit;
			this.credit = credit;
      this.pdfPath=pdfPath;
			this.solde = solde;
			this.numDepense = numDepense;
			this.dateDepense = dateDepense;
			this.datePaiementDepense = datePaiementDepense;
			this.dateReceptionDepense = dateReceptionDepense;
			this.montantHtDepense = montantHtDepense;
			this.tvaDepense = tvaDepense;
			this.montantTtcDepense = montantTtcDepense;
			this.totalGeneralDepense = totalGeneralDepense;
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
		public String getNumeroFacture() {
			return numeroFacture;
		}
		public void setNumeroFacture(String numeroFacture) {
			this.numeroFacture = numeroFacture;
		}
       public String getPdfPath() {
	return pdfPath;
}

public void setPdfPath(String pdfPath) {
	this.pdfPath = pdfPath;
}
		public LocalDate getDateFacture() {
			return dateFacture;
		}
		public void setDateFacture(LocalDate dateFacture) {
			this.dateFacture = dateFacture;
		}
		public LocalDate getDatePaiement() {
			return datePaiement;
		}
		public void setDatePaiement(LocalDate datePaiement) {
			this.datePaiement = datePaiement;
		}
		public Double getMontantHt() {
			return montantHt;
		}
		public void setMontantHt(Double montantHt) {
			this.montantHt = montantHt;
		}
		public Double getTva() {
			return tva;
		}
		public void setTva(Double tva) {
			this.tva = tva;
		}
		public Double getMontantTtc() {
			return montantTtc;
		}
		public void setMontantTtc(Double montantTtc) {
			this.montantTtc = montantTtc;
		}
		public Double getRemise() {
			return remise;
		}
		public void setRemise(Double remise) {
			this.remise = remise;
		}
		public String getStatut() {
			return statut;
		}
		public void setStatut(String statut) {
			this.statut = statut;
		}
		public TypePieceCommerciale getTypePieceCommerciale() {
			return typePieceCommerciale;
		}
		public void setTypePieceCommerciale(TypePieceCommerciale typePieceCommerciale) {
			this.typePieceCommerciale = typePieceCommerciale;
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
		public Date getDateOperation() {
			return dateOperation;
		}
		public void setDateOperation(Date dateOperation) {
			this.dateOperation = dateOperation;
		}
		public String getNumCheque() {
			return numCheque;
		}
		public void setNumCheque(String numCheque) {
			this.numCheque = numCheque;
		}
		public Long getDebit() {
			return debit;
		}
		public void setDebit(Long debit) {
			this.debit = debit;
		}
		public Long getCredit() {
			return credit;
		}
		public void setCredit(Long credit) {
			this.credit = credit;
		}
		public Long getSolde() {
			return solde;
		}
		public void setSolde(Long solde) {
			this.solde = solde;
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
	    
}
