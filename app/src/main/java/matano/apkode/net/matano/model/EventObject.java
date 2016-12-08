package matano.apkode.net.matano.model;

import java.io.Serializable;

/**
 * Created by smalllamartin on 12/5/16.
 */

public class EventObject implements Serializable {
    private String categorie;

    public EventObject() {
    }

    public EventObject(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
