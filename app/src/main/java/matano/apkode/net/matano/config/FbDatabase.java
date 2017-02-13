package matano.apkode.net.matano.config;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Tarif;
import matano.apkode.net.matano.model.Tchat;
import matano.apkode.net.matano.model.Ticket;
import matano.apkode.net.matano.model.User;
import matano.apkode.net.matano.model.Video;

public class FbDatabase {

    private FirebaseDatabase database;

    private DatabaseReference refRoot;

    private Event event;
    private Photo photo;
    private Tchat tchat;
    private User user;
    private Ticket ticket;
    private Video video;
    private Tarif tarif;

    public FbDatabase() {
        database = FirebaseDatabase.getInstance();
        refRoot = database.getReference();
    }

    public DatabaseReference getRefRoot() {
        return refRoot;
    }

    public DatabaseReference getRefEvents() {
        return refRoot.child(Utils.FIREBASE_DATABASE_EVENTS);
    }

    public DatabaseReference getRefPhotos() {
        return refRoot.child(Utils.FIREBASE_DATABASE_PHOTOS);
    }

    public DatabaseReference getRefTchats() {
        return refRoot.child(Utils.FIREBASE_DATABASE_TCHATS);
    }

    public DatabaseReference getRefUsers() {
        return refRoot.child(Utils.FIREBASE_DATABASE_USERS);
    }

    public DatabaseReference getRefTickets() {
        return refRoot.child(Utils.FIREBASE_DATABASE_TICKETS);
    }

    public DatabaseReference getRefVideos() {
        return refRoot.child(Utils.FIREBASE_DATABASE_TICKETS);
    }

    public DatabaseReference getRefTarifs() {
        return refRoot.child(Utils.FIREBASE_DATABASE_TARIFS);
    }


    // Event
    public DatabaseReference getRefEvent(String eventUid) {
        return getRefEvents().child(eventUid);
    }

    public DatabaseReference getRefEventTickets(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_TICKETS);
    }

    public DatabaseReference getRefEventTicket(String eventUid, String ticketUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_TICKETS).child(ticketUid);
    }

    public DatabaseReference getRefEventUsers(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_USERS);
    }

    public DatabaseReference getRefEventUser(String eventUid, String userUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_USERS).child(userUid);
    }

    public DatabaseReference getRefEventPhotos(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_PHOTOS);
    }

    public DatabaseReference getRefEventPhoto(String eventUid, String photoUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_PHOTOS).child(photoUid);
    }

    public DatabaseReference getRefEventTchats(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_TCHATS);
    }

    public DatabaseReference getRefEventTchat(String eventUid, String tchatUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_DATABASE_EVENT_TCHATS).child(tchatUid);
    }


    // Photo
    public DatabaseReference getRefPhoto(String photoUid) {
        return getRefPhotos().child(photoUid);
    }

    public DatabaseReference getRefPhotoLikes(String photoUid) {
        return getRefPhoto(photoUid).child(Utils.FIREBASE_DATABASE_PHOTO_LIKES);
    }

    public DatabaseReference getRefPhotoLike(String photoUid, String likeUid) {
        return getRefPhoto(photoUid).child(Utils.FIREBASE_DATABASE_PHOTO_LIKES).child(likeUid);
    }

    // Tchat
    public DatabaseReference getRefTchat(String tchatUid) {
        return getRefTchats().child(tchatUid);
    }

    // User
    public DatabaseReference getRefUser(String userUid) {
        return getRefUsers().child(userUid);
    }

    public DatabaseReference getRefUserEvents(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_EVENTS);
    }

    public DatabaseReference getRefUserEvent(String userUid, String eventUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_EVENTS).child(eventUid);
    }

    public DatabaseReference getRefUserFollowers(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_FOLLOWERS);
    }

    public DatabaseReference getRefUserFollower(String userUid, String followerUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_FOLLOWERS).child(followerUid);
    }

    public DatabaseReference getRefUserFollowings(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_FOLLOWINGS);
    }

    public DatabaseReference getRefUserFollowing(String userUid, String followingUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_FOLLOWINGS).child(followingUid);
    }

    public DatabaseReference getRefUserPhotos(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_PHOTOS);
    }

    public DatabaseReference getRefUserPhoto(String userUid, String photoUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_PHOTOS).child(photoUid);
    }

    public DatabaseReference getRefUserVideos(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_VIDEOS);
    }

    public DatabaseReference getRefUserVideo(String userUid, String videoUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_VIDEOS).child(videoUid);
    }

    public DatabaseReference getRefUserTickets(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_TICKETS);
    }

    public DatabaseReference getRefUserTicket(String userUid, String ticketUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_TICKETS).child(ticketUid);
    }

    public DatabaseReference getRefUserLikes(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_LIKES);
    }

    public DatabaseReference getRefUserLike(String userUid, String likeUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_LIKES).child(likeUid);
    }

    public DatabaseReference getRefUserTchats(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_TCHATS);
    }

    public DatabaseReference getRefUserTchat(String userUid, String tchatUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_DATABASE_USER_TCHATS).child(tchatUid);
    }

    // Ticket
    public DatabaseReference getRefTicket(String ticketUid) {
        return getRefTickets().child(ticketUid);
    }

    // Video
    public DatabaseReference getRefVideo(String videoUid) {
        return getRefVideos().child(videoUid);
    }

    // Video
    public DatabaseReference getRefTarif(String tarifUid) {
        return getRefTarifs().child(tarifUid);
    }

}
