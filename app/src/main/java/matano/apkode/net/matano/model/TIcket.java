package matano.apkode.net.matano.model;

import java.io.Serializable;
import java.util.Date;


public class TIcket implements Serializable {
    private String code;        // code bare
    private String event;       // idEvent
    private String user;        // idUser
    private String Tarif;      // idTarif
    private Integer quantity;
    private Date date;
    private Boolean status;

    public TIcket() {
    }

    public TIcket(String code, String event, String user, String tarif, Integer quantity, Date date, Boolean status) {
        this.code = code;
        this.event = event;
        this.user = user;
        Tarif = tarif;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTarif() {
        return Tarif;
    }

    public void setTarif(String tarif) {
        Tarif = tarif;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
