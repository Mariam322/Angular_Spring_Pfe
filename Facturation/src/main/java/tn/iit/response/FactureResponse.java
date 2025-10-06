package tn.iit.response;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tn.iit.entites.TypePieceCommerciale;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.Facture;
import tn.iit.entites.StatutFacture;
import tn.iit.entites.LignePieceCommerciale;
import lombok.NoArgsConstructor;
@NoArgsConstructor

public class FactureResponse {

    private Long id;
    private String numeroFacture;
    private LocalDate dateFacture;
    private LocalDate datePaiement;
    private Double montantHt;
    private Double tva;
    private Double montantTtc;
    private Double remise;
    private Long idCompaign;
    private StatutFacture  statut;
    private String pdfPath ;
      private TypePieceCommerciale typePieceCommerciale; 
      private List<LignePieceCommerciale> lignes=new ArrayList<>();
    private String reference;
    private String nom;
    private String address;
    private String email;
    private String telephone;
    private String pays;
    private String ville;
    private TypeClientFournisseur type;
    private Date createdAt;
     private Double montantRestant;

    // Constructeur avec Facture seulement
    // In tn.iit.response.FactureResponse class
public FactureResponse(Facture facture) {
    this.id = facture.getId();
    this.numeroFacture = facture.getNumeroFacture();
        this.dateFacture = facture.getDateFacture();
        this.datePaiement = facture.getDatePaiement();
        this.montantHt = facture.getMontantHt();
        this.tva = facture.getTva();
        this.montantTtc = facture.getMontantTtc();
        this.remise = facture.getRemise();
      this.pdfPath=facture.getPdfPath();
        this.statut = facture.getStatut();
        this.lignes=facture.getLignes();
         this.typePieceCommerciale = facture.getType();
         this.montantRestant=facture.getMontantRestant();
       
}

    // Constructeur avec Facture et CompaignResponse
    public FactureResponse(Facture facture, CompaignResponse compaign) {
        this(facture); // Appelle le premier constructeur
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
    
    public Long getIdCompaign() {
        return idCompaign;
    }

    public void setIdCompaign(Long idCompaign) {
        this.idCompaign = idCompaign;
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

public List<LignePieceCommerciale> getLignes() {
    return lignes;
}

public void setLignes(List<LignePieceCommerciale> lignes) {
    this.lignes = lignes;
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
public Double getMontantRestant() {
	return montantRestant;
}

public void setMontantRestant(Double montantRestant) {
	this.montantRestant = montantRestant;
}
    
	// Méthode pour convertir une liste de Factures en FactureResponse
    public static List<FactureResponse> toArrayList(List<Facture> factures) {
        List<FactureResponse> list = new ArrayList<>();
        for (Facture facture : factures) {
            list.add(new FactureResponse(facture));
        }
        return list;
    }
}