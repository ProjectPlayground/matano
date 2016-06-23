package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.ImageGalerie;


public class ImageGalerieHolder extends RecyclerView.ViewHolder {
    private ImageView thumbnail;

    public ImageGalerieHolder(View itemView) {
        super(itemView);
        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
    }

    public void bind(ImageGalerie imageGalerie) {

        Glide.with(thumbnail.getContext()).load(imageGalerie.getImagegalerie())
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.color.grey300)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(thumbnail);
    }

}
