package matano.apkode.net.matano.holder.user;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;


public class UserFriendHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhoto;
    private TextView textViewUsername;
    private ImageButton imageButtonAddOrSetting;
    private ImageButton imageButtonAddFollowing;
    private CardView cardViewParticipant;
    private RelativeLayout relativeLayoutFriend;

    public UserFriendHolder(View itemView) {
        super(itemView);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        imageButtonAddOrSetting = (ImageButton) itemView.findViewById(R.id.imageButtonAddOrSetting);
        imageButtonAddFollowing = (ImageButton) itemView.findViewById(R.id.imageButtonAddFollowing);
        cardViewParticipant = (CardView) itemView.findViewById(R.id.cardViewParticipant);
        relativeLayoutFriend = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutFriend);
    }


    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public void setImageViewPhoto(Context context, String s) {
        if (s != null) {
            if (imageViewPhoto != null && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
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

    public RelativeLayout getRelativeLayoutFriend() {
        return relativeLayoutFriend;
    }
}
