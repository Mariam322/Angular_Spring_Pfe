package tn.iit.request;

import tn.iit.entites.TypeClientFournisseur;
import java.util.Date;

public class CreateCompainRequest {

    private String reference;
    private String nom;
    private String address;
    private String email;
    private String telephone;
    private String pays;
    private String ville;
    private TypeClientFournisseur type;
    private Date createdAt;
 private Date updatedAt;
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
     public Date getUpdatedAt() {
        return updatedAt; 
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt; 
    }
}
