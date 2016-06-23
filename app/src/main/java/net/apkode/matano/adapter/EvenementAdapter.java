package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.EvenementHolder;
import net.apkode.matano.model.Evenement;

import java.text.ParseException;
import java.util.List;


public class EvenementAdapter extends RecyclerView.Adapter<EvenementHolder> {
    List<Evenement> list;

    public EvenementAdapter(List<Evenement> list) {
        this.list = list;
    }

    @Override
    public EvenementHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evenement, viewGroup, false);
        return new EvenementHolder(view);
    }

    @Override
    public void onBindViewHolder(EvenementHolder evenementHolder, int position) {
        Evenement evenement = list.get(position);
        try {
            evenementHolder.bind(evenement);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        evenementHolder.itemView.setTag(evenement);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
