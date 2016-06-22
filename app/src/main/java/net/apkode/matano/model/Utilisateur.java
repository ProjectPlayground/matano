package net.apkode.matano.model;

import java.io.Serializable;

public class Utilisateur implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    private String password;
    private String email;
    private String presentation;
    private String image;

    public Utilisateur(Integer id, String nom, String prenom, String telephone, String password, String email, String presentation, String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.password = password;
        this.email = email;
        this.presentation = presentation;
        this.image = image;
    }

    public Utilisateur(String nom, String prenom, String telephone, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.password = password;
    }

    public Utilisateur(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public Utilisateur() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
