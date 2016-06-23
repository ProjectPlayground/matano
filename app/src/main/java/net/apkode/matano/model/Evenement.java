package net.apkode.matano.model;

import java.io.Serializable;

/**
 * Created by brabo on 6/2/16.
 */
public class Evenement implements Serializable {
    private Integer id;
    private String categorie;
    private String rubrique;
    private String titre;
    private String tarif;
    private String lieu;
    private String presentation;
    private String image;
    private String horaire;
    private String lien;
    private String jour;
    private String video;
    private String imagefull;

    public Evenement(Integer id, String categorie, String rubrique, String titre, String tarif, String lieu, String presentation, String image, String horaire, String lien, String jour, String video, String imagefull) {
        this.id = id;
        this.categorie = categorie;
        this.rubrique = rubrique;
        this.titre = titre;
        this.tarif = tarif;
        this.lieu = lieu;
        this.presentation = presentation;
        this.image = image;
        this.horaire = horaire;
        this.lien = lien;
        this.jour = jour;
        this.video = video;
        this.imagefull = imagefull;
    }

    public Evenement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getRubrique() {
        return rubrique;
    }

    public void setRubrique(String rubrique) {
        this.rubrique = rubrique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
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

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImagefull() {
        return imagefull;
    }

    public void setImagefull(String imagefull) {
        this.imagefull = imagefull;
    }
}
