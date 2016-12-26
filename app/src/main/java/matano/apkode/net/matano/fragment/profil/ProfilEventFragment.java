package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.profil.ProfilEventHolder;
import matano.apkode.net.matano.model.Event;

public class ProfilEventFragment extends Fragment {
    private static String ARG_USER_UID = "userUid";
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private FirebaseRecyclerAdapter<String, ProfilEventHolder> adapter;
    private LinearLayoutManager manager;

    public ProfilEventFragment() {
    }

    public ProfilEventFragment newInstance(String userUid) {
        ProfilEventFragment profilEventFragment = new ProfilEventFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_UID, userUid);
        profilEventFragment.setArguments(bundle);
        ARG_USER_UID = userUid;
        return profilEventFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user").child(ARG_USER_UID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // TODO go sign in
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profil_event, container, false);
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

        Query query = refUser.child("events");

        adapter = new FirebaseRecyclerAdapter<String, ProfilEventHolder>(String.class, R.layout.card_profil_event, ProfilEventHolder.class, query) {
            @Override
            protected void populateViewHolder(ProfilEventHolder profilEventHolder, String s, int position) {
                if (s != null) {
                    displayUserInformation(profilEventHolder, getRef(position).getKey());
                }
            }
        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
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


    private void displayUserInformation(final ProfilEventHolder profilEventHolder, String eventUid) {

        DatabaseReference reference = mRootRef.child("event").child(eventUid);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                if (event != null) {

                    String photoProfil = event.getPhotoProfil();
                    if (photoProfil != null) {
                        profilEventHolder.setImageViewPhotoProfil(getContext(), photoProfil);
                    }

                    String title = event.getTitle();
                    if (title != null) {
                        profilEventHolder.setTextViewTitle(title);
                    }

                    String place = event.getPlace();

                    if (place != null) {
                        profilEventHolder.setTextViewPlace(place);
                    }

                    String tarification = event.getTarification();

                    if (tarification != null) {
                        profilEventHolder.setTextViewTarification(tarification);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
