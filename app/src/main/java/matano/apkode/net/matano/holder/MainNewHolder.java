package matano.apkode.net.matano.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.NewObject;


public class MainNewHolder extends RecyclerView.ViewHolder {
    private ImageView profilePic;

    public MainNewHolder(View itemView) {
        super(itemView);
    }

    public void bind(NewObject newObject) {

        profilePic = (ImageView) itemView.findViewById(R.id.user_image);

    }


}
