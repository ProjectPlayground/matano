package matano.apkode.net.matano.config;


import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentification {
    private final String TAG = "firebase";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean result;

    public boolean isLoggedIn() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    result = true;
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    result = false;
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        return result;
    }


    public void logOut() {
        FirebaseAuth.getInstance().signOut();
    }


}
