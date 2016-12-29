package matano.apkode.net.matano.holder.event.privates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;

public class EventPrivatePhotoHolder extends RecyclerView.ViewHolder {
    private TextView textViewUsername;
    private TextView textViewDate;
    private ImageView imageViewPhoto;
    private ImageView imageViewPhotoProfil;
    private LinearLayout linearLayoutUser;

    public EventPrivatePhotoHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        linearLayoutUser = (LinearLayout) itemView.findViewById(R.id.linearLayoutUser);
    }


    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText("?" + s);
            }
        }
    }

    public void setTextViewDate(String s) {
        if (s != null) {
            if (textViewDate != null) {
                textViewDate.setText(s);
            }
        }
    }

    public void setImageViewPhoto(Context context, String s) {
        if (s != null) {
            if (imageViewPhoto != null) {
                Glide
                        .with(context)
                        .load(s)
                        // .placeholder(R.mipmap.img4)
                        //  .centerCrop()
                        .into(imageViewPhoto);


            }
        }
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (s != null) {
            if (imageViewPhotoProfil != null) {
                Glide
                        .with(context)
                        .load(s)
                        //.placeholder(R.mipmap.img4)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);


            }
        }
    }

    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public ImageView getImageViewPhotoProfil() {
        return imageViewPhotoProfil;
    }

    public LinearLayout getLinearLayoutUser() {
        return linearLayoutUser;
    }


}
