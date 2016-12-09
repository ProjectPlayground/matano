package matano.apkode.net.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainFramelayoutCategorieHolder;


public class MainFramelayoutCategorieAdapter extends RecyclerView.Adapter<MainFramelayoutCategorieHolder> {
    List<String> list;

    public MainFramelayoutCategorieAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MainFramelayoutCategorieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_framelayout, viewGroup, false);
        return new MainFramelayoutCategorieHolder(view);
    }

    @Override
    public void onBindViewHolder(MainFramelayoutCategorieHolder mainFramelayoutCategorieHolder, int position) {
        String s = list.get(position);
        mainFramelayoutCategorieHolder.bind(s);
        mainFramelayoutCategorieHolder.itemView.setTag(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
