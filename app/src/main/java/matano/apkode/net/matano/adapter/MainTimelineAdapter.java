package matano.apkode.net.matano.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Timeline;


public class MainTimelineAdapter extends RecyclerView.Adapter<MainTimelineHolder> {
    List<Timeline> list;

    public MainTimelineAdapter(List<Timeline> list) {
        this.list = list;
    }

    @Override
    public MainTimelineHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_main_timeline, viewGroup, false);
        return new MainTimelineHolder(view);
    }

    @Override
    public void onBindViewHolder(MainTimelineHolder mainTimelineHolder, int position) {
        Timeline timeline = list.get(position);
        mainTimelineHolder.bind(timeline);
        mainTimelineHolder.itemView.setTag(timeline);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
