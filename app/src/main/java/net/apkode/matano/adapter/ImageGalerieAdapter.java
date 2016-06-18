package net.apkode.matano.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.holder.ImageGalerieHolder;
import net.apkode.matano.model.ImageGalerie;

import java.util.List;


public class ImageGalerieAdapter extends RecyclerView.Adapter<ImageGalerieHolder> {

    List<ImageGalerie> list;

    public ImageGalerieAdapter(List<ImageGalerie> list) {
        this.list = list;
    }

    @Override
    public ImageGalerieHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_image_galerie, viewGroup, false);
        return new ImageGalerieHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageGalerieHolder imageGalerieHolder, int position) {
        ImageGalerie imageGalerie = list.get(position);
        imageGalerieHolder.bind(imageGalerie);
        imageGalerieHolder.itemView.setTag(imageGalerie);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);

    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ImageGalerieAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ImageGalerieAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
