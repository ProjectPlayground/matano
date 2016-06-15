package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.Commentaire;

public class CommentaireHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtNomCommentaire;
    private TextView txtJourCommentaire;
    private TextView txtHoraireCommentaire;
    private TextView txtCommentaire;
    private ImageView imvImageCommentaire;

    public CommentaireHolder(View itemView) {
        super(itemView);
        txtNomCommentaire = (TextView)itemView.findViewById(R.id.txtNomCommentaire);
        txtJourCommentaire = (TextView)itemView.findViewById(R.id.txtJourCommentaire);
       // txtHoraireCommentaire = (TextView)itemView.findViewById(R.id.txtHoraireCommentaire);
        txtCommentaire = (TextView)itemView.findViewById(R.id.txtCommentaire);
        imvImageCommentaire = (ImageView)itemView.findViewById(R.id.imvImageCommentaire);

        itemView.setOnClickListener(this);
    }

    public void bind(Commentaire commentaire){
        txtNomCommentaire.setText(commentaire.getNom());
        txtJourCommentaire.setText(commentaire.getJour());
      //  txtHoraireCommentaire.setText(commentaire.getHoraire());
        txtCommentaire.setText(commentaire.getCommentaire());

        Glide.with(imvImageCommentaire.getContext())
                .load(commentaire.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imvImageCommentaire.getResources().getDrawable(R.mipmap.placeholder_femme))
                .into(imvImageCommentaire);
    }

    @Override
    public void onClick(View v) {

    }
}
