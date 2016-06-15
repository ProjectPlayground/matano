package net.apkode.matano.model;

import java.io.Serializable;

public class Login implements Serializable {
    private String telephone;
    private String password;

    public Login(String telephone, String password) {
        this.telephone = telephone;
        this.password = password;
    }

    public Login() {
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
}
