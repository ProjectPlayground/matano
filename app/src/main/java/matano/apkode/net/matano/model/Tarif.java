package matano.apkode.net.matano.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Tarif implements Serializable {
    private String event;   //  idEvent
    private Integer tarif;
    private String detail;
    private Integer quantity;

    public Tarif() {
    }

    public Tarif(String event, Integer tarif, String detail, Integer quantity) {
        this.event = event;
        this.tarif = tarif;
        this.detail = detail;
        this.quantity = quantity;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getTarif() {
        return tarif;
    }

    public void setTarif(Integer tarif) {
        this.tarif = tarif;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
