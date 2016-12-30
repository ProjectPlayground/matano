package matano.apkode.net.matano.fragment.event;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.UserActivity;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.EventParticipantHolder;
import matano.apkode.net.matano.model.User;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EventParticipantFragment extends Fragment {
    private App app;
    private String incomeEventUid;
    private Db db;

    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<String, EventParticipantHolder> adapter;
    private TextView textViewParticipantNumer;
    private Button button_participer;


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

        View view = inflater.inflate(R.layout.fragment_event_participant, container, false);

        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

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

        Query query = app.getRefEventUsers(incomeEventUid);

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
        Query query = app.getRefUser(userUid);

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

            if (!userUid.equals(app.getCurrentUserUid())) {
                isUserMyFriend(imageButtonAddFollowing, userUid);
            }

            imageButtonAddFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.setFollowing(userUid, (String) view.getTag(), app.getCurrentUserUid());
                }
            });

            eventParticipantHolder.setCardViewParticipant();

            eventParticipantHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });
        }

    }


    private void isUserMyFriend(final ImageButton imageButtonAddOrSetting, final String userUid) {
        Query query = app.getRefUserFollowings(app.getCurrentUserUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    imageButtonAddOrSetting.setTag("1");
                    imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (snap.getKey().equals(userUid)) {
                            // We are friends
                            imageButtonAddOrSetting.setTag(null);
                            imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_people_padding);
                        } else {
                            // we are not friends
                            imageButtonAddOrSetting.setTag("1");
                            imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                        }
                    }
                }
                imageButtonAddOrSetting.setVisibility(View.VISIBLE);
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


    private void finishActivity() {
        getActivity().finish();
    }

}
