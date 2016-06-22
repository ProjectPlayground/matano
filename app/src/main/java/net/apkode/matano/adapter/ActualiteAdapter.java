package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.ActualiteHolder;
import net.apkode.matano.model.Actualite;

import java.text.ParseException;
import java.util.List;

public class ActualiteAdapter extends RecyclerView.Adapter<ActualiteHolder> {
    List<Actualite> list;

    public ActualiteAdapter(List<Actualite> list) {
        this.list = list;
    }

    @Override
    public ActualiteHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_actualite, viewGroup, false);
        return new ActualiteHolder(view);
    }

    @Override
    public void onBindViewHolder(ActualiteHolder actualiteHolder, int position) {
        Actualite actualite = list.get(position);
        try {
            actualiteHolder.bind(actualite);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        actualiteHolder.itemView.setTag(actualite);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
