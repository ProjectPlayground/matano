package matano.apkode.net.matano.holder.profil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Event;


public class ProfilEventHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhotoProfil;
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewTarification;

    public ProfilEventHolder(View itemView) {
        super(itemView);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
        textViewTarification = (TextView) itemView.findViewById(R.id.textViewTarification);

    }

    public void bind(Event event) {
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (null != s) {
            if (null != imageViewPhotoProfil) {
                Glide
                        .with(context)
                        .load(s)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
    }

    public void setTextViewTitle(String s) {
        if (s != null) {
            if (textViewTitle != null) {
                textViewTitle.setText(s);
            }
        }
    }

    public void setTextViewPlace(String s) {
        if (s != null) {
            if (textViewPlace != null) {
                textViewPlace.setText(s);
            }
        }
    }

    public void setTextViewTarification(String s) {
        if (s != null) {
            if (textViewTarification != null) {
                textViewTarification.setText(s);
            }
        }
    }

}
