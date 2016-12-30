package matano.apkode.net.matano.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.user.UserEventHolder;
import matano.apkode.net.matano.model.Event;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserEventFragment extends Fragment {
    private App app;
    private String incomeUserUid;
    private Db db;

    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<String, UserEventHolder> adapter;

    public UserEventFragment() {
    }

    public static UserEventFragment newInstance(String userUid) {
        UserEventFragment userEventFragment = new UserEventFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        userEventFragment.setArguments(bundle);

        return userEventFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplicationContext();
        db = new Db(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_user_event, container, false);

        incomeUserUid = getArguments().getString(Utils.ARG_USER_UID);

        if (incomeUserUid == null) {
            finishActivity();
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = app.getRefUserEvents(incomeUserUid);

        adapter = new FirebaseRecyclerAdapter<String, UserEventHolder>(String.class, R.layout.card_user_event, UserEventHolder.class, query) {
            @Override
            protected void populateViewHolder(UserEventHolder userEventHolder, String s, int position) {
                if (s != null) {
                    getEvent(userEventHolder, getRef(position).getKey());
                }
            }
        };

        recyclerView.setAdapter(adapter);

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
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getEvent(final UserEventHolder userEventHolder, final String eventUid) {

        Query query = app.getRefEvent(eventUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Event event = dataSnapshot.getValue(Event.class);
                if (event != null && event.getPhotoProfil() != null && event.getTitle() != null && event.getPlace() != null && event.getTarification() != null) {
                    displayLayout(userEventHolder, eventUid, event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(UserEventHolder userEventHolder, final String eventUid, Event event) {

        String photoProfil = event.getPhotoProfil();
        String title = event.getTitle();
        String place = event.getPlace();
        String tarification = event.getTarification();

        userEventHolder.setImageViewPhotoProfil(context, photoProfil);
        userEventHolder.setTextViewTitle(title);
        userEventHolder.setTextViewPlace(place);
        userEventHolder.setTextViewTarification(tarification);

        userEventHolder.getLinearLayoutEvent().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEventActivity(eventUid);
            }
        });


    }

    private void goEventActivity(String eventUid) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, eventUid);
        startActivity(intent);
    }

    private void finishActivity() {
        getActivity().finish();
    }

}
