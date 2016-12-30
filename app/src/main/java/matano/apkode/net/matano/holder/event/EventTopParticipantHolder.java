package matano.apkode.net.matano.holder.event;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import matano.apkode.net.matano.UserActivity;
import matano.apkode.net.matano.model.User;


public class EventTopParticipantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public EventTopParticipantHolder(View itemView) {
        super(itemView);
    }

    public void bind(User user) {
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), UserActivity.class);
        view.getContext().startActivity(intent);
    }
}
