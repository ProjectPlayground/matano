package net.apkode.matano.model;


import java.io.Serializable;

public class Feedback implements Serializable {
    private String telephone;
    private String message;

    public Feedback(String telephone, String message) {
        this.telephone = telephone;
        this.message = message;
    }

    public Feedback() {
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
