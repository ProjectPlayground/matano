package net.apkode.matano.interfaces;

import net.apkode.matano.model.Commentaire;

import java.util.List;

public interface ICommentaire {
    void getResponse(List<Commentaire> commentaires);

    void sendResponse(String response);
}
