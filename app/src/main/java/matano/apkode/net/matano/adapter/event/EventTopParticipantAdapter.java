package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventTopParticipantHolder;
import matano.apkode.net.matano.model.User;

public class EventTopParticipantAdapter extends RecyclerView.Adapter<EventTopParticipantHolder> {
    List<User> list;

    public EventTopParticipantAdapter(List<User> list) {
        this.list = list;
    }

    @Override
    public EventTopParticipantHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_top_participant, viewGroup, false);
        return new EventTopParticipantHolder(view);
    }

    @Override
    public void onBindViewHolder(EventTopParticipantHolder eventTopParticipantHolder, int position) {
        User user = list.get(position);

        eventTopParticipantHolder.bind(user);
        eventTopParticipantHolder.itemView.setTag(user);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
