package matano.apkode.net.matano.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.EventActivity;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.Event;

public class MainEventEducationFragment extends Fragment {
    private static final String CATEGORIE = "Education";
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private FirebaseRecyclerAdapter<Event, MainEventHolder> adapter;
    private LinearLayoutManager manager;

    public MainEventEducationFragment() {
    }

    public MainEventEducationFragment newInstance(Context context) {
        this.context = context;
        MainEventEducationFragment mainEventEducationFragment = new MainEventEducationFragment();
        return mainEventEducationFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refEvent = mRootRef.child("event");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //TODO someting
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refEvent.orderByChild("category").equalTo(CATEGORIE);


        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, query) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, Event event, int position) {
                displayLayout(mainEventHolder, event, getRef(position).getKey());
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void displayLayout(MainEventHolder mainEventHolder, Event event, final String refEvent) {
        String title = event.getTitle();
        String place = event.getPlace();
        String tarification = event.getTarification();
        String photoProfil = event.getPhotoProfil();
        Date date = event.getDate();
        int users = 0;

        if (event.getUsers() != null) {
            users = event.getUsers().size();
        }

        if (title != null && place != null && photoProfil != null && date != null && tarification != null) {
            mainEventHolder.setTextViewTitle(title);
            mainEventHolder.setTextViewPlace(place);
            mainEventHolder.setTextViewTarification(tarification);
            mainEventHolder.setImageViewPhotoProfil(getActivity(), photoProfil);
            mainEventHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
            mainEventHolder.setTxtParticipantNumber(users);

            mainEventHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), EventActivity.class);
                    intent.putExtra("eventKey", refEvent);
                    startActivity(intent);
                }
            });

        }

    }

}
