package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.User;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;
    private FrameLayout frameLayoutContry;
    private FrameLayout frameLayoutCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReference();
        refUser = mRootRef.child("user");

        frameLayoutContry = (FrameLayout) findViewById(R.id.frameLayoutContry);
        frameLayoutCity = (FrameLayout) findViewById(R.id.frameLayoutCity);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    refUser = refUser.child(user.getUid());
                    setIfUserExist();
                } else {
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
                }
            }
        };
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

            if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                return;
            }

        }
    }

    private void setIfUserExist() {

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {

                    String username = currentUser.getDisplayName();
                    String firstName = currentUser.getDisplayName();
                    String email = currentUser.getEmail();
                    String photoProfil = null;

                    Uri photoUrl = currentUser.getPhotoUrl();

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

            }
        });
    }

    private void saveUser(User userNew) {
        refUser.setValue(userNew, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null == databaseError) {
                    setIfUserContryExist();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setIfUserContryExist() {
        refUser.child("contry").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    frameLayoutCity.setVisibility(View.GONE);
                    frameLayoutContry.setVisibility(View.VISIBLE);
                } else {
                    setIfUserCityExist();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setIfUserCityExist() {
        refUser.child("city").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    frameLayoutContry.setVisibility(View.GONE);
                    frameLayoutCity.setVisibility(View.VISIBLE);
                } else {
                    goMain();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_niger:
                if (checked)
                    setUserAddContry("Niger");
                break;
            case R.id.radio_senegal:
                if (checked)
                    setUserAddContry("Senegal");
                break;
        }
    }

    public void onRadioButtonCityClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_niamey:
                if (checked)
                    setUserAddCity("Niamey");
                break;
            case R.id.radio_dosso:
                if (checked)
                    setUserAddCity("Dosso");
                break;
        }
    }

    private void setUserAddContry(String contry) {
        refUser.child("contry").setValue(contry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null == databaseError) {
                    setIfUserCityExist();
                } else {

                }
            }
        });
    }

    private void setUserAddCity(String city) {
        refUser.child("city").setValue(city, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null == databaseError) {
                    goMain();
                } else {

                }
            }
        });
    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
