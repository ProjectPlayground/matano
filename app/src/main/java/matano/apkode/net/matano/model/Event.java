package matano.apkode.net.matano.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class Event implements Serializable {
    private String title;
    private String category;
    private String subCategory;
    private String contry;
    private String city;
    private String place;
    private String address;
    private Double longitude;
    private Double latitude;
    private Double altitude;
    private Date dateStart;
    private Date dateEnd;
    private String presentation;
    private String photoProfil; // idPhoto
    private String videoProfil; //  idVideo
    private String tarification; // gratuit - payant - free

    private List<String> tarifs = new ArrayList<>(); // Uid
    private Map<String, Integer> users = new HashMap<>();  // uId - status (0, 1, 2)


    public Event() {
    }

    public Event(String title, String category, String subCategory, String contry, String city, String place, String address, Double longitude, Double latitude, Double altitude, Date dateStart, Date dateEnd, String presentation, String photoProfil, String videoProfil, String tarification, List<String> tarifs, Map<String, Integer> users) {
        this.title = title;
        this.category = category;
        this.subCategory = subCategory;
        this.contry = contry;
        this.city = city;
        this.place = place;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.presentation = presentation;
        this.photoProfil = photoProfil;
        this.videoProfil = videoProfil;
        this.tarification = tarification;
        this.tarifs = tarifs;
        this.users = users;
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

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
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

    public List<String> getTarifs() {
        return tarifs;
    }

    public void setTarifs(List<String> tarifs) {
        this.tarifs = tarifs;
    }

    public Map<String, Integer> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Integer> users) {
        this.users = users;
    }
}
