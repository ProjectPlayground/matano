package matano.apkode.net.matano.adapter.profil;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilPhotoHolder;
import matano.apkode.net.matano.model.PhotoObject;


public class ProfilPhotoAdapter extends RecyclerView.Adapter<ProfilPhotoHolder> {
    List<PhotoObject> list;

    public ProfilPhotoAdapter(List<PhotoObject> list) {
        this.list = list;
    }

    @Override
    public ProfilPhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_profil_photo, viewGroup, false);
        return new ProfilPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilPhotoHolder profilPhotoHolder, int position) {
        PhotoObject photoObject = list.get(position);
        profilPhotoHolder.bind(photoObject);
        profilPhotoHolder.itemView.setTag(photoObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
