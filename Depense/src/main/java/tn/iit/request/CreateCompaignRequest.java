package tn.iit.request;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tn.iit.entites.TypeClientFournisseur;
public class CreateCompaignRequest {

   private String reference;
    private String nom;
    private String address;
    private String email;
    private String telephone;
    private String pays;
    private String ville;
    private TypeClientFournisseur type; 
    private Date createdAt;

    // Constructeur
    public CreateCompaignRequest(String reference, String nom, String address, String email, String telephone,
                                 String pays, String ville, TypeClientFournisseur type, Date createdAt) {
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

    // Getters & Setters
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