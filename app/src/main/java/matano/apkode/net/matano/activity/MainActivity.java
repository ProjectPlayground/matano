package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.MainEventFragment;
import matano.apkode.net.matano.fragment.MainNewFragment;


public class MainActivity extends AppCompatActivity {
    private static String ARG_USER_UID = "userUid";
    private FrameLayout frameLayout;
    private MainEventFragment mainEventFragment;
    private MainNewFragment mainNewFragment;
    private FrameLayout frameLayoutMenu;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    goSignIn();
                    finish();
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

        frameLayoutMenu = (FrameLayout) findViewById(R.id.frameLayoutMenu);


        if (null != frameLayout) {

            if (savedInstanceState != null) {
                return;
            }

            mainEventFragment = new MainEventFragment();
            mainNewFragment = new MainNewFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainEventFragment).commit();

        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_bottom_event:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainEventFragment).commit();
                        return true;

                    case R.id.ic_bottom_new:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainNewFragment).commit();
                        return true;

                    case R.id.ic_bottom_profil:
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        intent.putExtra(ARG_USER_UID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(intent);
                        break;

                }
                return false;
            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            frameLayoutMenu.bringToFront();
            frameLayoutMenu.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void goSignIn() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void logOut(View view) {
        AuthUI.getInstance().signOut(this);
    }
}
