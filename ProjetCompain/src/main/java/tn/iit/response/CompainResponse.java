package tn.iit.response;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tn.iit.entites.TypeClientFournisseur;
import tn.iit.entites.Compain;

public class CompainResponse {

    private Long id;
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

    // Constructeur à partir de l'entité Compain
    public CompainResponse(Compain compain) {
        this.id = compain.getId();
        this.reference = compain.getReference();
        this.nom = compain.getNom();
        this.address = compain.getAddress();
        this.email = compain.getEmail();
        this.telephone = compain.getTelephone();
        this.pays = compain.getPays();
        this.ville = compain.getVille();
        this.type = compain.getType();
        this.createdAt = compain.getCreatedAt();
        this.updatedAt = compain.getUpdatedAt();
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Méthode utilitaire pour convertir une liste de Compain en liste de CompainResponse
    public static List<CompainResponse> toArrayList(List<Compain> compains) {
        List<CompainResponse> responseList = new ArrayList<>();
        for (Compain c : compains) {
            responseList.add(new CompainResponse(c));
        }
        return responseList;
    }
}

