package net.apkode.matano.model;

import java.io.Serializable;

public class Actualite implements Serializable {
    private Integer id;
    private String jour;
    private String actualite;

    public Actualite(Integer id, String jour, String actualite) {
        this.id = id;
        this.jour = jour;
        this.actualite = actualite;
    }

    public Actualite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }


    public String getActualite() {
        return actualite;
    }

    public void setActualite(String actualite) {
        this.actualite = actualite;
    }
}
