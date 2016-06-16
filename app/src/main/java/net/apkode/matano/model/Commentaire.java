package net.apkode.matano.model;

import java.io.Serializable;

/**
 * Created by brabo on 6/14/16.
 */
public class Commentaire implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String jour;
    private String horaire;
    private String image;
    private String commentaire;

    public Commentaire(Long id, String nom, String prenom, String jour, String horaire, String image, String commentaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.jour = jour;
        this.horaire = horaire;
        this.image = image;
        this.commentaire = commentaire;
    }

    public Commentaire() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
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
