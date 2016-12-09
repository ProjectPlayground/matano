package matano.apkode.net.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainFramelayoutVilleHolder;


public class MainFramelayoutVilleAdapter extends RecyclerView.Adapter<MainFramelayoutVilleHolder> {
    List<String> list;

    public MainFramelayoutVilleAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MainFramelayoutVilleHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_framelayout, viewGroup, false);
        return new MainFramelayoutVilleHolder(view);
    }

    @Override
    public void onBindViewHolder(MainFramelayoutVilleHolder mainFramelayoutVilleHolder, int position) {
        String s = list.get(position);
        mainFramelayoutVilleHolder.bind(s);
        mainFramelayoutVilleHolder.itemView.setTag(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
