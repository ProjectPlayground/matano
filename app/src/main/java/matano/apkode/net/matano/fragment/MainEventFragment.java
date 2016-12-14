package matano.apkode.net.matano.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.EventActivity;
import matano.apkode.net.matano.activity.SignInActivity;
import matano.apkode.net.matano.adapter.MainEventAdapter;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.User;

public class MainEventFragment extends Fragment implements FirebaseAuth.AuthStateListener {

    private ArrayList<Event> eventList = new ArrayList<>();
    private MainEventAdapter mAdapter;
    private FirebaseUser currentUser;

    private FirebaseDatabase database;

    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;

    private FirebaseRecyclerAdapter<Event, MainEventHolder> adapter;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private RecyclerView mRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        mRootRef = database.getReference();
        refEvent = mRootRef.child("event");
        refUser = mRootRef.child("user");

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(this);

        currentUser = mAuth.getCurrentUser();


        // String key = refEvent.push().getKey();
        //  refEvent.push().setValue(new Event("Festival international de la mode africaine", "Culture", "Mode", "Niger", "Agadez", "Stade d'agadez", "9 rue de la paix", 2.76892, 34.076373, 2.98, new Date(), new Date(), "Une longue presensation du festival de la node", "https://2.bp.blogspot.com/-WPu45Bfj0gc/Tyq68PDD6wI/AAAAAAACDfU/bwIKOTt_PAA/s1600/Grand+finale+Mustafa+Hassanali+collection+at+FIMA+in+Niamey+in+Niger+on+26+November+2011+(2).JPG"));

        // Log.e(Utils.TAG, "key "+key);


        /*mRootRef.push().setValue("hello", new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.e(Utils.TAG, "Compllete reference "+databaseReference.getKey());
            }
        });*/

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_event, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



       /* mAdapter = new MainEventAdapter(eventList);

        eventList.add(new Event("Monday"));
        eventList.add(new Event("Thuesday"));
        eventList.add(new Event("Thurstady"));
        eventList.add(new Event("Wednasday"));
        eventList.add(new Event("Friday"));
        eventList.add(new Event("Saturday"));
        eventList.add(new Event("Sunday"));*/

        //mRecyclerView.setAdapter(mAdapter);
        //refEvent.push().setValue(new Event("Musique"));
        //refEvent.push().setValue(new Event("Danse"));


        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, refEvent) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, final Event event, int position) {

                event.getDateStart();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
                String date = simpleDateFormat.format(event.getDateStart());
                Log.e(Utils.TAG, "date debut " + date);

                mainEventHolder.setTextViewEventTitle(event.getTitle());
                mainEventHolder.setTextViewEventPresentation(event.getPresentation());
                mainEventHolder.setImageViewEventPhoto(getContext(), event.getPhotoProfil());

                mainEventHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EventActivity.class);
                        intent.putExtra("Event", event);
                        startActivity(intent);
                    }
                });

            }

        };


        mRecyclerView.setAdapter(adapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        if (!isSignedIn()) {
            goSignIn();
        } else {
            refUser.child(currentUser.getUid()).setValue(new User("smalllamartin", "Bassirou", "Rabo", "Niger", "Niamey", currentUser.getEmail(), "92332322", "M", new Date(), "Je code donc je suis", currentUser.getPhotoUrl().toString(), null, null, null, null, null, null), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Log.e(Utils.TAG, "Compllete reference " + databaseReference.getKey());
                }
            });
        }
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
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void goSignIn() {
        startActivity(new Intent(getContext(), SignInActivity.class));
    }


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            //Log.e(Utils.TAG, "connecter : " + user.getUid());
            //refUser.child(user.getUid()).setValue( new User("smalllamartin", user.getDisplayName(), "Niger", "Niamey", user.getEmail(), "Je code donc je suis", user.getPhotoUrl().toString()));

        } else {
            goSignIn();
        }
    }

    public boolean isSignedIn() {
        return (currentUser != null);
    }
}
