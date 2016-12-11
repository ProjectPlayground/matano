package matano.apkode.net.matano.holder.profil;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import matano.apkode.net.matano.activity.EventActivity;
import matano.apkode.net.matano.model.EventObject;


public class ProfilEventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ProfilEventHolder(View itemView) {
        super(itemView);
    }

    public void bind(EventObject eventObject) {
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), EventActivity.class);
        view.getContext().startActivity(intent);
    }
}
