package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventNewHolder;
import matano.apkode.net.matano.model.Photo;


public class EventNewAdapter extends RecyclerView.Adapter<EventNewHolder> {
    List<Photo> list;

    public EventNewAdapter(List<Photo> list) {
        this.list = list;
    }

    @Override
    public EventNewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_new, viewGroup, false);
        return new EventNewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventNewHolder eventNewHolder, int position) {
        Photo photo = list.get(position);
        eventNewHolder.bind(photo);
        eventNewHolder.itemView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
