package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.Participant;

public class ParticipantActivity extends AppCompatActivity {
    private Participant participant;
    private TextView txtNomParticiapant;
    private TextView txtPrenomParticipant;
    private TextView txtPresentationParticipant;
    private ImageView imgImageParticipant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        participant = (Participant) getIntent().getSerializableExtra("Participant");

        txtNomParticiapant = (TextView) findViewById(R.id.txtNomParticiapant);
        txtPrenomParticipant = (TextView) findViewById(R.id.txtPrenomParticipant);
        txtPresentationParticipant = (TextView) findViewById(R.id.txtPresentationParticipant);
        imgImageParticipant = (ImageView) findViewById(R.id.imgImageParticipant);



    }

    @Override
    protected void onResume() {
        super.onResume();
        txtNomParticiapant.setText(participant.getNom());
        txtPrenomParticipant.setText(participant.getPrenom());
        txtPresentationParticipant.setText(participant.getPresentation());

        Glide.with(this)
                .load(participant.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgImageParticipant);

    }
}
