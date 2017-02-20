package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Event implements Serializable {
    private String title;
    private String category;
    private String subCategory;
    private String country;
    private String city;
    private String place;
    private String address;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Date date;
    private String presentation;
    private String photoProfil; // idPhoto
    private String videoProfil; //  idVideo
    private String tarification; // gratuit - payant - free

    private Map<String, String> tickets = new HashMap<>(); // Uid
    private Map<String, String> users = new HashMap<>();  // uId - status (0, 1, 2)
    private Map<String, String> photos = new HashMap<>(); // Uid
    private Map<String, Tchat> tchats = new HashMap<>(); // tchatUid
    private Map<String, Programme> programmes = new HashMap<>(); // programmeUid



    public Event() {
    }

    public Event(String title, String category, String subCategory, String country, String city, String place, String address, Double longitude, Double latitude, Double altitude, Date date, String presentation, String photoProfil, String videoProfil, String tarification, Map<String, String> tickets, Map<String, String> users, Map<String, String> photos, Map<String, Tchat> tchats, Map<String, Programme> programmes) {
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.country = country;
        this.city = city;
        this.place = place;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.date = date;
        this.presentation = presentation;
        this.photoProfil = photoProfil;
        this.videoProfil = videoProfil;
        this.tarification = tarification;
        this.tickets = tickets;
        this.users = users;
        this.photos = photos;
        this.tchats = tchats;
        this.programmes = programmes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getPhotoProfil() {
        return photoProfil;
    }

    public void setPhotoProfil(String photoProfil) {
        this.photoProfil = photoProfil;
    }

    public String getVideoProfil() {
        return videoProfil;
    }

    public void setVideoProfil(String videoProfil) {
        this.videoProfil = videoProfil;
    }

    public String getTarification() {
        return tarification;
    }

    public void setTarification(String tarification) {
        this.tarification = tarification;
    }

    public Map<String, String> getTickets() {
        return tickets;
    }

    public void setTickets(Map<String, String> tickets) {
        this.tickets = tickets;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public Map<String, String> getPhotos() {
        return photos;
    }

    public void setPhotos(Map<String, String> photos) {
        this.photos = photos;
    }

    public Map<String, Tchat> getTchats() {
        return tchats;
    }

    public void setTchats(Map<String, Tchat> tchats) {
        this.tchats = tchats;
    }

    public Map<String, Programme> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(Map<String, Programme> programmes) {
        this.programmes = programmes;
    }
}
