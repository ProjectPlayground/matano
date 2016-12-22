package matano.apkode.net.matano.holder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Event;


public class MainEventHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewDateStart;
    private TextView txtParticipantNumber;
    private ImageView imageViewPhotoProfil;


    public MainEventHolder(View itemView) {
        super(itemView);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
        textViewDateStart = (TextView) itemView.findViewById(R.id.textViewDateStart);
        txtParticipantNumber = (TextView) itemView.findViewById(R.id.txtParticipantNumber);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
    }


    public void setTextViewTitle(String s) {
        if (null != s) {
            if (null != textViewTitle) {
                textViewTitle.setText(s);
            }
        }
    }

    public void setTextViewPlace(String s) {
        if (null != s) {
            if (null != textViewPlace) {
                textViewPlace.setText(s);
            }
        }
    }

    public void setTextViewDateStart(String s) {
        if (null != s) {
            if (null != textViewDateStart) {
                textViewDateStart.setText(s);
            }
        }
    }

    public void setTxtParticipantNumber(int s) {
        if (null != txtParticipantNumber) {
            txtParticipantNumber.setText(s + " Participants");
        }
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (null != s) {
            if (null != imageViewPhotoProfil) {
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.mipmap.img5)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
    }


    public void bind(Event event) {
        //  txtCategorie = (TextView)itemView.findViewById(R.id.txtCategorie);
        //txtCategorie.setText(event.getCategorie());
        // itemView.setOnClickListener(this);
    }


}
