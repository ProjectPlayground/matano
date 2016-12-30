package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;

@IgnoreExtraProperties
public class Tchat implements Serializable {
    private String event; // idEvent
    private String user;    // userUid
    private Date date;  // enregistrement
    private String messsage;
    private String photo;   // photouid
    private String video;   // videoUid


    public Tchat() {
    }

    public Tchat(String event, String user, Date date, String messsage, String photo, String video) {
        this.event = event;
        this.user = user;
        this.date = date;
        this.messsage = messsage;
        this.photo = photo;
        this.video = video;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
