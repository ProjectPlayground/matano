package matano.apkode.net.matano.fragment.main;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Utils;
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
        Log.e(Utils.TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(Utils.TAG, "onCreate");

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
        Log.e(Utils.TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_main_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(Utils.TAG, "onViewCreated");
        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refEvent.orderByChild("category").equalTo(CATEGORIE);


        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, query) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, Event event, int position) {
                Log.e(Utils.TAG, CATEGORIE);
                displayLayout(mainEventHolder, event);
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        Log.e(Utils.TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
        Log.e(Utils.TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        Log.e(Utils.TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(Utils.TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(Utils.TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
        }
        Log.e(Utils.TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(Utils.TAG, "onDetach");
    }

    private void displayLayout(MainEventHolder mainEventHolder, Event event) {
        String title = event.getTitle();
        String place = event.getPlace();
        String photoProfil = event.getPhotoProfil();

        if (title != null && place != null && photoProfil != null) {
            mainEventHolder.setTextViewTitle(title);
            mainEventHolder.setTextViewPlace(place);
            mainEventHolder.setImageViewPhotoProfil(getActivity(), photoProfil);
        }

    }

}
