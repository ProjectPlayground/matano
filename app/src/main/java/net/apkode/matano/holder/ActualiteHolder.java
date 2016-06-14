package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.model.Actualite;

/**
 * Created by brabo on 6/14/16.
 */
public class ActualiteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtJourActualite;
    private TextView txtHoraireAcutalite;
    private TextView txtActualite;

    public ActualiteHolder(View itemView) {
        super(itemView);

        txtJourActualite = (TextView)itemView.findViewById(R.id.txtJourActualite);
        txtHoraireAcutalite = (TextView)itemView.findViewById(R.id.txtHoraireAcutalite);
        txtActualite = (TextView)itemView.findViewById(R.id.txtActualite);

        itemView.setOnClickListener(this);

    }

    public void bind(Actualite actualite){
        txtJourActualite.setText(actualite.getJour());
        txtHoraireAcutalite.setText(actualite.getHoraire());
        txtActualite.setText(actualite.getActualite());
    }

    @Override
    public void onClick(View v) {

    }
}
