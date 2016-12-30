package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Photo implements Serializable {
    private String event;   // idEvent
    private String user;    //  Uidowner
    private String url;
    private Date date;      // enregistrement
    private String status;
    private Map<String, String> likes = new HashMap<>();   // userUid

    public Photo() {
    }

    public Photo(String event, String user, String url, Date date, String status, Map<String, String> likes) {
        this.event = event;
        this.user = user;
        this.url = url;
        this.date = date;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, String> likes) {
        this.likes = likes;
    }
}
