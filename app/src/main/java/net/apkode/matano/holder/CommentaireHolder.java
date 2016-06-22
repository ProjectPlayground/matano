package net.apkode.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.Commentaire;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommentaireHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtNomCommentaire;
    private TextView txtPrenomCommentaire;
    private TextView txtJourCommentaire;
    private TextView txtCommentaire;
    private ImageView imvImageCommentaire;

    public CommentaireHolder(View itemView) {
        super(itemView);
        txtNomCommentaire = (TextView) itemView.findViewById(R.id.txtNomCommentaire);
        txtPrenomCommentaire = (TextView) itemView.findViewById(R.id.txtPrenomCommentaire);
        txtJourCommentaire = (TextView) itemView.findViewById(R.id.txtJourCommentaire);
        txtCommentaire = (TextView) itemView.findViewById(R.id.txtCommentaire);
        imvImageCommentaire = (ImageView) itemView.findViewById(R.id.imvImageCommentaire);

        itemView.setOnClickListener(this);
    }

    public void bind(Commentaire commentaire) throws ParseException {
        txtNomCommentaire.setText(commentaire.getNom());
        txtPrenomCommentaire.setText(commentaire.getPrenom());

        txtCommentaire.setText(commentaire.getCommentaire());

        Glide.with(imvImageCommentaire.getContext())
                .load(commentaire.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvImageCommentaire);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String dateInString = commentaire.getJour();

        txtJourCommentaire.setText((CharSequence) formatter.parse(dateInString));
    }

    @Override
    public void onClick(View v) {

    }
}
