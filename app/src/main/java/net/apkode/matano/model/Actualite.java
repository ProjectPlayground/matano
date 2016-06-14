package net.apkode.matano.model;

import java.io.Serializable;

/**
 * Created by brabo on 6/14/16.
 */
public class Actualite implements Serializable {
    private Long id;
    private String jour;
    private String horaire;
    private String actualite;

    public Actualite(Long id, String jour, String horaire, String actualite) {
        this.id = id;
        this.jour = jour;
        this.horaire = horaire;
        this.actualite = actualite;
    }

    public Actualite() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getActualite() {
        return actualite;
    }

    public void setActualite(String actualite) {
        this.actualite = actualite;
    }
}
