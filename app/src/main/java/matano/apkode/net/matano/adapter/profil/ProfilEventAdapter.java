package matano.apkode.net.matano.adapter.profil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilEventHolder;
import matano.apkode.net.matano.model.EventObject;


public class ProfilEventAdapter extends RecyclerView.Adapter<ProfilEventHolder> {
    List<EventObject> list;

    public ProfilEventAdapter(List<EventObject> list) {
        this.list = list;
    }

    @Override
    public ProfilEventHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_profil_event, viewGroup, false);
        return new ProfilEventHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilEventHolder profilEventHolder, int position) {
        EventObject eventObject = list.get(position);
        profilEventHolder.bind(eventObject);
        profilEventHolder.itemView.setTag(eventObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}