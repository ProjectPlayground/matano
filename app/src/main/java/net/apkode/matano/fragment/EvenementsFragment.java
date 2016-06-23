package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EvennementAdapter;
import net.apkode.matano.api.APIEvenement;
import net.apkode.matano.interfaces.IEvenement;
import net.apkode.matano.model.Evenement;

import java.util.ArrayList;
import java.util.List;


public class EvenementsFragment extends Fragment implements IEvenement {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context context;
    private static boolean isEvenementPassed = false;
    private List<Evenement> evenementsCulture = new ArrayList<>();
    private List<Evenement> evenementsEducation = new ArrayList<>();
    private List<Evenement> evenementsSport = new ArrayList<>();
    private RecyclerView recyclerView;
    private APIEvenement apiEvenement;
    private EvennementAdapter cultureAdapter;
    private EvennementAdapter educationAdapter;
    private EvennementAdapter sportAdpater;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public EvenementsFragment() {
    }


    public static EvenementsFragment newInstance(int sectionNumber, Context ctx) {
        context = ctx;
        EvenementsFragment fragment = new EvenementsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        isEvenementPassed = true;
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apiEvenement = new APIEvenement(this, context);
        // getDataFromApi();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_evenements, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cultureAdapter = new EvennementAdapter(evenementsCulture);
        educationAdapter = new EvennementAdapter(evenementsEducation);
        sportAdpater = new EvennementAdapter(evenementsSport);

        progressBar = (ProgressBar) view.findViewById(R.id.loading);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshLayout();
                    }
                }
        );


        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                recyclerView.setAdapter(cultureAdapter);
                if (isEvenementPassed) {
                    apiEvenement.getDataCulture();
                    isEvenementPassed = false;
                } else {
                    if (evenementsCulture.size() == 0) {
                        apiEvenement.getDataCulture();
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                break;
            case 2:
                recyclerView.setAdapter(educationAdapter);
                if (isEvenementPassed) {
                    apiEvenement.getDataEducation();
                    isEvenementPassed = false;
                } else {
                    if (evenementsEducation.size() == 0) {
                        apiEvenement.getDataEducation();
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                break;
            case 3:
                recyclerView.setAdapter(sportAdpater);
                if (isEvenementPassed) {
                    apiEvenement.getDataSport();
                    isEvenementPassed = false;
                } else {
                    if (evenementsSport.size() == 0) {
                        apiEvenement.getDataSport();
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
                break;
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
    public void getResponses(List<Evenement> evenements) {

    }

    @Override
    public void getResponsesCulture(List<Evenement> evenements) {
        Log.e("e", "size " + evenements.size());
        if (evenements == null) {
            apiEvenement.getDataCulture();
        } else {
            evenementsCulture = evenements;

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                try {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(new EvennementAdapter(evenementsCulture));
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }

    @Override
    public void getResponsesEducation(List<Evenement> evenements) {
        if (evenements == null) {
            apiEvenement.getDataCulture();
        } else {
            evenementsEducation = evenements;

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                try {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(new EvennementAdapter(evenementsEducation));
                } catch (Exception e) {
                    e.getMessage();
                }
            }

        }
    }

    @Override
    public void getResponsesSport(List<Evenement> evenements) {
        if (evenements == null) {
            apiEvenement.getDataCulture();
        } else {
            evenementsSport = evenements;

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                try {
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(new EvennementAdapter(evenementsSport));
                } catch (Exception e) {
                    e.getMessage();
                }
            }

        }
    }

    @Override
    public void getResponse(List<Evenement> evenements) {

    }

    private void refreshLayout() {
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                apiEvenement.getDataCulture();
                break;
            case 2:
                apiEvenement.getDataEducation();
                break;

            case 3:
                apiEvenement.getDataSport();
                break;
        }
    }
}