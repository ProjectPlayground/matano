package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.EventParticipantHolder;
import matano.apkode.net.matano.model.User;

public class EventParticipantFragment extends Fragment {
    private static final String CURRENT_FRAGMENT = "Participants";
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventParticipantHolder> adapter;
    private TextView textViewParticipantNumer;
    private Button button_participer;
    private DatabaseReference refEventUser;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;
    private String eventUid;


    public EventParticipantFragment() {
    }

    public static EventParticipantFragment newInstance(String eventUid) {
        EventParticipantFragment eventParticipantFragment = new EventParticipantFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventParticipantFragment.setArguments(bundle);

        return eventParticipantFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        eventUid = getArguments().getString(Utils.ARG_EVENT_UID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = new LocalStorage(context);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        if (eventUid == null) {
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
        refEvent = mRootRef.child("event").child(eventUid);
        refUser = mRootRef.child("user");

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

        ActionBar supportActionBar = ((EventActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(CURRENT_FRAGMENT);
        }

        View view = inflater.inflate(R.layout.fragment_event_participant, container, false);

        textViewParticipantNumer = (TextView) view.findViewById(R.id.textViewParticipantNumer);
        button_participer = (Button) view.findViewById(R.id.button_participer);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refEvent.child("users");

        adapter = new FirebaseRecyclerAdapter<String, EventParticipantHolder>(String.class, R.layout.card_event_participant, EventParticipantHolder.class, query) {
            @Override
            protected void populateViewHolder(final EventParticipantHolder eventParticipantHolder, final String s, int position) {
                if (s != null) {
                    getUser(eventParticipantHolder, getRef(position).getKey());
                    if (textViewParticipantNumer != null) {
                        textViewParticipantNumer.setText(getItemCount() + " " + getResources().getString(R.string.participants));
                    }

                } else {
                    textViewParticipantNumer.setText("0" + " " + getResources().getString(R.string.participants));
                }

            }
        };

        recyclerView.setAdapter(adapter);

    }

    private void getUser(final EventParticipantHolder eventParticipantHolder, final String userUid) {
        Query query = refUser.child(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    displayLayout(eventParticipantHolder, user, userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle error
            }
        });
    }

    private void displayLayout(EventParticipantHolder eventParticipantHolder, User user, final String userUid) {
        String photoProfl = user.getPhotoProfl();
        String username = user.getUsername();

        if (photoProfl != null && username != null) {

            eventParticipantHolder.setTextViewUsername(username);
            eventParticipantHolder.setImageViewPhoto(context, photoProfl);

            ImageButton imageButtonAddFollowing = eventParticipantHolder.getImageButtonAddFollowing();

            if (!userUid.equals(currentUserUid)) {
                isUserMyFriend(imageButtonAddFollowing, userUid);
            }

            imageButtonAddFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFollowing(userUid, (String) view.getTag());
                }
            });

            eventParticipantHolder.setCardViewParticipant();

            eventParticipantHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void isUserMyFriend(final ImageButton imageButtonAddFollowing, final String userUid) {
        Query query = refUser.child(currentUserUid).child("followings");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    imageButtonAddFollowing.setTag("1");
                    imageButtonAddFollowing.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (snap.getKey().equals(userUid)) {
                            // We are friends
                            imageButtonAddFollowing.setTag(null);
                            imageButtonAddFollowing.setImageResource(R.mipmap.ic_action_social_people_padding);
                        } else {
                            // we are not friends
                            imageButtonAddFollowing.setTag("1");
                            imageButtonAddFollowing.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                        }
                    }
                }
                imageButtonAddFollowing.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle error
            }
        });
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


    private void addFollowing(String userUid, String tag) {
        Map hashMap = new HashMap();
        hashMap.put("user/" + userUid + "/followers/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/followings/" + userUid, tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
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
