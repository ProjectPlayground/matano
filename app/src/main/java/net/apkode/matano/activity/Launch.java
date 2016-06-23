package net.apkode.matano.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import net.apkode.matano.R;
import net.apkode.matano.helper.Sleep;
import net.apkode.matano.interfaces.ISleep;

public class Launch extends AppCompatActivity implements ISleep {
    private TextView nom;
    private Typeface typeface;
    private TextView interrogation;

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

        typeface = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");

        nom = (TextView) findViewById(R.id.nom);
        interrogation = (TextView) findViewById(R.id.interrogation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        nom.setTypeface(typeface);
        interrogation.setTypeface(typeface);

        YoYo.with(Techniques.StandUp)
                .duration(2000)
                .playOn(findViewById(R.id.nom));

        new Sleep(this, this).execute();
    }

    @Override
    public void getResponse() {
        startActivity(new Intent(getApplicationContext(), EvenementsActivity.class));
        finish();
    }
}