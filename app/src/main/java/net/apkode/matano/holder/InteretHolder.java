package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.model.Interet;

/**
 * Created by brabo on 6/16/16.
 */
public class InteretHolder extends RecyclerView.ViewHolder {
    private TextView txtTnteresse;

    public InteretHolder(View itemView) {
        super(itemView);
        txtTnteresse = (TextView) itemView.findViewById(R.id.txtInteresse);
    }

    public void bind(Interet interet) {
        txtTnteresse.setText(interet.getInteresse());
    }
}
