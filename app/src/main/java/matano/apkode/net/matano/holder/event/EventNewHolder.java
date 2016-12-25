package matano.apkode.net.matano.holder.event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Photo;


public class EventNewHolder extends RecyclerView.ViewHolder {
    private TextView textViewUsername;
    private TextView textViewDate;
    private ImageView imageViewPhoto;
    private ImageView imageViewPhotoProfil;
    private ImageButton imageButtonLikePhoto;
    private ImageButton imageButtonSharePhoto;

    public EventNewHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        imageButtonLikePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonLikePhoto);
        imageButtonSharePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonSharePhoto);
    }

    public void bind(Photo photo) {

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

    public ImageButton getImageButtonLikePhoto() {
        return imageButtonLikePhoto;
    }

    public ImageButton getImageButtonSharePhoto() {
        return imageButtonSharePhoto;
    }
}
