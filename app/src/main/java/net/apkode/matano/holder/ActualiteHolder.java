package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.model.Actualite;

import java.text.ParseException;

public class ActualiteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtJourActualite;
    private TextView txtActualite;

    public ActualiteHolder(View itemView) {
        super(itemView);

        txtJourActualite = (TextView) itemView.findViewById(R.id.txtJourActualite);
        txtActualite = (TextView) itemView.findViewById(R.id.txtActualite);

        itemView.setOnClickListener(this);

    }

    public void bind(Actualite actualite) throws ParseException {
        txtJourActualite.setText(actualite.getJour());
        txtActualite.setText(actualite.getActualite());
    }

    @Override
    public void onClick(View v) {

    }
}
