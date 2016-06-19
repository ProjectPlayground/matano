package net.apkode.matano.model;

import java.io.Serializable;

public class Commentaire implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    private String jour;
    private String image;
    private String commentaire;

    public Commentaire(Integer id, String nom, String prenom, String telephone, String jour, String image, String commentaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.jour = jour;
        this.image = image;
        this.commentaire = commentaire;
    }

    public Commentaire() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
