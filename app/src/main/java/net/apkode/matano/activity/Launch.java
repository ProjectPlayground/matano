package net.apkode.matano.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.helper.APIEvent;
import net.apkode.matano.helper.IEvent;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Event;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Launch extends AppCompatActivity implements IEvent {
    private APIEvent apiEvent;
    private static final int SPLASH_TIME = 2000;
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_launch);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");
        TextView nom = (TextView) findViewById(R.id.nom);
        nom.setTypeface(custom_font);

        try {
            Thread.sleep(SPLASH_TIME);
            apiEvent = new APIEvent(this, getApplicationContext());
            apiEvent.getEvents();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
    public void getResponse(List<Event> events) {
        apiEvent.compareAndCharge(events);
        finish();
        startActivity(new Intent(this, EventsActivity.class));
    }

}
