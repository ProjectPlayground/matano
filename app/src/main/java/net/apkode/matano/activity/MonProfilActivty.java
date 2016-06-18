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
import android.view.View;

import net.apkode.matano.R;
import net.apkode.matano.adapter.InteretAdapter;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Interet;

import java.util.ArrayList;
import java.util.List;

public class MonProfilActivty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Interet> interets = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setScrollbarFadingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        interets.add(new Interet("Film"));
        interets.add(new Interet("Musique"));
        interets.add(new Interet("Danse"));
        interets.add(new Interet("Formation"));
        interets.add(new Interet("Seminaire"));
        interets.add(new Interet("Football"));
        interets.add(new Interet("Natation"));
        interets.add(new Interet("Theatre"));

        recyclerView.setAdapter(new InteretAdapter(interets));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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


        if (id == R.id.btn_navigation_menu_profil) {
            startActivity(new Intent(this, MonProfilActivty.class));
        } else if (id == R.id.btn_navigation_menu_evenements) {
            startActivity(new Intent(this, EventsActivity.class));
        } else if (id == R.id.btn_navigation_menu_mes_evenements) {
            startActivity(new Intent(this, MesEventsActivity.class));
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

    public void startUpdateContact(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateContact.class));
    }

    public void startUpdateProfil(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateProfil.class));
    }

    public void startUpdateBasic(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateBasic.class));
    }

    public void startUpdatePresentation(View view) {
        startActivity(new Intent(getApplicationContext(), UpdatePresentation.class));
    }

    public void startUpdateInteret(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateInteret.class));
    }
}
