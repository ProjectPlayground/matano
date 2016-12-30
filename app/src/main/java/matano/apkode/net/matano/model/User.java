package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class User implements Serializable {
    private String uid;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private String sexe;
    private Date birthday;
    private String presentation;
    private String photoProfl;

    private Map<String, String> events = new HashMap<>();  // idEvent - boolean

    private Map<String, String> followers = new HashMap<>();  // Uid
    private Map<String, String> followings = new HashMap<>();  // Uid

    private Map<String, String> photos = new HashMap<>(); // Uid   // idPhoto
    private Map<String, String> videos = new HashMap<>();   // idVidoe

    private Map<String, String> tickets = new HashMap<>();   // idTicket

    private Map<String, String> likes = new HashMap<>();   // idPhotos

    private Map<String, String> tchats = new HashMap<>();   // idPhotos


    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String telephone, String sexe, Date birthday, String presentation, String photoProfl) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.birthday = birthday;
        this.presentation = presentation;
        this.photoProfl = photoProfl;
    }

    public User(String uid, String username, String firstName, String lastName, String email, String telephone, String sexe, Date birthday, String presentation, String photoProfl, Map<String, String> events, Map<String, String> followers, Map<String, String> followings, Map<String, String> photos, Map<String, String> videos, Map<String, String> tickets, Map<String, String> likes, Map<String, String> tchats) {
        this.uid = uid;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.birthday = birthday;
        this.presentation = presentation;
        this.photoProfl = photoProfl;
        this.events = events;
        this.followers = followers;
        this.followings = followings;
        this.photos = photos;
        this.videos = videos;
        this.tickets = tickets;
        this.likes = likes;
        this.tchats = tchats;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPhotoProfl() {
        return photoProfl;
    }

    public void setPhotoProfl(String photoProfl) {
        this.photoProfl = photoProfl;
    }

    public Map<String, String> getEvents() {
        return events;
    }

    public void setEvents(Map<String, String> events) {
        this.events = events;
    }

    public Map<String, String> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, String> followers) {
        this.followers = followers;
    }

    public Map<String, String> getFollowings() {
        return followings;
    }

    public void setFollowings(Map<String, String> followings) {
        this.followings = followings;
    }

    public Map<String, String> getPhotos() {
        return photos;
    }

    public void setPhotos(Map<String, String> photos) {
        this.photos = photos;
    }

    public Map<String, String> getVideos() {
        return videos;
    }

    public void setVideos(Map<String, String> videos) {
        this.videos = videos;
    }

    public Map<String, String> getTickets() {
        return tickets;
    }

    public void setTickets(Map<String, String> tickets) {
        this.tickets = tickets;
    }

    public Map<String, String> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, String> likes) {
        this.likes = likes;
    }

    public Map<String, String> getTchats() {
        return tchats;
    }

    public void setTchats(Map<String, String> tchats) {
        this.tchats = tchats;
    }
}
