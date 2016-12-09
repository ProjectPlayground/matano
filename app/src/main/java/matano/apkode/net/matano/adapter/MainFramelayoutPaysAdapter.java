package matano.apkode.net.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainFramelayoutPaysHolder;


public class MainFramelayoutPaysAdapter extends RecyclerView.Adapter<MainFramelayoutPaysHolder> {
    List<String> list;

    public MainFramelayoutPaysAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MainFramelayoutPaysHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_framelayout, viewGroup, false);
        return new MainFramelayoutPaysHolder(view);
    }

    @Override
    public void onBindViewHolder(MainFramelayoutPaysHolder mainFramelayoutPaysHolder, int position) {
        String s = list.get(position);
        mainFramelayoutPaysHolder.bind(s);
        mainFramelayoutPaysHolder.itemView.setTag(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
