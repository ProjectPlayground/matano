package matano.apkode.net.matano.fragment.profil;

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

import java.util.ArrayList;
import java.util.List;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.profil.ProfilTicketHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Ticket;

public class ProfilTicketFragment extends Fragment {
    private static final String CURRENT_FRAGMENT = "Mes Tickets";
    private Context context;
    private RecyclerView recyclerView;
    private List<Event> events = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private DatabaseReference refTickets;
    private FirebaseRecyclerAdapter<Ticket, ProfilTicketHolder> adapter;
    private LinearLayoutManager manager;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;
    private String userUid;


    public static ProfilTicketFragment newInstance(String userUid) {
        ProfilTicketFragment profilTicketFragment = new ProfilTicketFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilTicketFragment.setArguments(bundle);

        return profilTicketFragment;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        userUid = getArguments().getString(Utils.ARG_USER_UID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = new LocalStorage(context);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        if (userUid == null) {
            finishActivity();
        }

        if (!localStorage.isContryStored() || currentUserContry == null) {
            goContryActivity();
        }

        if (!localStorage.isCityStored() || currentUserCity == null) {
            goCityActivity();
        }


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user").child(userUid);
        refTickets = refUser.child("tickets");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    finishActivity();
                } else {
                    currentUserUid = user.getUid();
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profil_ticket, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

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


    private void goContryActivity() {
        Intent intent = new Intent(context, ContryActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void goCityActivity() {
        Intent intent = new Intent(context, CityActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void finishActivity() {
        getActivity().finish();
    }


}
