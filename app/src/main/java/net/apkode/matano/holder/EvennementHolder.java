package net.apkode.matano.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.apkode.matano.R;
import net.apkode.matano.activity.EvennementActivity;
import net.apkode.matano.model.Evennement;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class EvennementHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtRubrique, txtTitre, txtTarif, txtLieu, txtJour;
    private ImageView imvImage;

    public EvennementHolder(View itemView) {
        super(itemView);

        txtRubrique = (TextView) itemView.findViewById(R.id.txtRubrique);
        txtTitre = (TextView) itemView.findViewById(R.id.txtTitre);
        txtTarif = (TextView) itemView.findViewById(R.id.txtTarif);
        txtLieu = (TextView) itemView.findViewById(R.id.txtLieu);
        txtJour = (TextView) itemView.findViewById(R.id.txtJour);
        imvImage = (ImageView) itemView.findViewById(R.id.imvImage);

        itemView.setOnClickListener(this);
    }

    public void bind(Evennement evennement) throws ParseException {
        txtRubrique.setText("#" + evennement.getRubrique());
        txtTitre.setText(evennement.getTitre());
        txtTarif.setText(evennement.getTarif());
        txtLieu.setText(evennement.getLieu());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateInString = evennement.getJour();

        txtJour.setText((CharSequence) formatter.parse(dateInString));

        Integer placeholder = R.mipmap.placeholder_culture;
        switch (evennement.getCategorie()) {
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

      /*  Glide.with(imvImage.getContext())
                .load(evennement.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imvImage.getResources().getDrawable(placeholder))
                .into(imvImage);*/


        Picasso.with(imvImage.getContext())
                .load(evennement.getImage())
                //.fit()
                // .centerCrop()
                .into(imvImage);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext().getApplicationContext(), EvennementActivity.class);
        intent.putExtra("Evennement", (Serializable) v.getTag());
        v.getContext().startActivity(intent);
    }
}