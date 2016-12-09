package matano.apkode.net.matano.adapter.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.EventParticipantHolder;
import matano.apkode.net.matano.model.ParticipantObject;


public class EventParticipantAdapter extends RecyclerView.Adapter<EventParticipantHolder> {
    List<ParticipantObject> list;

    public EventParticipantAdapter(List<ParticipantObject> list) {
        this.list = list;
    }

    @Override
    public EventParticipantHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_event_participant, viewGroup, false);
        return new EventParticipantHolder(view);
    }

    @Override
    public void onBindViewHolder(EventParticipantHolder eventParticipantHolder, int position) {
        ParticipantObject participantObject = list.get(position);
        eventParticipantHolder.bind(participantObject);
        eventParticipantHolder.itemView.setTag(participantObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}