package net.apkode.matano.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EventSectionsPagerAdapter;
import net.apkode.matano.helper.UtilisateurLocalStore;

public class EventsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EventSectionsPagerAdapter eventSectionsPagerAdapter = new EventSectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(eventSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(eventSectionsPagerAdapter.getTabView(i));
        }


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
        if(!utilisateurLocalStore.isLoggedIn()){
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
            startActivity(new Intent(this, ProfilActivty.class));
        } else if (id == R.id.btn_navigation_menu_evenements) {

        } else if (id == R.id.btn_navigation_menu_mes_evenements) {

        } else if (id == R.id.btn_navigation_menu_mes_messages) {

        } else if (id == R.id.btn_navigation_menu_about) {

        } else if (id == R.id.btn_navigation_menu_parametre) {

        } else if (id == R.id.btn_navigation_menu_dexonnexion) {
            utilisateurLocalStore.setUtilisateurLogin(false);
            finish();
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
