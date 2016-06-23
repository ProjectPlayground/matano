package net.apkode.matano.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.apkode.matano.R;
import net.apkode.matano.activity.EvenementActivity;
import net.apkode.matano.model.Evenement;

import java.io.Serializable;
import java.text.ParseException;


public class EvenementHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtRubrique, txtTitre, txtTarif, txtLieu, txtJour;
    private ImageView imvImage;

    public EvenementHolder(View itemView) {
        super(itemView);

        txtRubrique = (TextView) itemView.findViewById(R.id.txtRubrique);
        txtTitre = (TextView) itemView.findViewById(R.id.txtTitre);
        txtTarif = (TextView) itemView.findViewById(R.id.txtTarif);
        txtLieu = (TextView) itemView.findViewById(R.id.txtLieu);
        txtJour = (TextView) itemView.findViewById(R.id.txtJour);
        imvImage = (ImageView) itemView.findViewById(R.id.imvImage);

        itemView.setOnClickListener(this);
    }

    public void bind(Evenement evenement) throws ParseException {
        txtRubrique.setText("#" + evenement.getRubrique());
        txtTitre.setText(evenement.getTitre());
        txtTarif.setText(evenement.getTarif());
        txtLieu.setText(evenement.getLieu());
        txtJour.setText(evenement.getJour());

        Picasso.with(imvImage.getContext())
                .load(evenement.getImage())
                .into(imvImage);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext().getApplicationContext(), EvenementActivity.class);
        intent.putExtra("Evenement", (Serializable) v.getTag());
        v.getContext().startActivity(intent);
    }
}