package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.ActualiteHolder;
import net.apkode.matano.holder.InteretHolder;
import net.apkode.matano.model.Interet;

import java.util.List;

/**
 * Created by brabo on 6/16/16.
 */
public class InteretAdapter extends RecyclerView.Adapter<InteretHolder> {
    List<Interet> list;

    public InteretAdapter(List<Interet> list) {
        this.list = list;
    }

    @Override
    public InteretHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_interet, viewGroup, false);
        return new InteretHolder(view);
    }

    @Override
    public void onBindViewHolder(InteretHolder interetHolder, int position) {
        Interet interet = list.get(position);
        interetHolder.bind(interet);
        interetHolder.itemView.setTag(interet);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
