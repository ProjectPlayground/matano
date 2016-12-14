package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventTopPhotoHolder;
import matano.apkode.net.matano.model.Photo;


public class EventTopPhotoAdapter extends RecyclerView.Adapter<EventTopPhotoHolder> {
    List<Photo> list;

    public EventTopPhotoAdapter(List<Photo> list) {
        this.list = list;
    }

    @Override
    public EventTopPhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_top_photo, viewGroup, false);
        return new EventTopPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(EventTopPhotoHolder eventTopPhotoHolder, int position) {
        Photo photo = list.get(position);
        eventTopPhotoHolder.bind(photo);
        eventTopPhotoHolder.itemView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
