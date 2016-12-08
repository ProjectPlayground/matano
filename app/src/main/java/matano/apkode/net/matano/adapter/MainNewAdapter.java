package matano.apkode.net.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainNewHolder;
import matano.apkode.net.matano.model.NewObject;


public class MainNewAdapter extends RecyclerView.Adapter<MainNewHolder> {
    List<NewObject> list;

    public MainNewAdapter(List<NewObject> list) {
        this.list = list;
    }

    @Override
    public MainNewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_new, viewGroup, false);
        return new MainNewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainNewHolder mainNewHolder, int position) {
        NewObject newObject = list.get(position);
        mainNewHolder.bind(newObject);
        mainNewHolder.itemView.setTag(mainNewHolder);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
