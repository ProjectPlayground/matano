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
import net.apkode.matano.model.Commentaire;
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

    public static ParticipantFragment newInstance(){
        ParticipantFragment participantFragment = new ParticipantFragment();
        return participantFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Log.e("e", "onCreate saveInstance != null");
        }else {
            Log.e("e"," onCreate saveInstance == null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            Log.e("e", "onCreateView saveInstance != null");
        }else {
            Log.e("e"," onCreateView saveInstance == null");
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
            recyclerView.setScrollbarFadingEnabled (true);
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            participants.add(new Participant(1L, "Awa sow", "Hassane" ,"https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            participants.add(new Participant(1L, "Bachir", "Rabo" , "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
            participants.add(new Participant(2L, "Iamel", "Touré" , "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
            participants.add(new Participant(2L, "Issata", "Ousmane" , "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
            participants.add(new Participant(2L, "Aicha", "Kader" , "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
            participants.add(new Participant(1L, "Awa", "Bachir" , "https://goodpitch.org/uploads/cache/user_image/max_400_400_monifa-bandele-b.jpg"));
            participants.add(new Participant(1L, "Karim", "Benzema" , "https://pbs.twimg.com/profile_images/1717956431/BP-headshot-fb-profile-photo_400x400.jpg"));
            participants.add(new Participant(2L, "Bachir", "Mahamadou" , "http://cps-static.rovicorp.com/3/JPG_400/MI0003/643/MI0003643950.jpg?partner=allrovi.com"));
            participants.add(new Participant(2L, "Mahamadou", "Hassane" , "https://pbs.twimg.com/profile_images/637722086547587072/g3kWsOVa.jpg"));
            participants.add(new Participant(2L, "Hdiza", "Mansour" , "http://servotronicstech.com/wp-content/uploads/2015/03/p-1-400x400.jpg"));
            recyclerView.setAdapter(new ParticipantAdapter(participants));
        }

        if (isPassed) {
            Log.e("e", "first time");

        }else {
            Log.e("e","already passed");
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
