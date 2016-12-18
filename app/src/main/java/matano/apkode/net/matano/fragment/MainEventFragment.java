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

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.BuildConfig;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.EventActivity;
import matano.apkode.net.matano.holder.MainEventHolder;
import matano.apkode.net.matano.model.Event;


public class MainEventFragment extends Fragment {
    private static final int RC_SIGN_IN = 1;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static Context context;
    private static int pPosition;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseRecyclerAdapter<Event, MainEventHolder> adapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager manager;

    public MainEventFragment() {
    }

    public static MainEventFragment newInstance(int p, Context ctx) {
        pPosition = p;
        context = ctx;

        MainEventFragment mainEventFragment = new MainEventFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, p);
        mainEventFragment.setArguments(args);

        return mainEventFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_event, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mRootRef = database.getReference();
        refEvent = mRootRef.child("event");


        //  TextView textView = (TextView) rootView.findViewById(R.id.txtPosition);
        //  textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //displayVue();
                } else {
                    goSignIn();
                }
            }
        };


        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        manager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(manager);

        Query query = refEvent;

        adapter = new FirebaseRecyclerAdapter<Event, MainEventHolder>(Event.class, R.layout.card_main_event, MainEventHolder.class, query) {
            @Override
            protected void populateViewHolder(MainEventHolder mainEventHolder, final Event event, final int position) {
                event.getDateStart();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
                String date = simpleDateFormat.format(event.getDateStart());

                mainEventHolder.setTextViewEventTitle(event.getTitle());
                mainEventHolder.setTextViewEventPresentation(event.getPresentation());
                mainEventHolder.setImageViewEventPhoto(context, event.getPhotoProfil());

                mainEventHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EventActivity.class);
                        intent.putExtra("eventKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

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
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
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


        mRecyclerView.setAdapter(adapter);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void goSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setLogo(R.mipmap.ic_action_action_account_balance_wallet)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .setProviders(Arrays.asList(
                                // new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())
                        )
                        .build(),
                RC_SIGN_IN);
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
        if (mAuth != null) {
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

}
