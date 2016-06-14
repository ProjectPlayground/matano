package net.apkode.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.CommentaireHolder;
import net.apkode.matano.holder.EventHolder;
import net.apkode.matano.model.Commentaire;

import java.util.List;


public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireHolder> {
    List<Commentaire> list;

    public CommentaireAdapter(List<Commentaire> list) {
        this.list = list;
    }


    @Override
    public CommentaireHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_commentaire, viewGroup, false);
        return new CommentaireHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentaireHolder commentaireHolder, int position) {
        Commentaire commentaire = list.get(position);
        commentaireHolder.bind(commentaire);
        commentaireHolder.itemView.setTag(commentaire);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
