package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


@IgnoreExtraProperties
public class User implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String contry;
    private String city;
    private String email;
    private String telephone;
    private String sexe;
    private Date birthday;
    private String presentation;
    private String photoProfl;

    private Map<String, Boolean> events = new HashMap<>();  // idEvent - boolean

    private HashSet<String> followers = new HashSet<>();  // Uid
    private HashSet<String> followings = new HashSet<>();  // Uid

    private HashSet<String> photos = new HashSet<>();   // idPhoto
    private HashSet<String> videos = new HashSet<>();   // idVidoe

    private HashSet<String> tickets = new HashSet<>();   // idTicket

    public User() {
    }

    public User(String username, String firstName, String lastName, String contry, String city, String email, String telephone, String sexe, Date birthday, String presentation, String photoProfl) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contry = contry;
        this.city = city;
        this.email = email;
        this.telephone = telephone;
        this.sexe = sexe;
        this.birthday = birthday;
        this.presentation = presentation;
        this.photoProfl = photoProfl;
    }

    public User(String username, String firstName, String lastName, String contry, String city, String email, String telephone, String sexe, Date birthday, String presentation, String photoProfl, Map<String, Boolean> events, HashSet<String> followers, HashSet<String> followings, HashSet<String> photos, HashSet<String> videos, HashSet<String> tickets) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contry = contry;
        this.city = city;
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

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Map<String, Boolean> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Boolean> events) {
        this.events = events;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    public void setFollowers(HashSet<String> followers) {
        this.followers = followers;
    }

    public HashSet<String> getFollowings() {
        return followings;
    }

    public void setFollowings(HashSet<String> followings) {
        this.followings = followings;
    }

    public HashSet<String> getPhotos() {
        return photos;
    }

    public void setPhotos(HashSet<String> photos) {
        this.photos = photos;
    }

    public HashSet<String> getVideos() {
        return videos;
    }

    public void setVideos(HashSet<String> videos) {
        this.videos = videos;
    }

    public HashSet<String> getTickets() {
        return tickets;
    }

    public void setTickets(HashSet<String> tickets) {
        this.tickets = tickets;
    }
}
