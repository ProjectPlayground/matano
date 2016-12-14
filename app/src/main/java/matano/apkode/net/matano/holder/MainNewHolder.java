package matano.apkode.net.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Photo;


public class MainNewHolder extends RecyclerView.ViewHolder {
    private ImageView profilePic;

    public MainNewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Photo photo) {

        profilePic = (ImageView) itemView.findViewById(R.id.user_image);

    }


}
