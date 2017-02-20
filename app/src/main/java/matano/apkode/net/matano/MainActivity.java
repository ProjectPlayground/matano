package matano.apkode.net.matano;

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
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.fragment.MainEventFragment;
import matano.apkode.net.matano.fragment.MainTimelineFragment;


public class MainActivity extends AppCompatActivity {
    private Db db;

    private MainEventFragment mainEventFragment;
    private MainTimelineFragment mainTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Db(this);

        //fbDatabase.getRefEvent("-KctmsdelXhPGyJBhYFb").child(Utils.FIREBASE_DATABASE_EVENT_INVITES).push().setValue(new Programme("Jacques ATTALI", "Président - Positive Planet", "http://www.sahelinnov.org/wp-content/uploads/2016/01/Attali.jpg", "Jacques Attali est né le 1er novembre 1943. Polytechnicien, énarque et ancien conseiller spécial du président de la République François Mitterrand pendant dix ans, il est le fondateur de quatre institutions internationales : Action contre la faim, Eureka, BERD et Positive Planet. Cette dernière est la plus importante institution mondiale de soutien à la microfinance et a apporté son appui à plus de 10 millions de micro-entrepreneurs."));


        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainEventFragment = new MainEventFragment();
        mainTimelineFragment = new MainTimelineFragment();

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

        if (frameLayout != null) {
            if (savedInstanceState != null) {
                return;
            }

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainEventFragment).commit();

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainEventFragment).commit();
                        return true;

                    case R.id.ic_bottom_new:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainTimelineFragment).commit();
                        return true;

                    case R.id.ic_bottom_profil:
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_country:
                startActivity(new Intent(this, CountryActivity.class));
                return true;

            case R.id.action_city:
                startActivity(new Intent(this, CityActivity.class));
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

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

}
