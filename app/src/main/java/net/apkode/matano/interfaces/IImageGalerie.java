package net.apkode.matano.interfaces;

import net.apkode.matano.model.ImageGalerie;

import java.util.List;

public interface IImageGalerie {

    void getResponse(List<ImageGalerie> imageGaleries);

    void responseSendImageGalerie(String response);
}
