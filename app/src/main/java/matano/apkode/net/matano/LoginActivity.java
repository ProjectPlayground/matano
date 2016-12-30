package matano.apkode.net.matano;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.model.User;

import static com.firebase.ui.auth.ui.ResultCodes.RESULT_NO_NETWORK;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private App app;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String currentUserUid;
    private Db db;
    private LocalStorage localStorage;
    private TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (App) getApplicationContext();
        db = new Db(this);
        localStorage = new LocalStorage(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                                    .setLogo(R.mipmap.ic_action_action_account_balance_wallet)
                                    .setTheme(R.style.AppTheme_NoActionBar)
                                    .setProviders(Arrays.asList(
                                            // new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())
                                    )
                                    .build(),
                            RC_SIGN_IN);
                } else {
                    currentUserUid = user.getUid();
                    setIfUserExist();
                }
            }
        };

        logo = (TextView) findViewById(R.id.logo);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                setIfUserExist();
                return;
            }

            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.cancel_login, Toast.LENGTH_SHORT).show();
                return;
            }

            if (resultCode == RESULT_NO_NETWORK) {
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }

    private void setIfUserExist() {
        Query query = app.getRefUser(currentUserUid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {

                    String username = user.getDisplayName();
                    String firstName = user.getDisplayName();
                    String email = user.getEmail();
                    String photoProfil = null;

                    Uri photoUrl = user.getPhotoUrl();

                    if (null != photoUrl) {
                        photoProfil = photoUrl.toString();
                    }

                    User userNew = new User();
                    userNew.setUsername(username);
                    userNew.setFirstName(firstName);
                    userNew.setEmail(email);
                    userNew.setPhotoProfl(photoProfil);

                    saveUser(userNew);

                } else {
                    setIfUserContryExist();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError.getCode() == DatabaseError.DISCONNECTED && databaseError.getCode() == DatabaseError.NETWORK_ERROR) {
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveUser(User user) {
        DatabaseReference databaseReference = app.getRefUser(currentUserUid);
        databaseReference.setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
                } else {
                    setIfUserContryExist();
                }
            }
        });
    }

    private void setIfUserContryExist() {

        if (localStorage.isContryStored()) {
            setIfUserCityExist();
        } else {
            Intent intent = new Intent(this, ContryActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void setIfUserCityExist() {

        if (localStorage.isCityStored()) {
            goMainAcivity();
        } else {
            Intent intent = new Intent(this, CityActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void goMainAcivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
