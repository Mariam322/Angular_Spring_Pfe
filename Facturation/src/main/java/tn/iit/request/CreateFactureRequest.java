package tn.iit.request;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.TypePieceCommerciale;
import tn.iit.entites.LignePieceCommerciale;
import tn.iit.entites.StatutFacture;
@AllArgsConstructor
@NoArgsConstructor
public class CreateFactureRequest {

 private String numeroFacture;
    private LocalDate dateFacture;
        private LocalDate datePaiement;
    private Double montantHt;
    private Double tva;
    private Double montantTtc;
    private Double remise;
 private Double montantRestant;
    private StatutFacture  statut;
    private String pdfPath ;
       private Long idCompaign;
     // Nouveau champ : Type de pièce commerciale (FACTURE, DEVIS)
    private TypePieceCommerciale typePieceCommerciale;
    private List<LignePieceCommerciale> lignes;

    // Infos compaign ajoutées
   private String reference;
    private String nom;
    private String address;
    private String email;
    private String telephone;
    private String pays;
    private String ville;
    private boolean pdfGenerated;
    private TypeClientFournisseur type; 
    private Date createdAt;
 public List<LignePieceCommerciale> getLignes() {
		return lignes;
	}

	public void setLignes(List<LignePieceCommerciale> lignes) {
		this.lignes = lignes;
	}

    // Getters et setters

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

public Long getIdCompaign() {
        return idCompaign;
    }

    public void setIdCompaign(Long idCompaign) {
        this.idCompaign = idCompaign;
    }

    public StatutFacture  getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture  statut) {
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

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
 public boolean isPdfGenerated() {
		return pdfGenerated;
	}
	public void setPdfGenerated(boolean pdfGenerated) {
		this.pdfGenerated = pdfGenerated;
	}
 public Double getMontantRestant() {
	return montantRestant;
}

public void setMontantRestant(Double montantRestant) {
	this.montantRestant = montantRestant;
}
    
}