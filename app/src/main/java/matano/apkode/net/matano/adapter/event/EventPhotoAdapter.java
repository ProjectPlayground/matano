package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Photo;


public class EventPhotoAdapter extends RecyclerView.Adapter<EventPhotoHolder> {
    List<Photo> list;

    public EventPhotoAdapter(List<Photo> list) {
        this.list = list;
    }

    @Override
    public EventPhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_photo, viewGroup, false);
        return new EventPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(EventPhotoHolder eventPhotoHolder, int position) {
        Photo photo = list.get(position);
        eventPhotoHolder.bind(photo);
        eventPhotoHolder.itemView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
