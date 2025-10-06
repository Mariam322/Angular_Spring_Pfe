package tn.iit.request;

import java.time.LocalDate;
import java.util.List;

import tn.iit.entites.LignePieceCommerciale;
import tn.iit.entites.TypePieceCommerciale;
import tn.iit.entites.StatutFacture;
public class UpdateFactureRequest {

    private String numeroFacture;
    private LocalDate dateFacture;
    private LocalDate datePaiement;
    private Double montantHt;
    private Double tva;
    private Double montantTtc;
    private Double remise;
private String pdfPath ;
 private Double montantRestant;
private List<LignePieceCommerciale> lignes;
    private StatutFacture  statut;
    private boolean pdfGenerated;
   private TypePieceCommerciale typePieceCommerciale; 

    public String getNumeroFacture() {
        return numeroFacture;
    }
public List<LignePieceCommerciale> getLignes() {
	return lignes;
}

public void setLignes(List<LignePieceCommerciale> lignes) {
	this.lignes = lignes;
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
    public Double getMontantRestant() {
	return montantRestant;
}

public void setMontantRestant(Double montantRestant) {
	this.montantRestant = montantRestant;
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
    
}