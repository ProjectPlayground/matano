package matano.apkode.net.matano.config;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bumptech.glide.request.target.ViewTarget;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Tchat;
import matano.apkode.net.matano.model.Ticket;
import matano.apkode.net.matano.model.User;
import matano.apkode.net.matano.model.Video;


public class App extends Application implements Application.ActivityLifecycleCallbacks {
    // TODO Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sEj0jBiSTz80lQPVKAkj5a2BY";
    private static final String TWITTER_SECRET = "J4ZDcAZOeRrJ3e3Is88iKMmSOwdDtcbpM0oVhedXRVypXwOx7B";

    private LocalStorage localStorage;

    private String currentUserContry;
    private String currentUserCity;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser;

    private String currentUserUid;

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private DatabaseReference refDatabaseRoot;
    private StorageReference refStorageRoot;

    private DatabaseReference refEvents;
    private DatabaseReference refPhotos;
    private DatabaseReference refTchats;
    private DatabaseReference refUsers;
    private DatabaseReference refTickets;
    private DatabaseReference refVideos;

    private Event event;
    private Photo photo;
    private Tchat tchat;
    private User user;
    private Ticket ticket;
    private Video video;


    private ArrayList<Event> events;
    private ArrayList<Photo> photos;
    private ArrayList<Tchat> tchats;
    private ArrayList<User> users;
    private ArrayList<Ticket> tickets;
    private ArrayList<Video> videos;

    private ArrayList<Ticket> eventTickets;
    private ArrayList<User> eventUsers;
    private ArrayList<Photo> eventPhotos;
    private ArrayList<User> photoLikes;  // idUser
    private ArrayList<Event> userEvents;
    private ArrayList<User> userFollowers;
    private ArrayList<User> userFollowings;
    private ArrayList<Photo> userPhotos;
    private ArrayList<Video> userVideos;
    private ArrayList<User> userLikes;  // idPhoto

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        // GLIDE
        ViewTarget.setTagId(R.id.glide_tag);

        // LOCAL_STORAGE
        localStorage = new LocalStorage(this);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        // LOGIN
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null || !localStorage.isContryStored() || currentUserContry == null) {
                    goLoginActivity();
                } else {
                    currentUserUid = currentUser.getUid();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        database = FirebaseDatabase.getInstance();
        refDatabaseRoot = database.getReference();

        storage = FirebaseStorage.getInstance();
        refStorageRoot = storage.getReference();

        refEvents = refDatabaseRoot.child(Utils.FIREBASE_CHILD_EVENTS);
        refPhotos = refDatabaseRoot.child(Utils.FIREBASE_CHILD_PHOTOS);
        refTchats = refDatabaseRoot.child(Utils.FIREBASE_CHILD_TCHATS);
        refUsers = refDatabaseRoot.child(Utils.FIREBASE_CHILD_USERS);
        refTickets = refDatabaseRoot.child(Utils.FIREBASE_CHILD_TICKETS);
        refVideos = refDatabaseRoot.child(Utils.FIREBASE_CHILD_TICKETS);

    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    public DatabaseReference getRefEvents() {
        return refEvents;
    }

    public DatabaseReference getRefPhotos() {
        return refPhotos;
    }

    public DatabaseReference getRefTchats() {
        return refTchats;
    }

    public DatabaseReference getRefUsers() {
        return refUsers;
    }

    public DatabaseReference getRefTickets() {
        return refTickets;
    }

    public DatabaseReference getRefVideos() {
        return refVideos;
    }


    public DatabaseReference getRefEvent(String eventUid) {
        return refEvents.child(eventUid);
    }

    public DatabaseReference getRefPhoto(String photoUid) {
        return refPhotos.child(photoUid);
    }

    public DatabaseReference getRefTchat(String tchatUid) {
        return refTchats.child(tchatUid);
    }

    public DatabaseReference getRefUser(String userUid) {
        return refUsers.child(userUid);
    }

    public DatabaseReference getRefTicket(String ticketUid) {
        return refTickets.child(ticketUid);
    }

    public DatabaseReference getRefVideo(String videoUid) {
        return refVideos.child(videoUid);
    }


    public DatabaseReference getRefEventTickets(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_CHILD_EVENT_TICKETS);
    }

    public DatabaseReference getRefEventUsers(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_CHILD_EVENT_USERS);
    }

    public DatabaseReference getRefEventPhotos(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_CHILD_EVENT_PHOTOS);
    }

    public DatabaseReference getRefEventTchats(String eventUid) {
        return getRefEvent(eventUid).child(Utils.FIREBASE_CHILD_EVENT_TCHATS);
    }

    public DatabaseReference getRefPhotoLikes(String photoUid) {
        return getRefPhoto(photoUid).child(Utils.FIREBASE_CHILD_PHOTO_LIKES);
    }

    public DatabaseReference getRefUserEvents(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_EVENTS);
    }

    public DatabaseReference getRefUserFollowers(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_FOLLOWERS);
    }

    public DatabaseReference getRefUserFollowings(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_FOLLOWINGS);
    }

    public DatabaseReference getRefUserPhotos(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_PHOTOS);
    }

    public DatabaseReference getRefUserVideos(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_VIDEOS);
    }

    public DatabaseReference getRefUserLikes(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_LIKES);
    }

    public DatabaseReference getRefUserTickets(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_TICKETS);
    }

    public DatabaseReference getRefUserTchats(String userUid) {
        return getRefUser(userUid).child(Utils.FIREBASE_CHILD_USER_TCHATS);
    }

    public ArrayList<Event> getEvents() {
        events = new ArrayList<>();
        getRefEvents().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    events.add(snap.getValue(Event.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                events = null;
            }
        });
        return events;
    }

    public ArrayList<Photo> getPhotos() {
        photos = new ArrayList<>();
        getRefPhotos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    photos.add(snap.getValue(Photo.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                photos = null;
            }
        });
        return photos;
    }

    public ArrayList<Tchat> getTchats() {
        tchats = new ArrayList<>();
        getRefTchats().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    tchats.add(snap.getValue(Tchat.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tchats = null;
            }
        });
        return tchats;
    }

    public ArrayList<Ticket> getTickets() {
        tickets = new ArrayList<>();
        getRefTickets().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    tickets.add(snap.getValue(Ticket.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tickets = null;
            }
        });
        return tickets;
    }

    public ArrayList<User> getUsers() {
        users = new ArrayList<>();
        getRefUsers().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    users.add(snap.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                users = null;
            }
        });
        return users;
    }

    public ArrayList<Video> getVideos() {
        videos = new ArrayList<>();
        getRefVideos().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    videos.add(snap.getValue(Video.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                videos = null;
            }
        });
        return videos;
    }

    public Event getEvent(String eventUid) {
        event = new Event();
        getRefEvent(eventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                event = dataSnapshot.getValue(Event.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                event = null;
            }
        });

        return event;
    }

    public Photo getPhoto(String photoUid) {
        photo = new Photo();
        getRefPhoto(photoUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                photo = dataSnapshot.getValue(Photo.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                photo = null;
            }
        });
        return photo;
    }

    public Tchat getTchat(String tchatUid) {
        tchat = new Tchat();
        getRefTchat(tchatUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tchat = dataSnapshot.getValue(Tchat.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tchat = null;
            }
        });
        return tchat;
    }

    public User getUser(String userUid) {
        user = new User();

        getRefUser(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user.setUid(dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                user = null;
            }
        });

        return user;
    }


    public Ticket getTicket(String ticketUid) {
        ticket = new Ticket();
        getRefTicket(ticketUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticket = dataSnapshot.getValue(Ticket.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ticket = null;
            }
        });
        return ticket;
    }

    public Video getVideo(String videoUid) {
        video = new Video();
        getRefVideo(videoUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                video = dataSnapshot.getValue(Video.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                video = null;
            }
        });
        return video;
    }


    public ArrayList<User> getEventUsers(String eventUid) {
        eventUsers = new ArrayList<>();
        getRefEventUsers(eventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = getUser(snap.getKey());
                    if (user != null) {
                        eventUsers.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventUsers = null;
            }
        });
        return eventUsers;
    }

    public ArrayList<Photo> getEventPhotos(String eventUid) {
        eventPhotos = new ArrayList<>();
        getRefEventPhotos(eventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Photo photo = getPhoto(snap.getKey());
                    if (photo != null) {
                        eventPhotos.add(photo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventPhotos = null;
            }
        });
        return eventPhotos;
    }


    public ArrayList<Ticket> getEventTickets(String eventUid) {
        eventTickets = new ArrayList<>();
        getRefEventTickets(eventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Ticket ticket = getTicket(snap.getKey());
                    if (ticket != null) {
                        eventTickets.add(ticket);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                eventTickets = null;
            }
        });
        return eventTickets;
    }

    public ArrayList<User> getPhotoLikes(String photoUid) {
        photoLikes = new ArrayList<>();
        getRefPhotoLikes(photoUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = getUser(snap.getKey());
                    if (user != null) {
                        photoLikes.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                photoLikes = null;
            }
        });
        return photoLikes;
    }

    public ArrayList<Event> getUserEvents(String eventUid) {
        userEvents = new ArrayList<>();
        getRefUserEvents(eventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Event event = getEvent(snap.getKey());
                    if (event != null) {
                        userEvents.add(event);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userEvents = null;
            }
        });
        return userEvents;
    }


    public ArrayList<User> getUserFollowers(String userUid) {
        userFollowers = new ArrayList<>();
        getRefUserFollowers(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = getUser(snap.getKey());
                    if (user != null) {
                        userFollowers.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userFollowers = null;
            }
        });
        return userFollowers;
    }


    public ArrayList<User> getUserFollowings(String userUid) {
        userFollowings = new ArrayList<>();
        getRefUserFollowers(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = getUser(snap.getKey());
                    if (user != null) {
                        userFollowings.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userFollowings = null;
            }
        });
        return userFollowings;
    }


    public ArrayList<Photo> getUserPhotos(String photoUid) {
        userPhotos = new ArrayList<>();
        getRefUserPhotos(photoUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Photo photo = getPhoto(snap.getKey());
                    if (photo != null) {
                        userPhotos.add(photo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userPhotos = null;
            }
        });
        return userPhotos;
    }

    public ArrayList<User> getUserLikes(String userUid) {
        userLikes = new ArrayList<>();
        getRefUserLikes(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    User user = getUser(snap.getKey());
                    if (user != null) {
                        userLikes.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                userLikes = null;
            }
        });
        return userLikes;
    }


    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public DatabaseReference getRefDatabaseRoot() {
        return refDatabaseRoot;
    }

    public StorageReference getRefStorageRoot() {
        return refStorageRoot;
    }

    public String getCurrentUserContry() {
        return currentUserContry;
    }

    public void setCurrentUserContry(String currentUserContry) {
        this.currentUserContry = currentUserContry;
    }

    public String getCurrentUserCity() {
        return currentUserCity;
    }

    public void setCurrentUserCity(String currentUserCity) {
        this.currentUserCity = currentUserCity;
    }

    public String getCurrentUserUid() {
        return currentUserUid;
    }

    public void setCurrentUserUid(String currentUserUid) {
        this.currentUserUid = currentUserUid;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }
}
