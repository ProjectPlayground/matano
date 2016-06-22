package net.apkode.matano.model;

import java.io.Serializable;

public class ImageGalerie implements Serializable {
    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    private String image;
    private String imagegalerie;

    public ImageGalerie(Integer id, String nom, String prenom, String telephone, String image, String imagegalerie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.image = image;
        this.imagegalerie = imagegalerie;
    }

    public ImageGalerie() {
    }

    public String getImagegalerie() {
        return imagegalerie;
    }

    public void setImagegalerie(String imagegalerie) {
        this.imagegalerie = imagegalerie;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
