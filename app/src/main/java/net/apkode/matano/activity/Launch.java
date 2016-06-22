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
import net.apkode.matano.api.APIEvennement;
import net.apkode.matano.interfaces.IEvennement;
import net.apkode.matano.model.Evennement;

import java.util.List;

public class Launch extends AppCompatActivity implements IEvennement {
    private APIEvennement apiEvennement;

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

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");
        TextView nom = (TextView) findViewById(R.id.nom);
        nom.setTypeface(custom_font);

        apiEvennement = new APIEvennement(this, getApplicationContext());
        apiEvennement.getData();
    }



    @Override
    public void getResponses(List<Evennement> evennements) {
       try {
           apiEvennement.compareAndCharge(evennements);
       }catch (Exception e){
           e.getMessage();
       }
        finish();
        startActivity(new Intent(this, EvennementsActivity.class));
    }

    @Override
    public void getResponse(List<Evennement> evennements) {

    }

}