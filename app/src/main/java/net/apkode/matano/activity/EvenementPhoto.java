package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;

public class EvenementPhoto extends AppCompatActivity {
    private ImageView eventPhotoFull;
    private String PHOTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenement_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PHOTO = getIntent().getStringExtra("photo");
        eventPhotoFull = (ImageView) findViewById(R.id.eventPhotoFull);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Glide.with(this)
                .load(PHOTO)
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(eventPhotoFull);

        // new PhotoViewAttacher(eventPhotoFull);
    }
}
