package net.apkode.matano.holder;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.apkode.matano.R;
import net.apkode.matano.model.MessageListe;


public class MessageListeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView txtNom;
    private TextView txtPrenom;
    private TextView txtJour;
    private TextView txtHoraire;
    private TextView txtMessage;
    private ImageView imvImage;


    public MessageListeHolder(View itemView) {
        super(itemView);
        txtNom = (TextView)itemView.findViewById(R.id.txtNom);
    //    txtPrenom = (TextView)itemView.findViewById(R.id.txtPrenom);
       // txtJour = (TextView)itemView.findViewById(R.id.txtJour);
       // txtHoraire = (TextView)itemView.findViewById(R.id.txtHoraire);
        txtMessage = (TextView)itemView.findViewById(R.id.txtMessage);
        imvImage = (ImageView)itemView.findViewById(R.id.imvImage);

        itemView.setOnClickListener(this);
    }

    public void bind(MessageListe messageListe){
        txtNom.setText(messageListe.getNom());
     //   txtPrenom.setText(messageListe.getPrenom());
      //  txtJour.setText(messageListe.getJour());
      //  txtHoraire.setText(messageListe.getHoraire());
        txtMessage.setText(messageListe.getMessage());

       /* Glide.with(imvImage.getContext())
                .load(messageListe.getImage())
                .crossFade()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(imvImage.getResources().getDrawable(R.mipmap.placeholder_femme))
                .into(imvImage);*/
    }

    @Override
    public void onClick(View v) {

    }
}
