package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.adapter.InteretAdapter;
import net.apkode.matano.model.Interet;
import net.apkode.matano.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Participant participant = (Participant) getIntent().getSerializableExtra("Participant");



        List<Interet> interets = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setScrollbarFadingEnabled (true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        interets.add(new Interet("Film"));
        interets.add(new Interet("Musique"));
        interets.add(new Interet("Danse"));
        interets.add(new Interet("Formation"));
        interets.add(new Interet("Seminaire"));
        interets.add(new Interet("Football"));
        interets.add(new Interet("Natation"));
        interets.add(new Interet("Theatre"));

        recyclerView.setAdapter(new InteretAdapter(interets));



    }

}
