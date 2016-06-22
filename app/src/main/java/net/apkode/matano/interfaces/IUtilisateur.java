package net.apkode.matano.interfaces;

import net.apkode.matano.model.Utilisateur;

public interface IUtilisateur {
    void responseUpdate(String response);

    void responseInscription(String response);

    void responseConnexion(String response);

    void responseGetUtilisateur(Utilisateur utilisateur);
}
