package matano.apkode.net.matano.config;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import matano.apkode.net.matano.model.User;

/**
 * Created by smalllamartin on 12/29/16.
 */

public class Firebasesss {

    private FirebaseDatabase database;
    private DatabaseReference refDatabaseRoot;
    private DatabaseReference refUsers;
    private DatabaseReference refUser;
    private Interfaces mCallback;


    public Firebasesss(Context context) {
        this.database = FirebaseDatabase.getInstance();
        this.refDatabaseRoot = database.getReference();
        mCallback = (Interfaces) context;
    }


    public void getUser(String userUid) {
        Log.e(Utils.TAG, "userUid " + userUid);
        Log.e(Utils.TAG, "userUid " + getRefUser(userUid).getRef());
        getRefUser(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setUid(dataSnapshot.getKey());
                mCallback.iGetUser(user);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public DatabaseReference getRefUsers() {
        return refUsers;
    }

    public DatabaseReference getRefUser(String userUid) {
        return refUsers.child(userUid);
    }


    public interface Interfaces {
        void iGetUser(User user);
    }

}
