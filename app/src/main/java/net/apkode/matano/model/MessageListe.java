package net.apkode.matano.model;

import java.io.Serializable;


public class MessageListe implements Serializable {
    private String nom;
    private String prenom;
    private String jour;
    private String horaire;
    private String message;
    private String image;

    public MessageListe(String nom, String prenom, String jour, String horaire, String message, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.jour = jour;
        this.horaire = horaire;
        this.message = message;
        this.image = image;
    }

    public MessageListe() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
