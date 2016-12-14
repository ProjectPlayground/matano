package matano.apkode.net.matano.holder.profil;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import matano.apkode.net.matano.activity.ProfilActivity;
import matano.apkode.net.matano.model.User;


public class ProfilFriendHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ProfilFriendHolder(View itemView) {
        super(itemView);
    }

    public void bind(User user) {
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ProfilActivity.class);
        view.getContext().startActivity(intent);
    }
}
