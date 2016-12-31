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

public class ContryActivity extends ListActivity {
    private ArrayList<String> contries;
    private LocalStorage localStorage;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contry);

        localStorage = new LocalStorage(this);
        app = (App) getApplicationContext();

        contries = new ArrayList<>();
        contries.addAll(Arrays.asList(getResources().getStringArray(R.array.contry)));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.card_contry, R.id.listText, contries);

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        localStorage.storeContry(contries.get(position));
        app.setCurrentUserContry(contries.get(position));
        goLoginActivity();
    }


    private void goLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
