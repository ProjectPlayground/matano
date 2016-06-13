package net.apkode.matano.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import net.apkode.matano.R;
import net.apkode.matano.model.Event;

public class EventActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        event = (Event) getIntent().getSerializableExtra("Event");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView txtTitre = (TextView) findViewById(R.id.txtTitre);
        TextView txtLieu = (TextView) findViewById(R.id.txtLieu);
        TextView txtHoraire = (TextView) findViewById(R.id.txtHoraire);
        TextView txtTarif = (TextView) findViewById(R.id.txtTarif);
        TextView txtPresentation = (TextView) findViewById(R.id.txtPresentation);
        TextView txtJour = (TextView) findViewById(R.id.txtJour);

        ImageView imageView = (ImageView) findViewById(R.id.main_backdrop);

        assert toolbar != null;
        toolbar.setTitle("#" + event.getRubrique());

        assert txtTitre != null;
        txtTitre.setText(event.getTitre());
        assert txtLieu != null;
        txtLieu.setText(event.getLieu());
        assert txtHoraire != null;
        txtHoraire.setText(event.getHoraire());
        assert txtTarif != null;
        txtTarif.setText(event.getTarif());
        assert txtPresentation != null;
        txtPresentation.setText(event.getPresentation());
        assert txtJour != null;
        txtJour.setText(event.getJour());

        assert imageView != null;
        Glide.with(this)
                .load(event.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
    }

    public void startEventPhoto(View view) {
        Intent intent = new Intent(getApplicationContext(), EventPhoto.class);
        intent.putExtra("photo", event.getImagefull());
        startActivity(intent);

    }

    public void startEventVideo(View view) {
        Intent intent = new Intent(getApplicationContext(), EventVideo.class);
        intent.putExtra("video", event.getVideo());
        startActivity(intent);

    }

    public void startEventShareFacebook(View view) {

        if (event != null) {
            String presentation = event.getPresentation();
            String titre = event.getTitre();
            String categorie = event.getCategorie();
            String rubrique = event.getRubrique();
            String image = event.getImage();
            String imagefull = event.getImagefull();
            String horaire = event.getHoraire();
            String lieu = event.getLieu();
            String tarif = event.getTarif();

            String description = "Categorie : " + categorie + "\n" + "Rubrique : " + rubrique + "\n" + "Lieu : " + lieu + "\n" + "Horaire : " + horaire + "\n" + "Tarif : +" + tarif + "\n" + "Detail : " + presentation;

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://www.facebook.com/Niameyzze227"))
                        .setImageUrl(Uri.parse(imagefull))
                        .setContentDescription(description)
                        .setContentTitle(titre)
                        .build();
                shareDialog.show(content);
            }
        }
    }

}
