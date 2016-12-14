package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

@IgnoreExtraProperties
public class Photo implements Serializable {
    private String event;   // idEvent
    private String user;    //  Uidowner
    private String url;
    private Date date;      // enregistrement
    private HashSet<String> likes = new HashSet<>(); // Uid

    public Photo() {
    }

    public Photo(String event, String user, String url, Date date, HashSet<String> likes) {
        this.event = event;
        this.user = user;
        this.url = url;
        this.date = date;
        this.likes = likes;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashSet<String> getLikes() {
        return likes;
    }

    public void setLikes(HashSet<String> likes) {
        this.likes = likes;
    }
}
