package matano.apkode.net.matano.adapter.profil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilTicketHolder;
import matano.apkode.net.matano.model.Event;

public class ProfilTicketAdapter extends RecyclerView.Adapter<ProfilTicketHolder> {
    List<Event> list;

    public ProfilTicketAdapter(List<Event> list) {
        this.list = list;
    }

    @Override
    public ProfilTicketHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_profil_ticket, viewGroup, false);
        return new ProfilTicketHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilTicketHolder profilTicketHolder, int position) {
        Event event = list.get(position);
        profilTicketHolder.bind(event);
        profilTicketHolder.itemView.setTag(event);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
