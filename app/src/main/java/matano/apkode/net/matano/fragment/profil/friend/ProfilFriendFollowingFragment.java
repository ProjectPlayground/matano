package matano.apkode.net.matano.fragment.profil.friend;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.profil.ProfilFriendHolder;
import matano.apkode.net.matano.model.User;


public class ProfilFriendFollowingFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeUserUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;
    private FirebaseRecyclerAdapter<String, ProfilFriendHolder> adapter;
    private RecyclerView recyclerView;

    public ProfilFriendFollowingFragment() {
    }

    public static ProfilFriendFollowingFragment newInstance(String userUid) {
        ProfilFriendFollowingFragment profilFriendFollowingFragment = new ProfilFriendFollowingFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilFriendFollowingFragment.setArguments(bundle);

        return profilFriendFollowingFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAuthStateListener();

        db = new Db(context);
        fbDatabase = new FbDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_profil_friend_following, container, false);

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

        Query query = fbDatabase.getRefUserFollowings(incomeUserUid);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<String, ProfilFriendHolder>(String.class, R.layout.card_profil_friend, ProfilFriendHolder.class, query) {
            @Override
            protected void populateViewHolder(ProfilFriendHolder profilFriendHolder, String s, int position) {
                if (s != null) {
                    getUser(profilFriendHolder, getRef(position).getKey());
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
        if (mAuthListener != null) {
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
                }
            }
        };
    }


    private void getUser(final ProfilFriendHolder profilFriendHolder, final String userUid) {
        Query query = fbDatabase.getRefUser(userUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    displayLayout(profilFriendHolder, user, userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle error
            }
        });
    }

    private void displayLayout(final ProfilFriendHolder profilFriendHolder, User user, final String userUid) {
        String photoProfl = user.getPhotoProfl();
        String username = user.getUsername();

        if (photoProfl != null && username != null) {

            profilFriendHolder.setTextViewUsername(username);
            profilFriendHolder.setImageViewPhoto(context, photoProfl);

            profilFriendHolder.setCardViewParticipant();

            profilFriendHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });

            profilFriendHolder.getTextViewUsername().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ProfilActivity.class);
                    intent.putExtra(Utils.ARG_USER_UID, userUid);
                    startActivity(intent);
                }
            });


            profilFriendHolder.getRelativeLayoutFriend().setVisibility(View.VISIBLE);
        }

    }

    private void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void finishActivity() {
        getActivity().finish();
    }

}
