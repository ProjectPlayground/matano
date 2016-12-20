package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Utils;
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
    private FirebaseStorage storage;
    private StorageReference refStoragePhotos;
    private StorageReference mRootStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReference();
        refUser = mRootRef.child("user");

        storage = FirebaseStorage.getInstance();
        mRootStorageRef = storage.getReference();

        refStoragePhotos = mRootStorageRef.child("profil");

        frameLayoutContry = (FrameLayout) findViewById(R.id.frameLayoutContry);
        frameLayoutCity = (FrameLayout) findViewById(R.id.frameLayoutCity);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserExiste();
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
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {

                setUserExiste();
                // goMain();
                // finish();
                return;
            }

            // Sign in canceled
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.cancel_login, Toast.LENGTH_SHORT).show();
                return;
            }

            // No network
            if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
                return;
            }

            // User is not signed in. Maybe just wait for the user to press
            // "sign in" again, or show a message.
        }
    }

    private void setUserExiste() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference refUserExist = refUser.child(user.getUid());

        refUserExist.addListenerForSingleValueEvent(new ValueEventListener() {
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

                    User userNew = new User(username, firstName, null, null, null, email, null, null, null, null, photoProfil, null, null, null, null, null, null, null);

                    uploadAndSavePhoto(userNew, user);

                } else {
                    setUserContryExist();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void uploadAndSavePhoto(final User userNew, final FirebaseUser user) {


        Map hashMap = new HashMap();

        hashMap.put("user/" + user.getUid(), userNew);
        hashMap.put("profil/" + user.getUid(), userNew.getPhotoProfl());

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null != databaseError) {
                    Log.e(Utils.TAG, "null != databaseError");
                } else {
                    Log.e(Utils.TAG, "null == databaseError");
                    setUserContryExist();
                }
            }
        });

    }


    private void savePhoto(FirebaseUser currentUser, Uri downloadUri, String uuid, User userNew) {
        Map hashMap = new HashMap();

        hashMap.put("user/" + uuid, userNew);
        // hashMap.put("profil/"+currentUser.getUid(), downloadUri.toString());

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null != databaseError) {
                    Log.e(Utils.TAG, "null != databaseError");
                } else {
                    Log.e(Utils.TAG, "null == databaseError");
                    setUserContryExist();
                }
            }
        });




    }

    private void setUserContryExist() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference refUserExist = refUser.child(user.getUid());

        refUserExist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).child("contry").exists()) {
                    setUserCityExist();
                } else {
                    frameLayoutCity.setVisibility(View.GONE);
                    frameLayoutContry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setUserCityExist() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference refUserExist = refUser.child(user.getUid());

        refUserExist.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user.getUid()).child("city").exists()) {
                    goMain();
                } else {
                    frameLayoutContry.setVisibility(View.GONE);
                    frameLayoutCity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
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
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference refUserContry = refUser.child(user.getUid()).child("contry");
        refUserContry.setValue(contry, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null == databaseError) {
                    setUserCityExist();
                } else {

                }
            }
        });
    }

    private void setUserAddCity(String city) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        DatabaseReference refUserContry = refUser.child(user.getUid()).child("city");
        refUserContry.setValue(city, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (null == databaseError) {
                    goMain();
                } else {

                }
            }
        });
    }

    public void logOut(View view) {
        AuthUI.getInstance().signOut(this);
    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
