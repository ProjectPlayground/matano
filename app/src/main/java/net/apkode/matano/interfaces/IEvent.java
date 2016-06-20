package net.apkode.matano.interfaces;

import net.apkode.matano.model.Event;

import java.util.List;

public interface IEvent {

    void getResponse(List<Event> events);
}