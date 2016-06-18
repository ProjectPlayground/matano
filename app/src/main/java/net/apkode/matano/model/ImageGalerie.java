package net.apkode.matano.model;

import java.io.Serializable;

public class ImageGalerie implements Serializable {
    private String image;

    public ImageGalerie(String image) {
        this.image = image;
    }

    public ImageGalerie() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
