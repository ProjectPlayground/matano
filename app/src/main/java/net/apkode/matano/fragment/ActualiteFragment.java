package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ActualiteAdapter;
import net.apkode.matano.api.APIActualite;
import net.apkode.matano.interfaces.IActualite;
import net.apkode.matano.model.Actualite;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class ActualiteFragment extends Fragment implements IActualite {
    private APIActualite apiActualite;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public ActualiteFragment() {
    }

    public static ActualiteFragment newInstance() {
        ActualiteFragment actualiteFragment = new ActualiteFragment();
        return actualiteFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiActualite = new APIActualite(this, context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actualite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            if (event != null) {
                apiActualite.getData(event);
                recyclerView.setAdapter(new ActualiteAdapter(new ArrayList<Actualite>()));
            }
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


    @Override
    public void getResponse(List<Actualite> actualites) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(new ActualiteAdapter(actualites));
    }
}
