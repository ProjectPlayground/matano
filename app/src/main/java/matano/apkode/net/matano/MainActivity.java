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

import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.MainEventFragment;
import matano.apkode.net.matano.fragment.MainTimelineFragment;


public class MainActivity extends AppCompatActivity {
    private App app;
    private String incomeEventUid;
    private Db db;

    private MainEventFragment mainEventFragment;
    private MainTimelineFragment mainTimelineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App) getApplicationContext();
        db = new Db(this);

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
                        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                        intent.putExtra(Utils.ARG_USER_UID, app.getCurrentUserUid());
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

    private void finishActivity() {
        finish();
    }

}
