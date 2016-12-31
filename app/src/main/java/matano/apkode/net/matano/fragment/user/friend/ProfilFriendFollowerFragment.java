package matano.apkode.net.matano.fragment.user.friend;

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
import android.widget.ImageButton;

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
import matano.apkode.net.matano.holder.user.UserFriendHolder;
import matano.apkode.net.matano.model.User;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfilFriendFollowerFragment extends Fragment {
    private App app;
    private String incomeUserUid;
    private Db db;

    private Context context;
    private FirebaseRecyclerAdapter<String, UserFriendHolder> adapter;
    private RecyclerView recyclerView;

    public ProfilFriendFollowerFragment() {
    }

    public static ProfilFriendFollowerFragment newInstance(String userUid) {
        ProfilFriendFollowerFragment profilFriendFollowerFragment = new ProfilFriendFollowerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilFriendFollowerFragment.setArguments(bundle);

        return profilFriendFollowerFragment;
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

        View view = inflater.inflate(R.layout.fragment_user_friend_follower, container, false);

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

        Query query = app.getRefUserFollowers(incomeUserUid);

        adapter = new FirebaseRecyclerAdapter<String, UserFriendHolder>(String.class, R.layout.card_user_friend, UserFriendHolder.class, query) {
            @Override
            protected void populateViewHolder(UserFriendHolder userFriendHolder, String s, int position) {
                if (s != null) {
                    getUser(userFriendHolder, getRef(position).getKey());
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }


    private void getUser(final UserFriendHolder userFriendHolder, final String userUid) {
        Query query = app.getRefUser(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    displayLayout(userFriendHolder, user, userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle error
            }
        });
    }

    private void displayLayout(final UserFriendHolder userFriendHolder, User user, final String userUid) {
        String photoProfl = user.getPhotoProfl();
        String username = user.getUsername();

        if (photoProfl != null && username != null) {

            userFriendHolder.setTextViewUsername(username);
            userFriendHolder.setImageViewPhoto(context, photoProfl);

            ImageButton imageButtonAddFollowing = userFriendHolder.getImageButtonAddFollowing();

            if (!userUid.equals(app.getCurrentUserUid())) {
                isUserMyFriend(imageButtonAddFollowing, userUid);
            }

            imageButtonAddFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.setFollowing(userUid, (String) view.getTag(), app.getCurrentUserUid());
                }
            });

            userFriendHolder.setCardViewParticipant();

            userFriendHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });

            userFriendHolder.getTextViewUsername().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });

            userFriendHolder.getRelativeLayoutFriend().setVisibility(View.VISIBLE);

        }

    }

    private void isUserMyFriend(final ImageButton imageButtonAddFollowing, final String userUid) {
        Query query = app.getRefUserFollowings(app.getCurrentUserUid());

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


    private void finishActivity() {
        getActivity().finish();
    }



}
