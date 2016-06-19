package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ImageGalerieAdapter;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.ImageGalerie;

import java.util.ArrayList;


public class ImageGalerieFragment extends Fragment {

    public ImageGalerieFragment() {
    }

    public static ImageGalerieFragment newInstance() {
        ImageGalerieFragment imageGalerieFragment = new ImageGalerieFragment();
        return imageGalerieFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView;
        final ArrayList<ImageGalerie> imageGaleries = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            //recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://www.revelryeventdesigners.com/wp-content/uploads/2011/05/Revelry-Event-Designers-Taylor-Wedding.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://www.makingdifferent.com/wp-content/uploads/2015/04/business-event-planning.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://blog.lewispr.com/content/uploads/2015/04/Event.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "https://pixabay.com/static/uploads/photo/2015/10/01/21/39/background-image-967820_960_720.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://www.fourdiamondevents.com/wp-content/uploads/2015/06/SetWidth1920-A-championship-event10.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://community.stagephod.com/wp-content/uploads/2015/02/Events-stagephod.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "https://adrianinitiative.files.wordpress.com/2015/11/event-3.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "http://www.photolakedistrict.co.uk/wp-content/uploads/events-FIREWORKS.jpg"));
            imageGaleries.add(new ImageGalerie(1, "Bachir", "Rabo", "92332322", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            recyclerView.setAdapter(new ImageGalerieAdapter(imageGaleries));

            recyclerView.addOnItemTouchListener(new ImageGalerieAdapter.RecyclerTouchListener(getActivity(), recyclerView, new ImageGalerieAdapter.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("ImageGalerie", imageGaleries);
                    bundle1.putInt("position", position);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    ImageGalerieSlideshow imageGalerieSlideshow = ImageGalerieSlideshow.newInstance();
                    imageGalerieSlideshow.setArguments(bundle1);
                    imageGalerieSlideshow.show(fragmentTransaction, "slideshow");
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }
    }
}
