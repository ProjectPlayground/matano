package matano.apkode.net.matano;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

import matano.apkode.net.matano.config.LocalStorage;

public class CountryActivity extends ListActivity {
    private ArrayList<String> countries;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_country);

        localStorage = new LocalStorage(this);

        localStorage.clearCountry();
        localStorage.clearCity();

        countries = new ArrayList<>();
        countries.addAll(Arrays.asList(getResources().getStringArray(R.array.country)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.card_country, R.id.listText, countries);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        localStorage.storeCountry(countries.get(position));
        goMain();
    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
