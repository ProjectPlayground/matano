package net.apkode.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EventAdapter;
import net.apkode.matano.helper.DBEvent;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class EventPlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context context;
    private List<Event> evenementsCulture = new ArrayList<>();
    private List<Event> evenementsEducation = new ArrayList<>();
    private List<Event> evenementsSport = new ArrayList<>();

    public EventPlaceholderFragment() {
    }


    public static EventPlaceholderFragment newInstance(int sectionNumber, Context ctx) {
        context = ctx;
        EventPlaceholderFragment fragment = new EventPlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        getDataFromApi(rootView);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            case 1:
                recyclerView.setAdapter(new EventAdapter(evenementsCulture));
                break;
            case 2:
                recyclerView.setAdapter(new EventAdapter(evenementsEducation));
                break;
            case 3:
                recyclerView.setAdapter(new EventAdapter(evenementsSport));
                break;

        }

        return rootView;
    }


    private void getDataFromApi(View rootView) {
        DBEvent dbEventCulture = new DBEvent(getContext());
        DBEvent dbEventEducation = new DBEvent(getContext());
        DBEvent dbEventSport = new DBEvent(getContext());

        evenementsCulture = dbEventCulture.getDatasByCategorie("Culture");
        evenementsEducation = dbEventEducation.getDatasByCategorie("Education");
        evenementsSport = dbEventSport.getDatasByCategorie("Sport");
    }

}