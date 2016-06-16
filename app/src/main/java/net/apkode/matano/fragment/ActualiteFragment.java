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

import net.apkode.matano.R;
import net.apkode.matano.adapter.ActualiteAdapter;
import net.apkode.matano.model.Actualite;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;

import java.util.ArrayList;
import java.util.List;


public class ActualiteFragment extends Fragment {

    public ActualiteFragment() {
    }

    public static ActualiteFragment newInstance(){
        ActualiteFragment actualiteFragment = new ActualiteFragment();
        return actualiteFragment;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView;
        List<Actualite> actualites = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Event event = (Event) bundle.getSerializable("Event");

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            actualites.add(new Actualite(1l, "08/11/2016", "11H10", "Then the activity that hosts the fragment implements the OnArticleSelectedListener interface and overrides onArticleSelected() to notify fragment B of the event from fragment"));
            actualites.add(new Actualite(2l, "12/03/2016", "9H42", "o ensure that the host activity implements this interface, fragment A's onAttach() callback method (which the system calls when adding the fragment to the activity) instantiates an instance of OnArticleSelectedListener by casting the Activity that is passed into onAttach"));
            actualites.add(new Actualite(3l, "02/08/2016", "14H17", "When the activity receives a callback through the interface, it can share the information with other fragments in the layout as necessary."));
            actualites.add(new Actualite(4l, "10/06/2016", "23H33", "Although a Fragment is implemented as an object that's independent from an Activity and can be used inside multiple activities, a given instance of a fragment is directly tied to the activity that contains it."));
            recyclerView.setAdapter(new ActualiteAdapter(actualites));

        }

    }


}
