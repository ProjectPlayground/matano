package matano.apkode.net.matano;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.LocalStorage;

public class CityActivity extends ListActivity {
    private ArrayList<String> cities;
    private LocalStorage localStorage;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAuthStateListener();

        db = new Db(this);

        setContentView(R.layout.activity_city);


        localStorage = new LocalStorage(this);

        cities = new ArrayList<>();

        if (!localStorage.isContryStored()) {
            goLogin();
        }

        String contry = localStorage.getContry();

        switch (contry) {
            case "Niger":
                cities.addAll(Arrays.asList(getResources().getStringArray(R.array.city_niger)));
                break;
            case "Sénégal":
                cities.addAll(Arrays.asList(getResources().getStringArray(R.array.city_senegal)));
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.card_city, R.id.listText, cities);

        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        localStorage.storeCity(cities.get(position));
        goLogin();
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

    private void createAuthStateListener() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    goLogin();
                } else {
                    currentUserUid = currentUser.getUid();
                }
            }
        };
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
