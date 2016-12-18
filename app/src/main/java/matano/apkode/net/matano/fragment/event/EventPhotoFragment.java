package matano.apkode.net.matano.fragment.event;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.event.EventPhotoAdapter;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.helper.ClickListener;
import matano.apkode.net.matano.helper.RecyclerTouchListener;
import matano.apkode.net.matano.model.Photo;

public class EventPhotoFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private EventPhotoAdapter eventPhotoAdapter;
    private List<Photo> photos = new ArrayList<>();
    private String eventKey;

    public EventPhotoFragment() {
    }

    public EventPhotoFragment newInstance(Context ctx, String key) {
        context = ctx;
        eventKey = key;
        EventPhotoFragment eventPhotoFragment = new EventPhotoFragment();
        return eventPhotoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("Photo", (Serializable) photos);
                bundle.putInt("position", position);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                PhotoDialog photoDialog = new PhotoDialog();

                photoDialog.setArguments(bundle);

                photoDialog.show(fragmentTransaction, "PhotoDialog");


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        eventPhotoAdapter = new EventPhotoAdapter(photos);

        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());

        recyclerView.setAdapter(eventPhotoAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}