package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.PhotoObject;


public class EventPhotoAdapter extends RecyclerView.Adapter<EventPhotoHolder> {
    List<PhotoObject> list;

    public EventPhotoAdapter(List<PhotoObject> list) {
        this.list = list;
    }

    @Override
    public EventPhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_photo, viewGroup, false);
        return new EventPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(EventPhotoHolder eventPhotoHolder, int position) {
        PhotoObject photoObject = list.get(position);
        eventPhotoHolder.bind(photoObject);
        eventPhotoHolder.itemView.setTag(photoObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
