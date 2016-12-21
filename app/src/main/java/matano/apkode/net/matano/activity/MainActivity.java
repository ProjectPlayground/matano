package matano.apkode.net.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.MainFramelayoutCategorieAdapter;
import matano.apkode.net.matano.adapter.MainFramelayoutPaysAdapter;
import matano.apkode.net.matano.adapter.MainFramelayoutVilleAdapter;
import matano.apkode.net.matano.fragment.MainFragment;
import matano.apkode.net.matano.fragment.MainNewFragment;


public class MainActivity extends AppCompatActivity {
    private static String ARG_USER_UID = "userUid";
    private FrameLayout frameLayout;
    private MainFragment mainFragment;
    private MainNewFragment mainNewFragment;
    private FrameLayout frameLayoutMenu;
    private FrameLayout frameLayoutBtnCategorie;
    private FrameLayout frameLayoutBtnPays;
    private FrameLayout frameLayoutBtnVille;
    private RecyclerView recyclerViewBtnCategorie;
    private RecyclerView recyclerViewBtnPays;
    private RecyclerView recyclerViewBtnVille;
    private List<String> categorieList = new ArrayList<>();
    private List<String> paysList = new ArrayList<>();
    private List<String> villeList = new ArrayList<>();
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
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);

        frameLayoutMenu = (FrameLayout) findViewById(R.id.frameLayoutMenu);
        frameLayoutBtnCategorie = (FrameLayout) findViewById(R.id.frameLayoutBtnCategorie);
        frameLayoutBtnPays = (FrameLayout) findViewById(R.id.frameLayoutBtnPays);
        frameLayoutBtnVille = (FrameLayout) findViewById(R.id.frameLayoutBtnVille);


        if (null != frameLayout) {

            if (savedInstanceState != null) {
                return;
            }

            mainFragment = new MainFragment();
            mainNewFragment = new MainNewFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment).commit();

        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_bottom_event:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
                        break;

                    case R.id.ic_bottom_new:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainNewFragment).commit();
                        break;

                    case R.id.ic_bottom_profil:
                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                        intent.putExtra(ARG_USER_UID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                        startActivity(intent);
                        break;

                }
                return false;
            }

        });


        // Categorie
        recyclerViewBtnCategorie = (RecyclerView) findViewById(R.id.recyclerViewBtnCategorie);
        recyclerViewBtnCategorie.setHasFixedSize(true);

        recyclerViewBtnCategorie.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MainFramelayoutCategorieAdapter mainFramelayoutCategorieAdapter = new MainFramelayoutCategorieAdapter(categorieList);

        categorieList.add("Culture");
        categorieList.add("Education");
        categorieList.add("Sport");

        recyclerViewBtnCategorie.setAdapter(mainFramelayoutCategorieAdapter);

        // Pays
        recyclerViewBtnPays = (RecyclerView) findViewById(R.id.recyclerViewBtnPays);
        recyclerViewBtnPays.setHasFixedSize(true);

        recyclerViewBtnPays.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MainFramelayoutPaysAdapter mainFramelayoutPaysAdapter = new MainFramelayoutPaysAdapter(paysList);

        paysList.add("Niger");
        paysList.add("Senegal");

        recyclerViewBtnPays.setAdapter(mainFramelayoutPaysAdapter);


        // Ville
        recyclerViewBtnVille = (RecyclerView) findViewById(R.id.recyclerViewBtnVille);
        recyclerViewBtnVille.setHasFixedSize(true);

        recyclerViewBtnVille.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        MainFramelayoutVilleAdapter mainFramelayoutVilleAdapter = new MainFramelayoutVilleAdapter(villeList);

        villeList.add("Niamey");
        villeList.add("Dosso");
        villeList.add("Zinder");

        recyclerViewBtnVille.setAdapter(mainFramelayoutVilleAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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


    public void showFragmentBtnCategorie(View view) {
        closeAllFragmentBtn();

        frameLayoutBtnCategorie.bringToFront();
        frameLayoutBtnCategorie.setVisibility(View.VISIBLE);
    }

    public void showFragmentBtnPays(View view) {
        closeAllFragmentBtn();

        frameLayoutBtnPays.bringToFront();
        frameLayoutBtnPays.setVisibility(View.VISIBLE);
    }

    public void showFragmentBtnVille(View view) {
        closeAllFragmentBtn();
        frameLayoutBtnVille.bringToFront();
        frameLayoutBtnVille.setVisibility(View.VISIBLE);
    }

    private void closeAllFragmentBtn() {
        frameLayoutBtnCategorie.setVisibility(View.GONE);
        frameLayoutBtnPays.setVisibility(View.GONE);
        frameLayoutBtnVille.setVisibility(View.GONE);
        frameLayoutMenu.setVisibility(View.GONE);

    }

    public void closeFragmentBtn(View view) {
        closeAllFragmentBtn();
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
