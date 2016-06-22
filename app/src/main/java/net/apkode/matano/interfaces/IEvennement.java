package net.apkode.matano.interfaces;

import net.apkode.matano.model.Evennement;

import java.util.List;

public interface IEvennement {

    void getResponses(List<Evennement> evennements);

    void getResponse(List<Evennement> evennements);
}