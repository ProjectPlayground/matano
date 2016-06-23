package net.apkode.matano.interfaces;

import net.apkode.matano.model.Evenement;

import java.util.List;

public interface IEvenement {

    void getResponses(List<Evenement> evenements);

    void getResponsesCulture(List<Evenement> evenements);

    void getResponsesEducation(List<Evenement> evenements);

    void getResponsesSport(List<Evenement> evenements);

    void getResponse(List<Evenement> evenements);
}