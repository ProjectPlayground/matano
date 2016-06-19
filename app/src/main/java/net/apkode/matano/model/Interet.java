package net.apkode.matano.model;

import java.io.Serializable;

public class Interet implements Serializable {
    private String interesse;

    public Interet(String interesse) {
        this.interesse = interesse;
    }

    public Interet() {
    }

    public String getInteresse() {
        return interesse;
    }

    public void setInteresse(String interesse) {
        this.interesse = interesse;
    }
}
