package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.profil.ProfilEventAdapter;
import matano.apkode.net.matano.model.EventObject;

public class ProfilEventFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private ProfilEventAdapter profilEventAdapter;
    private List<EventObject> eventObjects = new ArrayList<>();

    public ProfilEventFragment() {
    }

    public ProfilEventFragment newInstance(Context ctx) {
        context = ctx;
        ProfilEventFragment profilEventFragment = new ProfilEventFragment();
        return profilEventFragment;
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
        return inflater.inflate(R.layout.fragment_profil_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        profilEventAdapter = new ProfilEventAdapter(eventObjects);

        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());
        eventObjects.add(new EventObject());

        recyclerView.setAdapter(profilEventAdapter);

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
