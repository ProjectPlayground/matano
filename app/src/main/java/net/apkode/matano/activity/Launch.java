package net.apkode.matano.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.apkode.matano.R;
import net.apkode.matano.helper.APIEvent;
import net.apkode.matano.helper.IEvent;
import net.apkode.matano.model.Event;

import java.util.List;

public class Launch extends AppCompatActivity implements IEvent {
    private APIEvent apiEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_launch);

        apiEvent = new APIEvent(this, getApplicationContext());
        apiEvent.getEvents();

    }

    @Override
    public void getResponse(List<Event> events) {
        apiEvent.compareAndCharge(events);
        finish();
        startActivity(new Intent(this, EventsActivity.class));
    }

}
