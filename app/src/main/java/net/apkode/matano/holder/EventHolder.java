package net.apkode.matano.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.activity.EventActivity;
import net.apkode.matano.model.Event;

import java.io.Serializable;


public class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtRubrique, txtTitre, txtTarif, txtLieu, txtJour;
    private ImageView imvImage;

    public EventHolder(View itemView) {
        super(itemView);

        txtRubrique = (TextView) itemView.findViewById(R.id.txtRubrique);
        txtTitre = (TextView) itemView.findViewById(R.id.txtTitre);
        txtTarif = (TextView) itemView.findViewById(R.id.txtTarif);
        txtLieu = (TextView) itemView.findViewById(R.id.txtLieu);
        txtJour = (TextView) itemView.findViewById(R.id.txtJour);
        imvImage = (ImageView) itemView.findViewById(R.id.imvImage);

        itemView.setOnClickListener(this);
    }

    public void bind(Event event) {
        txtRubrique.setText("#" + event.getRubrique());
        txtTitre.setText(event.getTitre());
        txtTarif.setText(event.getTarif());
        txtLieu.setText(event.getLieu());
        txtJour.setText(event.getJour());

        Integer placeholder = R.mipmap.placeholder_culture;
        switch (event.getCategorie()) {
            case "Culture":
                placeholder = R.mipmap.placeholder_culture;
                break;
            case "Education":
                placeholder = R.mipmap.placeholder_education;
                break;
            case "Sport":
                placeholder = R.mipmap.placeholder_sport;
                break;
        }

        Glide.with(imvImage.getContext())
                .load(event.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imvImage.getResources().getDrawable(placeholder))
                .into(imvImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext().getApplicationContext(), EventActivity.class);
        intent.putExtra("Event", (Serializable) v.getTag());
        v.getContext().startActivity(intent);
    }
}