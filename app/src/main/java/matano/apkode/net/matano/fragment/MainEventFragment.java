package matano.apkode.net.matano.fragment;

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
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.Event;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MainEventFragment extends Fragment {
    private static final String CATEGORIE = "Culture";
    private App app;
    private String incomeEventUid;
    private Db db;
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Event, MainEventHolder> adapter;


    public MainEventFragment() {
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main_event, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        return view;

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = app.getRefEvents().orderByChild("category").equalTo(CATEGORIE);

        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, query) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, Event event, int position) {
                if (event != null) {
                    if (event.getCategory() != null) {
                        if (event.getCategory().equals(CATEGORIE)) {
                            displayLayout(mainEventHolder, event, getRef(position).getKey());
                        }
                    }
                }
            }
        };

        recyclerView.setAdapter(adapter);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                    intent.putExtra(Utils.ARG_EVENT_UID, refEvent);
                    startActivity(intent);
                }
            });

        }

    }

}
