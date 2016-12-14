package matano.apkode.net.matano.pageradapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.Photo;


public class PhotoPageAdapter extends PagerAdapter {
    private ArrayList<Photo> photos;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;

    public PhotoPageAdapter(ArrayList<Photo> photos, Activity act, Context ctx) {
        this.photos = photos;
        this.context = ctx;
        this.activity = act;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.card_photo_galerie, container, false);

        Photo photo = photos.get(position);

        ImageView imageViewPreview = (ImageView) view.findViewById(R.id.img);

        container.addView(view);

        return view;

    }


    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
