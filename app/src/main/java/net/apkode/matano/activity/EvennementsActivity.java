package net.apkode.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EvennementSectionsPagerAdapter;
import net.apkode.matano.helper.UtilisateurLocalStore;

public class EvennementsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evennemens);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EvennementSectionsPagerAdapter evennementSectionsPagerAdapter = new EvennementSectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(evennementSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(evennementSectionsPagerAdapter.getTabView(i));
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
            startActivity(new Intent(this, EvennementsActivity.class));
        } else if (id == R.id.btn_navigation_menu_mes_evenements) {
            startActivity(new Intent(this, MesEvennementsActivity.class));
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