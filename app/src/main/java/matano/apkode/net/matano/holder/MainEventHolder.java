package matano.apkode.net.matano.holder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import matano.apkode.net.matano.activity.EventActivity;
import matano.apkode.net.matano.model.EventObject;


public class MainEventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView txtCategorie;

    public MainEventHolder(View itemView) {
        super(itemView);
    }

    public void bind(EventObject eventObject) {
        //  txtCategorie = (TextView)itemView.findViewById(R.id.txtCategorie);
        //txtCategorie.setText(eventObject.getCategorie());
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext().getApplicationContext(), EventActivity.class);
        intent.putExtra("EventObject", (Serializable) view.getTag());
        view.getContext().startActivity(intent);
    }
}
