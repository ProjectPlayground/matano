package matano.apkode.net.matano.fragment.event;

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
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.event.EventParticipantAdapter;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.EventParticipantHolder;
import matano.apkode.net.matano.model.User;

public class EventParticipantFragment extends Fragment {
    private static final String ARG_EVENT_KEY = null;
    private Context context;
    private RecyclerView recyclerView;
    private EventParticipantAdapter eventParticipantAdapter;
    private List<User> participants = new ArrayList<>();
    private String eventKey;
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

    public EventParticipantFragment() {
    }

    public EventParticipantFragment newInstance(Context ctx, String key) {
        context = ctx;
        eventKey = key;
        EventParticipantFragment eventParticipantFragment = new EventParticipantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_KEY, key);
        eventParticipantFragment.setArguments(args);
        return eventParticipantFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user");

        String key = getArguments().getString(ARG_EVENT_KEY);

        if (key == null) {
            // TODO something
        } else {
            refEvent = mRootRef.child("event").child(key);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_participant, container, false);

        textViewParticipantNumer = (TextView) view.findViewById(R.id.textViewParticipantNumer);
        button_participer = (Button) view.findViewById(R.id.button_participer);

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

        Query query = refEvent.child("users");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (textViewParticipantNumer != null) {
                    if (dataSnapshot == null) {
                        textViewParticipantNumer.setText("0 Participants");
                    } else {
                        textViewParticipantNumer.setText(dataSnapshot.getChildrenCount() + " Participants");
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new FirebaseRecyclerAdapter<String, EventParticipantHolder>(String.class, R.layout.card_event_participant, EventParticipantHolder.class, query) {
            @Override
            protected void populateViewHolder(final EventParticipantHolder eventParticipantHolder, final String s, int position) {
                if (s != null) {
                    final String ref = getRef(position).getKey();
                    refUser.child(ref).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final User user = dataSnapshot.getValue(User.class);

                            if (user != null && user.getUsername() != null && user.getPhotoProfl() != null) {
                                eventParticipantHolder.setImageViewPhoto(getContext(), user.getPhotoProfl());
                                eventParticipantHolder.setTextViewUsername(user.getUsername());

                                if (eventParticipantHolder.getImageButtonAddFollowing() != null) {

                                    DatabaseReference reference = mRootRef.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("followings");

                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.getChildrenCount() == 0) {
                                                eventParticipantHolder.getImageButtonAddFollowing().setTag("1");
                                                eventParticipantHolder.getImageButtonAddFollowing().setVisibility(View.VISIBLE);
                                                if (!ref.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                    eventParticipantHolder.getImageButtonAddFollowing().setImageResource(R.mipmap.ic_action_social_person_add_padding);
                                                }
                                            } else {
                                                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                                    if (ref.equals(snap.getKey())) {
                                                        eventParticipantHolder.getImageButtonAddFollowing().setTag(null);
                                                        eventParticipantHolder.getImageButtonAddFollowing().setVisibility(View.VISIBLE);
                                                        if (!ref.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                            eventParticipantHolder.getImageButtonAddFollowing().setImageResource(R.mipmap.ic_action_action_done_all_padding);
                                                        }
                                                    } else {
                                                        eventParticipantHolder.getImageButtonAddFollowing().setTag("1");
                                                        eventParticipantHolder.getImageButtonAddFollowing().setVisibility(View.VISIBLE);
                                                        if (!ref.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                                            eventParticipantHolder.getImageButtonAddFollowing().setImageResource(R.mipmap.ic_action_social_person_add_padding);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    eventParticipantHolder.getImageButtonAddFollowing().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String a = (String) view.getTag();
                                            Log.e(Utils.TAG, "a " + a);
                                            addFollowing(ref, a);
                                        }
                                    });


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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


        recyclerView.setAdapter(adapter);

        if (button_participer != null) {

            isUserParticipe();

            button_participer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (button_participer.getTag().toString()) {
                        case "9":
                            button_participer.setTag("0");
                            break;
                        case "0":
                            button_participer.setTag("9");
                            break;
                        case "1":
                            button_participer.setTag("9");
                            break;
                    }
                    setUserToEvent();
                }
            });
        }


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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void isUserParticipe() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        refEventUser = refEvent.child("users").child(currentUser.getUid());

        refEventUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    button_participer.setText("Participer");
                    button_participer.setTag("9");
                    button_participer.setVisibility(View.VISIBLE);
                } else {
                    String status = dataSnapshot.getValue(String.class);
                    switch (status) {
                        case "0":
                            button_participer.setText("En attente");
                            button_participer.setTag("0");
                            button_participer.setVisibility(View.VISIBLE);
                            break;
                        case "1":
                            button_participer.setText("Je participe");
                            button_participer.setTag("1");
                            button_participer.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setUserToEvent() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String tag = button_participer.getTag().toString();

        switch (tag) {
            case "9":
                tag = null;
                break;
            case "0":
                tag = "0";
                break;
        }


        Map hashMap = new HashMap();
        hashMap.put("event/" + getArguments().getString(ARG_EVENT_KEY) + "/users/" + currentUser.getUid(), tag);
        hashMap.put("user/" + currentUser.getUid() + "/events/" + getArguments().getString(ARG_EVENT_KEY), tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
    }


    private void addFollowing(String uid, String tag) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        String currentUserUid = currentUser.getUid();

        Map hashMap = new HashMap();
        hashMap.put("user/" + uid + "/followers/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/followings/" + uid, tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });


    }


}
