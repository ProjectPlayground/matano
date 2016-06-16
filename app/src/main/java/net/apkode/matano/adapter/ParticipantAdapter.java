package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.ParticipantHolder;
import net.apkode.matano.model.Participant;

import java.util.List;

/**
 * Created by brabo on 6/14/16.
 */
public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantHolder> {
    List<Participant> list;

    public ParticipantAdapter(List<Participant> list) {
        this.list = list;
    }

    @Override
    public ParticipantHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_participant, viewGroup, false);
        return new ParticipantHolder(view);
    }

    @Override
    public void onBindViewHolder(ParticipantHolder participantHolder, int position) {
        Participant participant = list.get(position);
        participantHolder.bind(participant);
        participantHolder.itemView.setTag(participant);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
