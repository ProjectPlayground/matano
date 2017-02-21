package matano.apkode.net.matano.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.UserActivity;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.model.Timeline;


public class MainTimelineHolder extends RecyclerView.ViewHolder {
    private TextView textViewUsername;
    private TextView textViewDate;
    private TextView textViewTitle;
    private ImageView imageViewPhoto;
    private ImageView imageViewPhotoProfil;
    private ImageButton imageButtonLikePhoto;
    private ImageButton imageButtonSharePhoto;
    private CardView cardView;
    private TextView textViewCountLike;
    private LinearLayout linearLayoutTitle;

    public MainTimelineHolder(View itemView) {
        super(itemView);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
        textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        imageViewPhotoProfil = (ImageView) itemView.findViewById(R.id.imageViewPhotoProfil);
        imageButtonLikePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonLikePhoto);
        imageButtonSharePhoto = (ImageButton) itemView.findViewById(R.id.imageButtonSharePhoto);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        linearLayoutTitle = (LinearLayout) itemView.findViewById(R.id.linearLayoutTitle);
        textViewCountLike = (TextView) itemView.findViewById(R.id.textViewCountLike);
    }

    public void bind(final Timeline timeline) {
        setTextViewUsername(timeline.getUsername());
        setTextViewTitle(timeline.getTitle());
        setTextViewDate(timeline.getDateString());
        setImageViewPhotoProfil(timeline.getContext(), timeline.getPhotoProfl());

        setImageViewPhoto(timeline.getActivity(), timeline.getUrl());
        getImageViewPhoto().setTag(0);
        getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getLinearLayoutTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEventActivity(timeline);
            }
        });

        getImageViewPhotoProfil().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUserActivity(timeline);
            }
        });


        setContainerVisibility();

    }

    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText(s);
            }
        }
    }

    public void setTextViewDate(String s) {
        if (s != null) {
            if (textViewDate != null) {
                textViewDate.setText(s);

            }
        }
    }

    public void setTextViewTitle(String s) {
        if (s != null) {
            if (textViewTitle != null) {
                textViewTitle.setText(s);
            }
        }
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
                        .placeholder(R.color.background_image)
                        //  .centerCrop()
                        .into(imageViewPhoto);


            }
        }
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

                imageViewPhotoProfil.setVisibility(View.VISIBLE);

            }
        }
    }

    public void setContainerVisibility() {
        if (cardView != null) {
            cardView.setVisibility(View.VISIBLE);
        }
    }

    public ImageButton getImageButtonLikePhoto() {
        return imageButtonLikePhoto;
    }

    public ImageButton getImageButtonSharePhoto() {
        return imageButtonSharePhoto;
    }

    public ImageView getImageViewPhoto() {
        return imageViewPhoto;
    }

    public LinearLayout getLinearLayoutTitle() {
        return linearLayoutTitle;
    }

    public TextView getTextViewCountLike() {
        return textViewCountLike;
    }

    public void setTextViewCountLike(String s) {
        if (s != null) {
            if (textViewCountLike != null) {
                textViewCountLike.setText(s);
                textViewCountLike.setVisibility(View.VISIBLE);
            }
        }

    }

    public ImageView getImageViewPhotoProfil() {
        return imageViewPhotoProfil;
    }

    private void goEventActivity(Timeline timeline) {
        Intent intent = new Intent(timeline.getContext(), EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, timeline.getEventUid());
        timeline.getContext().startActivity(intent);
    }

    private void goUserActivity(Timeline timeline) {
        Intent intent = new Intent(timeline.getContext(), UserActivity.class);
        intent.putExtra(Utils.ARG_USER_UID, timeline.getUserUid());
        timeline.getContext().startActivity(intent);
    }


}
