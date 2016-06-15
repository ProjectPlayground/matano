package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.Participant;

/**
 * Created by brabo on 6/14/16.
 */
public class ParticipantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtNomParticiapant;
    private ImageView imvParticipant;

    public ParticipantHolder(View itemView) {
        super(itemView);
        txtNomParticiapant = (TextView)itemView.findViewById(R.id.txtNomParticiapant);
        imvParticipant = (ImageView)itemView.findViewById(R.id.imvParticipant);

        itemView.setOnClickListener(this);
    }

    public void bind(Participant participant){
        txtNomParticiapant.setText(participant.getNom());

        Glide.with(imvParticipant.getContext())
                .load(participant.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imvParticipant.getResources().getDrawable(R.mipmap.placeholder_homme))
                .into(imvParticipant);
    }

    @Override
    public void onClick(View v) {

    }
}
