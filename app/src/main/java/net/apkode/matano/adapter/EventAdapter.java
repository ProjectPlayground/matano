package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.EventHolder;
import net.apkode.matano.model.Event;

import java.util.List;


/**
 * Created by brabo on 6/2/16.
 */
public class EventAdapter extends RecyclerView.Adapter<EventHolder> {
    List<Event> list;

    public EventAdapter(List<Event> list) {
        this.list = list;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event, viewGroup, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(EventHolder eventHolder, int position) {
        Event event = list.get(position);
        eventHolder.bind(event);
        eventHolder.itemView.setTag(event);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
