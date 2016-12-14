package matano.apkode.net.matano.model;

import java.io.Serializable;
import java.util.Date;

public class Chat implements Serializable {
    private String event;   // idEvent
    private String user;    // Uid
    private String messsage;
    private Date date;  // enregistrement

    public Chat() {
    }

    public Chat(String event, String user, String messsage, Date date) {
        this.event = event;
        this.user = user;
        this.messsage = messsage;
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
