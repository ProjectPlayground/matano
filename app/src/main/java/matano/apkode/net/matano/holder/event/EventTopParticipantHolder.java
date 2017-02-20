package matano.apkode.net.matano.holder.event;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import matano.apkode.net.matano.ProfilActivity;


public class EventTopParticipantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public EventTopParticipantHolder(View itemView) {
        super(itemView);
    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), ProfilActivity.class);
        view.getContext().startActivity(intent);
    }
}
