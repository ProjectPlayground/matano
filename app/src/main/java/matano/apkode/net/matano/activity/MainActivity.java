package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.MainEventFragment;
import matano.apkode.net.matano.fragment.MainTimelineFragment;


public class MainActivity extends AppCompatActivity {
    private static String ARG_USER_UID = "userUid";
    private FrameLayout frameLayout;
    private MainEventFragment mainEventFragment;
    private MainTimelineFragment mainTimelineFragment;
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


        if (savedInstanceState != null) {
            return;
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new MainEventFragment().newInstance()).commit();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_bottom_event:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainEventFragment().newInstance()).commit();
                        return true;

                    case R.id.ic_bottom_new:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainTimelineFragment().newInstance()).commit();
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
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter = getFilter().filter(newText);
                return false;
            }
        });

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

}
