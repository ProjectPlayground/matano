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

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.MainEventFragment;
import matano.apkode.net.matano.fragment.MainNewFragment;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private MainEventFragment mainEventFragment;
    private MainNewFragment mainNewFragment;
    private FrameLayout frameLayoutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        break;

                    case R.id.ic_bottom_new:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainNewFragment).commit();
                        break;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            frameLayoutMenu.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
