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
import net.apkode.matano.adapter.ParticipantAdapter;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brabo on 6/15/16.
 */
public class ParticipantFragment extends Fragment {
    private boolean isPassed;

    public ParticipantFragment() {
    }

    public static ParticipantFragment newInstance() {
        ParticipantFragment participantFragment = new ParticipantFragment();
        return participantFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.e("e", "onCreate saveInstance != null");
        } else {
            Log.e("e", " onCreate saveInstance == null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.e("e", "onCreateView saveInstance != null");
        } else {
            Log.e("e", " onCreateView saveInstance == null");
        }

        return inflater.inflate(R.layout.fragment_participant, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView;
        List<Participant> participants = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setScrollbarFadingEnabled(true);
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            participants.add(new Participant(1, "Awa sow", "Hassane", "92332322", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            participants.add(new Participant(1, "Bachir", "Rabo", "92332322", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
            participants.add(new Participant(2, "Iamel", "Touré", "92332322", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
            participants.add(new Participant(2, "Issata", "Ousmane", "92332322", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
            participants.add(new Participant(2, "Aicha", "Kader", "92332322", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
            participants.add(new Participant(1, "Awa", "Bachir", "92332322", "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            participants.add(new Participant(1, "Karim", "Benzema", "92332322", "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
            participants.add(new Participant(2, "Bachir", "Mahamadou", "92332322", "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
            participants.add(new Participant(2, "Mahamadou", "Hassane", "92332322", "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
            participants.add(new Participant(2, "Hdiza", "Mansour", "92332322", "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
            recyclerView.setAdapter(new ParticipantAdapter(participants));
        }

        if (isPassed) {
            Log.e("e", "first time");

        } else {
            Log.e("e", "already passed");
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isPassed = true;
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPassed = false;
    }

}
