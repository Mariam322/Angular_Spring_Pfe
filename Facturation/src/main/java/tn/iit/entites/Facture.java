package tn.iit.entites;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import jakarta.persistence.PrePersist;
import lombok.NoArgsConstructor;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "facture")
public class Facture {

    // Identifiants
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_facture", unique = true)
    private String numeroFacture;

    // Dates
    @Column(name = "date_facture")
    private LocalDate dateFacture;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    // Montants et taxes
    @Column(name = "montant_ht")
    private Double montantHt;

    @Column(name = "tva")
    private Double tva;

    @Column(name = "montant_ttc")
    private Double montantTtc;

    @Column(name = "remise")
    private Double remise;


    @Column(name = "statut")
 @Enumerated(EnumType.STRING) // Stocke le nom de l'enum en base
    private StatutFacture statut;
    @Column(name = "path")
    private String pdfPath;
       @Column(name = "montantRestant")
    private Double montantRestant;
     private String driveFileId;
  @Enumerated(EnumType.STRING)
    @Column(name = "type_piece") // Ajout du type : FACTURE ou DEVIS
    private TypePieceCommerciale type;
    // R�f�rence externe
    @Column(name = "id_compaign")
    private Long idCompaign;

@OneToMany(mappedBy = "facture", cascade = CascadeType.ALL, orphanRemoval = true)
private List<LignePieceCommerciale> lignes=new ArrayList<>();
    // Getters et Setters
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



    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }
   
      public TypePieceCommerciale getType() { return type; }
    public void setType(TypePieceCommerciale type) { this.type = type; }
 

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


    public String getPdfPath() {
	return pdfPath;
}

public void setPdfPath(String pdfPath) {
	this.pdfPath = pdfPath;
}
 public String getDriveFileId() {
        return driveFileId;
    }

    public void setDriveFileId(String driveFileId) {
        this.driveFileId = driveFileId;
    }
public Double getMontantRestant() {
	return montantRestant;
}

public void setMontantRestant(Double montantRestant) {
	this.montantRestant = montantRestant;
}
@Override
public String toString() {
	return "Facture [id=" + id + ", numeroFacture=" + numeroFacture + ", dateFacture=" + dateFacture + ", datePaiement="
			+ datePaiement + ", montantHt=" + montantHt + ", tva=" + tva + ", montantTtc=" + montantTtc + ", remise="
			+ remise + ", statut=" + statut + ", pdfPath=" + pdfPath + ", idCompaign=" + idCompaign + "]";
}

 @PrePersist
    public void init() {
        if (this.statut == null) {
            this.statut = StatutFacture.NON_PAYEE;
        }
        if (this.montantRestant == null) {
            this.montantRestant = this.montantTtc;
        }
    }


	
}