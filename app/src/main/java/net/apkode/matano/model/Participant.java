package net.apkode.matano.model;

import java.io.Serializable;

/**
 * Created by brabo on 6/14/16.
 */
public class Participant implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String image;

    public Participant(Long id, String nom, String prenom, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
    }

    public Participant() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
