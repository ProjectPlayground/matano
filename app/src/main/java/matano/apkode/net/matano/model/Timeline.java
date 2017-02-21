package matano.apkode.net.matano.model;

import android.app.Activity;
import android.content.Context;

import java.io.Serializable;
import java.util.Date;


public class Timeline implements Serializable {
    String username;
    String photoProfl;
    String url;
    String title;
    Date date;
    String dateString;
    Context context;
    Activity activity;
    String eventUid;
    String userUid;
    String photoUid;
    String currentUserUid;


    public Timeline() {
    }

    public Timeline(String username, String photoProfl, String url, String title, Date date, String dateString, Context context, Activity activity, String eventUid, String userUid, String photoUid, String currentUserUid) {
        this.username = username;
        this.photoProfl = photoProfl;
        this.url = url;
        this.title = title;
        this.date = date;
        this.dateString = dateString;
        this.context = context;
        this.activity = activity;
        this.eventUid = eventUid;
        this.userUid = userUid;
        this.photoUid = photoUid;
        this.currentUserUid = currentUserUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoProfl() {
        return photoProfl;
    }

    public void setPhotoProfl(String photoProfl) {
        this.photoProfl = photoProfl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getEventUid() {
        return eventUid;
    }

    public void setEventUid(String eventUid) {
        this.eventUid = eventUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getCurrentUserUid() {
        return currentUserUid;
    }

    public void setCurrentUserUid(String currentUserUid) {
        this.currentUserUid = currentUserUid;
    }

    public String getPhotoUid() {
        return photoUid;
    }

    public void setPhotoUid(String photoUid) {
        this.photoUid = photoUid;
    }
}
