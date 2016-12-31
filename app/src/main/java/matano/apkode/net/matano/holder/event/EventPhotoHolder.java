package matano.apkode.net.matano.holder.event;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;

public class EventPhotoHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhoto;

    public EventPhotoHolder(View itemView) {
        super(itemView);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
    }


    public void setImageViewPhoto(Context context, String s) {
        if (null != s) {
            if (null != imageViewPhoto && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.mipmap.img4)
                        //  .centerCrop()
                        .into(imageViewPhoto);

            }
        }
    }

}
