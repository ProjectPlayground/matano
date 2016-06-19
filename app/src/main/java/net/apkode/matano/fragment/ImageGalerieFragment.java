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
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ImageGalerieAdapter;
import net.apkode.matano.api.APIImageGalerie;
import net.apkode.matano.interfac.IImageGalerie;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.ImageGalerie;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ImageGalerieFragment extends Fragment implements IImageGalerie {
    private APIImageGalerie apiImageGalerie;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

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
        apiImageGalerie = new APIImageGalerie(this, context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            if (event != null) {
                apiImageGalerie.getData(event);
                recyclerView.setAdapter(new ImageGalerieAdapter(new ArrayList<ImageGalerie>()));
            }
        }
    }

    @Override
    public void getResponse(final List<ImageGalerie> imageGaleries) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new ImageGalerieAdapter(imageGaleries));
        recyclerView.addOnItemTouchListener(new ImageGalerieAdapter.RecyclerTouchListener(getActivity(), recyclerView, new ImageGalerieAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("ImageGalerie", (Serializable) imageGaleries);
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
