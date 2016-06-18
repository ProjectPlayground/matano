package net.apkode.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.apkode.matano.R;
import net.apkode.matano.adapter.MessageListeAdapter;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.MessageListe;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MessageListe> messageListeList = new ArrayList<>();
        messageListeList.add(new MessageListe("Bachir", "Rabo", "11/02.2016", "08H32", "Hello comment vas tu", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
        messageListeList.add(new MessageListe("Bachir", "Rabo", "11/02.2016", "08H32", "Hello comment vas tu", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
        messageListeList.add(new MessageListe("Bachir", "Rabo", "11/02.2016", "08H32", "Hello comment vas tu", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));

        recyclerView.setAdapter(new MessageListeAdapter(messageListeList));
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_navigation_menu_evenements) {
            startActivity(new Intent(this, EventsActivity.class));
        } else if (id == R.id.btn_navigation_menu_mes_evenements) {
            startActivity(new Intent(this, MesEventsActivity.class));
        } else if (id == R.id.btn_navigation_menu_profil) {
            startActivity(new Intent(this, MonProfilActivty.class));
        } else if (id == R.id.btn_navigation_menu_feedback) {
            startActivity(new Intent(this, FeedbackActivity.class));
        } else if (id == R.id.btn_navigation_menu_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (id == R.id.btn_navigation_menu_parametre) {
            startActivity(new Intent(this, ParametresActivty.class));
        } else if (id == R.id.btn_navigation_menu_dexonnexion) {
            utilisateurLocalStore.setUtilisateurLogin(false);
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
