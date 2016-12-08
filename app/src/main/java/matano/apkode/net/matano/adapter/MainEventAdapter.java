package matano.apkode.net.matano.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.EventObject;

public class MainEventAdapter extends RecyclerView.Adapter<MainEventHolder> {

    List<EventObject> list;

    public MainEventAdapter(List<EventObject> list) {
        this.list = list;
    }

    @Override
    public MainEventHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_event, viewGroup, false);
        return new MainEventHolder(view);
    }

    @Override
    public void onBindViewHolder(MainEventHolder mainEventHolder, int position) {
        EventObject eventObject = list.get(position);
        mainEventHolder.bind(eventObject);
        mainEventHolder.itemView.setTag(eventObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
