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
    private TextView textViewEventTitle;
    private TextView textViewEventPresentation;
    private ImageView imageViewEventPhoto;


    public MainEventHolder(View itemView) {
        super(itemView);
        textViewEventTitle = (TextView) itemView.findViewById(R.id.textViewEventTitle);
        textViewEventPresentation = (TextView) itemView.findViewById(R.id.textViewEventPresentation);
        imageViewEventPhoto = (ImageView) itemView.findViewById(R.id.imageViewEventPhoto);
    }


    public void setTextViewEventTitle(String s) {
        if (null != s) {
            if (null != textViewEventTitle) {
                textViewEventTitle.setText(s);
            }
        }
    }

    public void setTextViewEventPresentation(String s) {
        if (null != s) {
            if (null != textViewEventPresentation) {
                textViewEventPresentation.setText(s);
            }
        }
    }

    public void setImageViewEventPhoto(Context context, String s) {
        if (null != s) {
            if (null != imageViewEventPhoto) {
                Glide
                        .with(context)
                        .load(s)
                        //  .centerCrop()
                        .into(imageViewEventPhoto);
            }
        }
    }


    public void bind(Event event) {
        //  txtCategorie = (TextView)itemView.findViewById(R.id.txtCategorie);
        //txtCategorie.setText(event.getCategorie());
        // itemView.setOnClickListener(this);
    }


}
