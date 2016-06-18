package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.ImageGalerieHolder;
import net.apkode.matano.model.ImageGalerie;

import java.util.List;


public class ImageGalerieAdapter extends RecyclerView.Adapter<ImageGalerieHolder> {

    List<ImageGalerie> list;

    public ImageGalerieAdapter(List<ImageGalerie> list) {
        this.list = list;
    }

    @Override
    public ImageGalerieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_image_galerie, viewGroup, false);
        return new ImageGalerieHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageGalerieHolder imageGalerieHolder, int position) {
        ImageGalerie imageGalerie = list.get(position);
        imageGalerieHolder.bind(imageGalerie);
        imageGalerieHolder.itemView.setTag(imageGalerie);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
