package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EvennementAdapter;
import net.apkode.matano.api.APIEvennement;
import net.apkode.matano.db.DBEvennement;
import net.apkode.matano.interfaces.IEvennement;
import net.apkode.matano.model.Evennement;

import java.util.ArrayList;
import java.util.List;


public class EvennementsFragment extends Fragment implements IEvennement {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context context;
    private List<Evennement> evenementsCulture = new ArrayList<>();
    private List<Evennement> evenementsEducation = new ArrayList<>();
    private List<Evennement> evenementsSport = new ArrayList<>();

    private APIEvennement apiEvennement;

    public EvennementsFragment() {
    }


    public static EvennementsFragment newInstance(int sectionNumber, Context ctx) {
        context = ctx;
        EvennementsFragment fragment = new EvennementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiEvennement = new APIEvennement(this, context);
        Log.e("e", "onAttache");
        getDataFromApi();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evennements, container, false);
        Log.e("e", "onCreateView");


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                Log.e("e", "onCreateView evenementsCulture");
                recyclerView.setAdapter(new EvennementAdapter(evenementsCulture));
                break;
            case 2:
                Log.e("e", "onCreateView evenementsEducation");
                recyclerView.setAdapter(new EvennementAdapter(evenementsEducation));
                break;
            case 3:
                Log.e("e", "onCreateView evenementsSport");
                recyclerView.setAdapter(new EvennementAdapter(evenementsSport));
                break;

        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("e", "onViewCreated");
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

    private void getDataFromApi() {
        Log.e("e", "getDataFromApi");
        DBEvennement dbEvennementCulture = new DBEvennement(getContext());
        DBEvennement dbEvennementEducation = new DBEvennement(getContext());
        DBEvennement dbEvennementSport = new DBEvennement(getContext());

        evenementsCulture = dbEvennementCulture.getDatasByCategorie("Culture");
        evenementsEducation = dbEvennementEducation.getDatasByCategorie("Education");
        evenementsSport = dbEvennementSport.getDatasByCategorie("Sport");
    }

    @Override
    public void getResponses(List<Evennement> evennements) {

    }

    @Override
    public void getResponse(List<Evennement> evennements) {

    }
}