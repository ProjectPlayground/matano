package matano.apkode.net.matano.fragment;

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
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.CountryActivity;
import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.Event;

public class MainEventFragment extends Fragment {
    private static final String CATEGORIE = "Culture";
    private FbDatabase fbDatabase;
    private Db db;
    private LocalStorage localStorage;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;


    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Event, MainEventHolder> adapter;

    private ProgressBar progressBar;

    private String country;
    private String city;

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

        db = new Db(context);
        fbDatabase = new FbDatabase();
        localStorage = new LocalStorage(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main_event, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        country = localStorage.getCountry();
        city = localStorage.getCity();

        if (null == country) {
            goCountry();
        } else {
            if (null == city) {
                goCity();
            }
        }

        createAuthStateListener();

    }

    private void goCountry() {
        Intent intent = new Intent(context, CountryActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void goCity() {
        Intent intent = new Intent(context, CityActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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

    private void createAuthStateListener() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    goLogin();
                } else {
                    currentUserUid = currentUser.getUid();
                    createView();
                }
            }
        };
    }

    private void createView() {
        Query query = fbDatabase.getRefEvents().orderByChild("city").equalTo(city);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, query) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, Event event, int position) {
                if (event != null) {
                    if (event.getCategory() != null) {
                        displayLayout(mainEventHolder, event, getRef(position).getKey());
                    }
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        });

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void displayLayout(MainEventHolder mainEventHolder, Event event, final String refEvent) {
        progressBar.setVisibility(View.GONE);

        String title = event.getTitle();
        String place = event.getPlace();
        String category = event.getCategory();
        String subCategory = event.getSubCategory();
        String photoProfil = event.getPhotoProfil();
        Date date = event.getDate();
        int users = 0;

        if (event.getUsers() != null) {
            users = event.getUsers().size();
        }

        if (title != null && place != null && photoProfil != null && date != null && category != null && subCategory != null) {
            mainEventHolder.setTextViewTitle(title);
            mainEventHolder.setTextViewPlace(place);
            mainEventHolder.setTextViewCategory(category);
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

    private void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
