package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.EvennementHolder;
import net.apkode.matano.model.Evennement;

import java.util.List;


public class EvennementAdapter extends RecyclerView.Adapter<EvennementHolder> {
    List<Evennement> list;

    public EvennementAdapter(List<Evennement> list) {
        this.list = list;
    }

    @Override
    public EvennementHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evennement, viewGroup, false);
        return new EvennementHolder(view);
    }

    @Override
    public void onBindViewHolder(EvennementHolder evennementHolder, int position) {
        Evennement evennement = list.get(position);
        evennementHolder.bind(evennement);
        evennementHolder.itemView.setTag(evennement);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
