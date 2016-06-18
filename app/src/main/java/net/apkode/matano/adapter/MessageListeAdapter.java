package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.MessageListeHolder;
import net.apkode.matano.model.MessageListe;

import java.util.List;


public class MessageListeAdapter extends RecyclerView.Adapter<MessageListeHolder> {
    List<MessageListe> list;

    public MessageListeAdapter(List<MessageListe> list) {
        this.list = list;
    }

    @Override
    public MessageListeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_message_liste, viewGroup, false);
        return new MessageListeHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageListeHolder messageListeHolder, int position) {
        MessageListe messageListe = list.get(position);
        messageListeHolder.bind(messageListe);
        messageListeHolder.itemView.setTag(messageListe);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
