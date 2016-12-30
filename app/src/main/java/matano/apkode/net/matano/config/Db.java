package matano.apkode.net.matano.config;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Tchat;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Db {
    private Context context;
    private App app;
    private FirebaseDatabase database;
    private DatabaseReference refDatabaseRoot;

    public Db(Context context) {
        this.app = (App) getApplicationContext();
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
        this.refDatabaseRoot = database.getReference();
    }

    public void setParticipantion(String tag, String incomeEventUid, String currentUserUid) {

        switch (tag) {
            case "9":
                tag = null;
                break;
            case "0":
                tag = "0";
                break;
        }

        Map hashMap = new HashMap();
        hashMap.put("event/" + incomeEventUid + "/users/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/events/" + incomeEventUid, tag);

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

    public void setFollowing(String userUid, String tag, String currentUserUid) {
        Map hashMap = new HashMap();
        hashMap.put("user/" + userUid + "/followers/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/followings/" + userUid, tag);

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

    public void setPhotoTimeline(Uri downloadUri, String uuid, String incomeEventUid, String currentUserUid) {

        Photo photo = new Photo(incomeEventUid, currentUserUid, downloadUri.toString(), new Date(), "0", null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + incomeEventUid + "/photos/" + uuid, "0");
        hashMap.put("photo/" + uuid, photo);
        hashMap.put("user/" + currentUserUid + "/photos/" + uuid, "0");

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

    public void setPhotoLike(String photoUid, String tag, String currentUserUid) {
        Map hashMap = new HashMap();
        hashMap.put("photo/" + photoUid + "/likes/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/likes/" + photoUid, tag);

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

    public void setTchatPhoto(Uri downloadUri, String uuid, String incomeEventUid, String currentUserUid) {
        Photo photo = new Photo(incomeEventUid, currentUserUid, downloadUri.toString(), new Date(), "1", null);
        Tchat tchat = new Tchat(incomeEventUid, currentUserUid, new Date(), null, uuid, null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + incomeEventUid + "/tchats/" + uuid, "1");
        hashMap.put("user/" + currentUserUid + "/tchats/" + uuid, "1");
        hashMap.put("photo/" + uuid, photo);
        hashMap.put("tchat/" + uuid, tchat);

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

    public void setTchatMessage(String message, String uuid, String incomeEventUid, String currentUserUid) {
        Tchat tchat = new Tchat(incomeEventUid, currentUserUid, new Date(), message, null, null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + incomeEventUid + "/tchats/" + uuid, "1");
        hashMap.put("user/" + currentUserUid + "/tchats/" + uuid, "1");
        hashMap.put("tchat/" + uuid, tchat);

        app.getRefDatabaseRoot().updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }

}
