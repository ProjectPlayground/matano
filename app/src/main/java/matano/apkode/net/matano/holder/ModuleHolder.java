package matano.apkode.net.matano.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;


public class ModuleHolder extends RecyclerView.ViewHolder {
    private TextView textViewTitle;
    private TextView textViewSubTitle;
    private TextView textViewContent;
    private ImageButton imageButton;
    private CardView cardView;

    public ModuleHolder(View itemView) {
        super(itemView);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        textViewSubTitle = (TextView) itemView.findViewById(R.id.textViewSubTitle);
        textViewContent = (TextView) itemView.findViewById(R.id.textViewContent);
        imageButton = (ImageButton) itemView.findViewById(R.id.imageButton);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
    }

    public void setTextViewTitle(String s) {
        if (s != null) {
            if (textViewTitle != null) {
                textViewTitle.setText(s);
            }
        }
    }

    public void setTextViewSubTitle(String s) {
        if (s != null) {
            if (textViewSubTitle != null) {
                textViewSubTitle.setText(s);
            }
        }
    }

    public void setTextViewContent(String s) {
        if (s != null) {
            if (textViewContent != null) {
                textViewContent.setText(s);
                textViewContent.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setImageViewPhoto(Context context, String s) {
        if (null != s) {
            if (null != imageButton && context != null) {

                Glide
                        .with(context)
                        .load(s)
                        .placeholder(R.color.background_image)
                        //  .centerCrop()
                        .into(imageButton);

                cardView.setVisibility(View.VISIBLE);

            }
        }
    }


}
