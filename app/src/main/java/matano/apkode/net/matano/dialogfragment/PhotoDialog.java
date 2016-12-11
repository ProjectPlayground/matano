package matano.apkode.net.matano.dialogfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.PhotoObject;
import matano.apkode.net.matano.pageradapter.PhotoPageAdapter;


public class PhotoDialog extends DialogFragment {
    private ViewPager viewPager;
    private PhotoPageAdapter photoPageAdapter;
    private int selectedPosition = 0;
    private TextView lblCount;
    private ArrayList<PhotoObject> photoObjects;
    private ImageButton closePhotoDialog;

    public static PhotoDialog newInstance() {
        PhotoDialog photoDialog = new PhotoDialog();
        return photoDialog;
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


        photoObjects = (ArrayList<PhotoObject>) getArguments().getSerializable("PhotoObject");
        selectedPosition = getArguments().getInt("position");

        photoPageAdapter = new PhotoPageAdapter(photoObjects, getActivity(), getContext());
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

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    private void displayMetaInfo(int position) {
        lblCount.setText((position + 1) + "/" + photoObjects.size());

        //  ImageGalerie imageGalerie = imageGaleries.get(position);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

}
