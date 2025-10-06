package tn.iit.event;

import java.time.LocalDate;
import java.util.List;

import tn.iit.entites.LignePieceCommerciale;
import tn.iit.entites.TypePieceCommerciale;
public class FactureCreatedEvent {
	private Long id;
	 private String numeroFacture;
	    private LocalDate dateFacture;
	    private LocalDate datePaiement;
	    private Double montantHt;
	    private Double tva;
	    private Double montantTtc;
	    private Double remise;
	    private boolean pdfGenerated;
	    private String statut;
	      private TypePieceCommerciale typePieceCommerciale; 
    private Long idCompaign;
    private List<LignePieceCommerciale> lignes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getIdCompaign() {
		return idCompaign;
	}
	public void setIdCompaign(Long idCompaign) {
		this.idCompaign = idCompaign;
	}
	public List<LignePieceCommerciale> getLignes() {
		return lignes;
	}
	public void setLignes(List<LignePieceCommerciale> lignes) {
		this.lignes = lignes;
	}
		public boolean isPdfGenerated() {
		return pdfGenerated;
	}
	public void setPdfGenerated(boolean pdfGenerated) {
		this.pdfGenerated = pdfGenerated;
	}
    
    
}

