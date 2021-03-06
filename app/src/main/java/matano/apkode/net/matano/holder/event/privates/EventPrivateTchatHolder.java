package matano.apkode.net.matano.holder.event.privates;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;

public class EventPrivateTchatHolder extends RecyclerView.ViewHolder {
    private TextView textViewUsername;
    private TextView textViewMessage;
    private ImageView imageViewPhotoProfil;
    private ImageView imageViewPhoto;
    private LinearLayout linearLayoutContainer;
    private RelativeLayout relativeLayoutContainer;
    private CardView cardViewPhotoProfil;
    private CardView cardViewContent;


    public EventPrivateTchatHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linearLayoutContainer);
        relativeLayoutContainer = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutContainer);
        cardViewPhotoProfil = (CardView) itemView.findViewById(R.id.cardViewPhotoProfil);
        cardViewContent = (CardView) itemView.findViewById(R.id.cardViewContent);
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (s != null) {
            if (imageViewPhotoProfil != null && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.color.background_image)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
    }

    /**/
    public void setImageViewPhoto(Context context, String s) {
        if (s != null) {
            if (imageViewPhoto != null && context != null) {
                Activity activity = (Activity) context;
                if (activity.isFinishing())
                    return;
                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.color.background_image)
                        //  .centerCrop()
                        .into(imageViewPhoto);
            }
        }
    }

    public void setIsSender(Context context, boolean isSender) {
        if (context != null) {
            if (isSender) {
                linearLayoutContainer.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                relativeLayoutContainer.setGravity(Gravity.END);
            } else {
                linearLayoutContainer.setBackgroundColor(context.getResources().getColor(android.R.color.background_light));
                relativeLayoutContainer.setGravity(Gravity.START);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(68, 0, 0, 0);
                // cardViewContent.setLayoutParams(layoutParams);

                //cardViewPhotoProfil.setLayoutParams(layoutParams);
            }
        }
    }

    public void showContainer() {
        if (relativeLayoutContainer != null) {
            relativeLayoutContainer.setVisibility(View.VISIBLE);
        }
    }

    public void showCardViewPhotoProfil() {
        if (cardViewPhotoProfil != null) {
            cardViewPhotoProfil.setVisibility(View.VISIBLE);
        }
    }

    public ImageView getImageViewPhotoProfil() {
        return imageViewPhotoProfil;
    }

    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public TextView getTextViewMessage() {
        return textViewMessage;
    }

    public void setTextViewMessage(String s) {
        if (s != null) {
            if (textViewMessage != null) {
                textViewMessage.setText(s);
            }
        }
    }

    public TextView getTextViewUsername() {
        return textViewUsername;
    }

    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText(s);
            }
        }
    }
}
