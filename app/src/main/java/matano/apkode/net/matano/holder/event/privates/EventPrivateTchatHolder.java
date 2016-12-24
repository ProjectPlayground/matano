package matano.apkode.net.matano.holder.event.privates;

import android.content.Context;
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

    public EventPrivateTchatHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewMessage = (TextView) itemView.findViewById(R.id.textViewMessage);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linearLayoutContainer);
        relativeLayoutContainer = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutContainer);
    }

    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText("?" + s);
            }
        }
    }

    public void setTextViewMessage(String s) {
        if (s != null) {
            if (textViewMessage != null) {
                textViewMessage.setText(s);
            }
        }
    }

    public void setImageViewPhotoProfil(Context context, String s) {
        if (s != null) {
            if (imageViewPhotoProfil != null) {
                Glide
                        .with(context)
                        .load(s)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
    }

    /**/
    public void setImageViewPhoto(Context context, String s) {
        if (s != null) {
            if (imageViewPhoto != null) {
                Glide
                        .with(context)
                        .load(s)
                        //  .centerCrop()
                        .into(imageViewPhoto);
            }
        }
    }

    public void setIsSender(Context context, boolean isSender) {
        if (isSender) {
            //linearLayoutContainer.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            relativeLayoutContainer.setGravity(Gravity.END);
        } else {
            //linearLayoutContainer.setBackgroundColor(context.getResources().getColor(R.color.com_facebook_button_background_color));
            relativeLayoutContainer.setGravity(Gravity.START);
        }
    }
}
