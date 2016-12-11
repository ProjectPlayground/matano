package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.event.EventTopParticipantAdapter;
import matano.apkode.net.matano.adapter.event.EventTopPhotoAdapter;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.helper.ClickListener;
import matano.apkode.net.matano.helper.RecyclerTouchListener;
import matano.apkode.net.matano.model.ParticipantObject;
import matano.apkode.net.matano.model.PhotoObject;

public class EventInfoFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerViewTopPhoto;
    private RecyclerView recyclerViewTopParticipant;
    private List<PhotoObject> photoObjects = new ArrayList<>();
    private List<ParticipantObject> participantObjects = new ArrayList<>();
    private EventTopPhotoAdapter eventTopPhotoAdapter;
    private EventTopParticipantAdapter eventTopParticipantAdapter;

    public EventInfoFragment() {
    }

    public EventInfoFragment newInstance(Context ctx) {
        context = ctx;
        EventInfoFragment eventInfoFragment = new EventInfoFragment();
        return eventInfoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventTopPhotoAdapter = new EventTopPhotoAdapter(photoObjects);
        eventTopParticipantAdapter = new EventTopParticipantAdapter(participantObjects);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewTopPhoto = (RecyclerView) view.findViewById(R.id.recyclerViewTopPhoto);
        recyclerViewTopParticipant = (RecyclerView) view.findViewById(R.id.recyclerViewTopParticipant);

        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());
        photoObjects.add(new PhotoObject());


        if (null != recyclerViewTopPhoto) {
            recyclerViewTopPhoto.setHasFixedSize(true);
            recyclerViewTopPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopPhoto.setAdapter(eventTopPhotoAdapter);
        }

        recyclerViewTopPhoto.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewTopPhoto, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("PhotoObject", (Serializable) photoObjects);
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


        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());
        participantObjects.add(new ParticipantObject());

        if (null != recyclerViewTopParticipant) {
            recyclerViewTopParticipant.setHasFixedSize(true);
            recyclerViewTopParticipant.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopParticipant.setAdapter(eventTopParticipantAdapter);
        }


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
