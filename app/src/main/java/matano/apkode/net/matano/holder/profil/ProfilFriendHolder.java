package matano.apkode.net.matano.holder.profil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.User;


public class ProfilFriendHolder extends RecyclerView.ViewHolder {
    private ImageView imageViewPhoto;
    private TextView textViewUsername;
    private ImageButton imageButtonAdd;

    public ProfilFriendHolder(View itemView) {
        super(itemView);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
        textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        imageButtonAdd = (ImageButton) itemView.findViewById(R.id.imageButtonAdd);
    }

    public void bind(User user) {

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

    public void setTextViewUsername(String s) {
        if (s != null) {
            if (textViewUsername != null) {
                textViewUsername.setText("?" + s);
            }
        }
    }

    public ImageButton getImageButtonAdd() {
        return imageButtonAdd;
    }

}
