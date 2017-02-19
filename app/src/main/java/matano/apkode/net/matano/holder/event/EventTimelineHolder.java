package matano.apkode.net.matano.holder.event;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;


public class EventTimelineHolder extends RecyclerView.ViewHolder {
    private TextView textViewUsername;
    private TextView textViewDate;
    private ImageView imageViewPhoto;
    private ImageView imageViewPhotoProfil;
    private ImageButton imageButtonLikePhoto;
    private ImageButton imageButtonSharePhoto;
    private LinearLayout linearLayoutUser;
    private TextView textViewCountLike;

    public EventTimelineHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        imageButtonLikePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonLikePhoto);
        imageButtonSharePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonSharePhoto);
        linearLayoutUser = (LinearLayout) itemView.findViewById(R.id.linearLayoutUser);
        textViewCountLike = (TextView) itemView.findViewById(R.id.textViewCountLike);
    }


    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText(s);
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
            if (imageViewPhoto != null && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.color.background_image)
                        //  .centerCrop()
                        .into(imageViewPhoto);


            }
        }
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (s != null) {
            if (imageViewPhotoProfil != null && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.color.background_image)
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

    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public ImageView getImageViewPhotoProfil() {
        return imageViewPhotoProfil;
    }

    public LinearLayout getLinearLayoutUser() {
        return linearLayoutUser;
    }

    public TextView getTextViewCountLike() {
        return textViewCountLike;
    }

    public void setTextViewCountLike(String s) {
        if (s != null) {
            if (textViewCountLike != null) {
                textViewCountLike.setText(s);
            }
        }
    }
}
