package matano.apkode.net.matano.adapter.profil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilFriendHolder;
import matano.apkode.net.matano.model.User;


public class ProfilFriendAdapter extends RecyclerView.Adapter<ProfilFriendHolder> {
    List<User> list;

    public ProfilFriendAdapter(List<User> list) {
        this.list = list;
    }

    @Override
    public ProfilFriendHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_profil_friend, viewGroup, false);
        return new ProfilFriendHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilFriendHolder profilFriendHolder, int position) {
        User user = list.get(position);
        profilFriendHolder.bind(user);
        profilFriendHolder.itemView.setTag(user);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
