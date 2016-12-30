package matano.apkode.net.matano.holder.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;


public class UserEventHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhotoProfil;
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewTarification;
    private LinearLayout linearLayoutEvent;

    public UserEventHolder(View itemView) {
        super(itemView);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
        textViewTarification = (TextView) itemView.findViewById(R.id.textViewTarification);
        linearLayoutEvent = (LinearLayout) itemView.findViewById(R.id.linearLayoutEvent);

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

    public LinearLayout getLinearLayoutEvent() {
        return linearLayoutEvent;
    }
}
