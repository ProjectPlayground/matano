package net.apkode.matano.interfaces;

import net.apkode.matano.model.ImageGalerie;

import java.util.List;

/**
 * Created by brabo on 6/19/16.
 */
public interface IImageGalerie {

    void getResponse(List<ImageGalerie> imageGaleries);

    void sendResponse(String response);
}