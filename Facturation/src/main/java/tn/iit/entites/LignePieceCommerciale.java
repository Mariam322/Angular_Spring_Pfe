package tn.iit.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;  // <-- Ajoutez ceci
@Entity
public class LignePieceCommerciale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int quantite;

    private Double prixUnitaire;

    private Double total;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "facture_id")
    private Facture facture;

    // Constructeur par défaut
    public LignePieceCommerciale() {
    }

    // Constructeur avec paramètres (optionnel)
    public LignePieceCommerciale(String description, int quantite, Double prixUnitaire, Facture facture) {
        this.description = description;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.facture = facture;
        recalculerTotal();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
        recalculerTotal();
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        recalculerTotal();
    }

    private void recalculerTotal() {
        if (this.prixUnitaire != null) {
            this.total = this.quantite * this.prixUnitaire;
        } else {
            this.total = null;
        }
    }

    public Double getTotal() {
        return total;
    }

    // On évite de modifier le total directement si on le recalcule à chaque changement
    public void setTotal(Double total) {
        this.total = total;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "LignePieceCommerciale{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", total=" + total +
                ", factureId=" + (facture != null ? facture.getId() : null) +
                '}';
    }
}
