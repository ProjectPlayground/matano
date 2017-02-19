package matano.apkode.net.matano.holder.user;

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

public class UserTimelineHolder extends RecyclerView.ViewHolder {
    private TextView textViewDate;
    private ImageView imageViewPhoto;
    private TextView textViewTitle;
    private ImageButton imageButtonLikePhoto;
    private ImageButton imageButtonSharePhoto;
    private LinearLayout linearLayoutTitle;

    public UserTimelineHolder(View itemView) {
        super(itemView);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        imageButtonLikePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonLikePhoto);
        imageButtonSharePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonSharePhoto);
        linearLayoutTitle = (LinearLayout) itemView.findViewById(R.id.linearLayoutTitle);
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

    public void setTextViewTitle(String s) {
        if (s != null) {
            if (textViewTitle != null) {
                textViewTitle.setText(s);
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


    public LinearLayout getLinearLayoutTitle() {
        return linearLayoutTitle;
    }
}
