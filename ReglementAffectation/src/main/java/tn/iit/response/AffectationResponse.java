package tn.iit.response;

import java.time.LocalDate;
import java.util.Date;
import tn.iit.entites.AffectationType;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ManyToOne;
import tn.iit.entites.Affectation;
import tn.iit.entites.Reglement;
import tn.iit.entites.TypePieceCommerciale;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.StatutFacture;
public class AffectationResponse {
	 private Long id;
	    private Double montantAffectaion;
	    private LocalDate dateAffectation;
	  
	    private Reglement reglement;
	     private String numeroFacture;
	    private LocalDate dateFacture;
          private AffectationType typeaffect;
	    private LocalDate datePaiement;
	    private Double montantHt;
	    private Double tva;
	    private Double montantTtc;
	    private Double remise;
  private Long idPieceCommercial;
    private Long idOperationBancaire;
    private Long idDepense;
	    private StatutFacture statut;
	    private String pdfPath ;
	      private TypePieceCommerciale typePieceCommerciale; 
	    private String reference;
	    private String nom;
	    private String address;
	    private String email;
	    private String telephone;
	    private String pays;
	    private String ville;
	    private TypeClientFournisseur type;
	    private Date createdAt;
		private Date dateOperation ;
		private String numCheque;
		private Long debit;
		private Long credit;
		 private String numDepense;
		    private LocalDate dateDepense;
		    private LocalDate datePaiementDepense;
		    private LocalDate dateReceptionDepense;
		    private Double montantHtDepense;
		    private Double tvaDepense;
		    private Double montantTtcDepense;
		    private Double totalGeneralDepense;
            
		    
		    public AffectationResponse(Affectation affectation) {
		        this.id = affectation.getId();
		        this.montantAffectaion=affectation.getMontantAffectaion();
		        this.dateAffectation=affectation.getDateAffectation();
                    this.typeaffect=affectation.getTypeaffect();
		        this.reglement=affectation.getReglement();
                    this.idPieceCommercial=affectation.getIdPieceCommercial();
                    this.idOperationBancaire=affectation.getIdOperationBancaire();
                    this.idDepense=affectation.getIdDepense();
		
		        
		    }
		    public AffectationResponse(Affectation affectation,FactureResponse facture,OperationBancaireResponse operation,DepenseResponse depense) {
		    	this(affectation);
		    	 if (facture != null) {
		    	        this.numeroFacture=facture.getNumeroFacture();
		    	           
		    	            this.dateFacture = facture.getDateFacture();
		    	            this.datePaiement = facture.getDatePaiement();
		    	            this.montantHt=facture.getMontantHt();
		    	            this.tva = facture.getTva();
		    	            this.montantTtc = facture.getMontantTtc();
		    	            this.remise = facture.getRemise();
		    	            this.statut = facture.getStatut();
    this.pdfPath = facture.getPdfPath();
    this.typePieceCommerciale = facture.getTypePieceCommerciale();
    this.reference = facture.getReference();
    this.nom = facture.getNom();
    this.address = facture.getAddress();
    this.email = facture.getEmail();
    this.telephone = facture.getTelephone();
    this.pays = facture.getPays();
    this.ville = facture.getVille();
    this.type = facture.getType();
    this.createdAt = facture.getCreatedAt();
		    	 }
		    	 if(operation!=null) {
		    		 this.dateOperation=operation.getDateOperation();
		    		 this.numCheque=operation.getNumCheque();
		    		 this.debit=operation.getDebit();
		    		 this.credit=operation.getCredit();
		    		 
		    	 }
		    	 if(depense!=null) {
		    		 this.numDepense=depense.getNumDepense();
		    		 this.dateDepense=depense.getDateDepense();
		    		 this.datePaiementDepense=depense.getDatePaiementDepense();
		    		 this.dateReceptionDepense=depense.getDateReceptionDepense();
		    		 this.montantHtDepense=depense.getMontantHtDepense();
		    		 this.tvaDepense=depense.getTvaDepense();
		    		 this.montantTtcDepense=depense.getMontantTtcDepense();
		    		 this.totalGeneralDepense=depense.getTotalGeneralDepense();
		    	 }
		    	       
		    	      
		    	        }
		    	
		 	
		    
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
       public AffectationType getTypeaffect() {
        return typeaffect;
    }
    
    public void setTypeaffect(AffectationType typeaffect) {
        this.typeaffect = typeaffect;
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
			public StatutFacture getStatut() {
				return statut;
			}
			public void setStatut(StatutFacture statut) {
				this.statut = statut;
			}
	public String getPdfPath() {
				return pdfPath;
			}
			public void setPdfPath(String pdfPath) {
				this.pdfPath = pdfPath;
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
			public static List<AffectationResponse> toArrayList(List<Affectation> affectations) {
			    List<AffectationResponse> list = new ArrayList<>();
			    for (Affectation affectation : affectations) {
			        list.add(new AffectationResponse(affectation));
			    }
			    return list;
			}

}
