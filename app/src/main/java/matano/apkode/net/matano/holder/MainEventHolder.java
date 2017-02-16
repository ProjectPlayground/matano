package matano.apkode.net.matano.holder;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;


public class MainEventHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewDate;
    private TextView txtParticipantNumber;
    private ImageView imageViewPhotoProfil;
    private TextView textViewCategory;


    public MainEventHolder(View itemView) {
        super(itemView);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        txtParticipantNumber = (TextView) itemView.findViewById(R.id.txtParticipantNumber);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        textViewCategory = (TextView) itemView.findViewById(R.id.textViewCategory);
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

    public void setTextViewCategory(String s) {
        if (null != s) {
            if (textViewCategory != null) {
                textViewCategory.setText(s);
            }
        }
    }


    public void setTextViewDate(String s) {
        if (null != s) {
            if (null != textViewDate) {
                textViewDate.setText(s);
            }
        }
    }

    public void setTxtParticipantNumber(int s) {
        if (null != txtParticipantNumber) {
            txtParticipantNumber.setText(s + "");
        }
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (null != s) {
            if (null != imageViewPhotoProfil && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        //.placeholder(R.mipmap.img5)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
    }

}
