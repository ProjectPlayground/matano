package matano.apkode.net.matano;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.LocalStorage;

public class CityActivity extends ListActivity {
    private ArrayList<String> cities;
    private LocalStorage localStorage;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        localStorage = new LocalStorage(this);
        app = (App) getApplicationContext();

        cities = new ArrayList<>();

        if (!localStorage.isContryStored()) {
            goLoginActivity();
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
        app.setCurrentUserCity(cities.get(position));
        goLoginActivity();
    }

    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
