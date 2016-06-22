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
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.adapter.ActualiteAdapter;
import net.apkode.matano.api.APIActualite;
import net.apkode.matano.interfaces.IActualite;
import net.apkode.matano.model.Actualite;
import net.apkode.matano.model.Evennement;

import java.util.ArrayList;
import java.util.List;


public class ActualiteFragment extends Fragment implements IActualite {
    private static boolean isActualitePassed = false;
    private APIActualite apiActualite;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Actualite> actualitesListe = new ArrayList<>();
    private Evennement evennement;

    public ActualiteFragment() {
    }

    public static ActualiteFragment newInstance() {
        isActualitePassed = true;

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

        recyclerView.setAdapter(new ActualiteAdapter(actualitesListe));

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        Bundle bundle = getArguments();
        if (bundle != null) {
            evennement = (Evennement) bundle.getSerializable("Evennement");

            if (evennement != null) {
                if (isActualitePassed) {
                    apiActualite.getData(evennement);
                    isActualitePassed = false;
                } else {
                    if (actualitesListe.size() == 0) {
                        apiActualite.getData(evennement);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
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
        if (actualites == null) {
            apiActualite.getData(evennement);
        } else {
            if (actualites.size() == 0) {
               try {
                   Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
                   progressBar.setVisibility(View.GONE);
               }catch (Exception e){
                   e.getMessage();
               }
            } else {
                try {
                    progressBar.setVisibility(View.GONE);
                    actualitesListe = actualites;
                    recyclerView.setAdapter(new ActualiteAdapter(actualitesListe));
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }


    }
}
