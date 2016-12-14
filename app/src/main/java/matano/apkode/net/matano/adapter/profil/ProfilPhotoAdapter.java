package matano.apkode.net.matano.adapter.profil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilPhotoHolder;
import matano.apkode.net.matano.model.Photo;


public class ProfilPhotoAdapter extends RecyclerView.Adapter<ProfilPhotoHolder> {
    List<Photo> list;

    public ProfilPhotoAdapter(List<Photo> list) {
        this.list = list;
    }

    @Override
    public ProfilPhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_profil_photo, viewGroup, false);
        return new ProfilPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilPhotoHolder profilPhotoHolder, int position) {
        Photo photo = list.get(position);
        profilPhotoHolder.bind(photo);
        profilPhotoHolder.itemView.setTag(photo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
