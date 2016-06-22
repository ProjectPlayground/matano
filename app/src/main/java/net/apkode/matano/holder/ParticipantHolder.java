package net.apkode.matano.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.activity.ParticipantActivity;
import net.apkode.matano.model.Participant;

import java.io.Serializable;

public class ParticipantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtNomParticiapant;
    private TextView txtPrenomParticiapant;
    private ImageView imvParticipant;

    public ParticipantHolder(View itemView) {
        super(itemView);
        txtNomParticiapant = (TextView) itemView.findViewById(R.id.txtNomParticiapant);
        txtPrenomParticiapant = (TextView) itemView.findViewById(R.id.txtPrenomParticiapant);
        imvParticipant = (ImageView) itemView.findViewById(R.id.imvParticipant);

        itemView.setOnClickListener(this);
    }

    public void bind(Participant participant) {
        txtNomParticiapant.setText(participant.getNom());
        txtPrenomParticiapant.setText(participant.getPrenom());

        Glide.with(imvParticipant.getContext())
                .load(participant.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvParticipant);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ParticipantActivity.class);
        intent.putExtra("Participant", (Serializable) v.getTag());
        v.getContext().startActivity(intent);
    }
}
