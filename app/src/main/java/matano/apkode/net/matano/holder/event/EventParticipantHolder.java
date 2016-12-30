package matano.apkode.net.matano.holder.event;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;

public class EventParticipantHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhoto;
    private TextView textViewUsername;
    private ImageButton imageButtonAddFollowing;
    private CardView cardViewParticipant;

    public EventParticipantHolder(View itemView) {
        super(itemView);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        imageButtonAddFollowing = (ImageButton) itemView.findViewById(R.id.imageButtonAddFollowing);
        cardViewParticipant = (CardView) itemView.findViewById(R.id.cardViewParticipant);
    }

    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public void setImageViewPhoto(Context context, String s) {
        if (s != null) {
            if (imageViewPhoto != null) {
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.mipmap.img4)
                        //  .centerCrop()
                        .into(imageViewPhoto);


            }
        }
    }

    public ImageButton getImageButtonAddFollowing() {
        return imageButtonAddFollowing;
    }

    public CardView getCardViewParticipant() {
        return cardViewParticipant;
    }

    public void setCardViewParticipant() {
        if (cardViewParticipant != null) {
            cardViewParticipant.setVisibility(View.VISIBLE);
        }
    }

    public TextView getTextViewUsername() {
        return textViewUsername;
    }

    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText("?" + s);
            }
        }
    }
}
