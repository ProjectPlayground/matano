package matano.apkode.net.matano.dialogfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.model.Photo;


public class PhotoDialog extends DialogFragment {
    private ViewPager viewPager;
    private PhotoPageAdapter photoPageAdapter;
    private int selectedPosition = 0;
    private TextView lblCount;
    private ArrayList<Photo> photos;
    private ImageButton closePhotoDialog;
    private String userUid;


    public static PhotoDialog newInstance() {
        PhotoDialog photoDialog = new PhotoDialog();
        return photoDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_galerie, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        closePhotoDialog = (ImageButton) v.findViewById(R.id.closePhotoDialog);

        if (null != closePhotoDialog) {
            closePhotoDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
        }


        photos = (ArrayList<Photo>) getArguments().getSerializable(Utils.ARG_PHOTO_DIALOG);
        selectedPosition = getArguments().getInt(Utils.ARG_PHOTO_DIALOG_POSITION);
        userUid = getArguments().getString(Utils.ARG_USER_UID);

        if (photos == null || userUid == null) {
            finishActivity();
        }

        photoPageAdapter = new PhotoPageAdapter(getActivity(), getContext());
        viewPager.setAdapter(photoPageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                displayMetaInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setCurrentItem(selectedPosition);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + "/" + photos.size());
        //  ImageGalerie imageGalerie = imageGaleries.get(position);
    }

    private void finishActivity() {
        getActivity().finish();
    }


    /**
     * PagerAdapter
     */

    public class PhotoPageAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private Context context;
        private Activity activity;

        public PhotoPageAdapter(Activity act, Context ctx) {
            this.context = ctx;
            this.activity = act;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.card_photo_galerie, container, false);

            Photo photo = photos.get(position);

            ImageView imageViewPhoto = (ImageView) view.findViewById(R.id.imageViewPhoto);

            final String url = photo.getUrl();

            if (imageViewPhoto != null && url != null) {
                Glide
                        .with(context)
                        .load(url)
                        //  .centerCrop()
                        .into(imageViewPhoto);

                imageViewPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

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

}
