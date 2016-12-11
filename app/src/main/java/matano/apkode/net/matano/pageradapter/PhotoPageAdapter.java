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
import matano.apkode.net.matano.model.PhotoObject;


public class PhotoPageAdapter extends PagerAdapter {
    private ArrayList<PhotoObject> photoObjects;
    private LayoutInflater layoutInflater;
    private Context context;
    private Activity activity;

    public PhotoPageAdapter(ArrayList<PhotoObject> photoObjects, Activity act, Context ctx) {
        this.photoObjects = photoObjects;
        this.context = ctx;
        this.activity = act;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.card_photo_galerie, container, false);

        PhotoObject photoObject = photoObjects.get(position);

        ImageView imageViewPreview = (ImageView) view.findViewById(R.id.img);

        container.addView(view);

        return view;

    }


    @Override
    public int getCount() {
        return photoObjects.size();
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
