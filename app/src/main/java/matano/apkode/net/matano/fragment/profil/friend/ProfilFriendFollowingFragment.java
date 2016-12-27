package matano.apkode.net.matano.fragment.profil.friend;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ImageView;

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

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.ProfilActivity;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.profil.ProfilFriendHolder;
import matano.apkode.net.matano.model.User;


public class ProfilFriendFollowingFragment extends Fragment {
    private static String ARG_USER_UID = "userUid";
    private FirebaseAuth mAuth;
    private Context context;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private FirebaseRecyclerAdapter<String, ProfilFriendHolder> adapter;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;


    public ProfilFriendFollowingFragment() {
    }

    public ProfilFriendFollowingFragment newInstance(Context ctx, String userUid) {
        context = ctx;
        ProfilFriendFollowingFragment profilFriendFollowingFragment = new ProfilFriendFollowingFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_UID, userUid);
        profilFriendFollowingFragment.setArguments(bundle);
        ARG_USER_UID = userUid;
        return profilFriendFollowingFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user").child(ARG_USER_UID);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // TODO go sign in
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_profil_friend_following, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refUser.child("followings");

        adapter = new FirebaseRecyclerAdapter<String, ProfilFriendHolder>(String.class, R.layout.card_profil_friend_following, ProfilFriendHolder.class, query) {
            @Override
            protected void populateViewHolder(ProfilFriendHolder profilFriendHolder, String s, int position) {
                if (s != null) {
                    displayInformationFollowings(profilFriendHolder, getRef(position).getKey());
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

    private void displayInformationFollowings(final ProfilFriendHolder profilFriendHolder, final String userUid) {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        final String currentUserUid = currentUser.getUid();

        DatabaseReference reference = mRootRef.child("user").child(userUid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    profilFriendHolder.setImageViewPhoto(getContext(), user.getPhotoProfl());
                    profilFriendHolder.setTextViewUsername(user.getUsername());

                    ImageView imageViewPhoto = profilFriendHolder.getImageViewPhoto();

                    if (imageViewPhoto != null) {

                        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), ProfilActivity.class);
                                intent.putExtra("userUid", userUid);
                                startActivity(intent);

                            }
                        });

                    }

                    if (!userUid.equals(currentUserUid)) {
                        final ImageButton imageButtonAddOrSetting = profilFriendHolder.getImageButtonAddOrSetting();
                        if (imageButtonAddOrSetting != null) {
                            DatabaseReference databaseReference = mRootRef.child("user").child(currentUserUid).child("followings");

                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(final DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                                        imageButtonAddOrSetting.setVisibility(View.VISIBLE);
                                        imageButtonAddOrSetting.setTag("1");

                                        imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                addFollowing(userUid, (String) view.getTag());
                                            }
                                        });

                                    } else {
                                        for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                            if (userUid.equals(snap.getKey())) {
                                                imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_people_padding);
                                                imageButtonAddOrSetting.setVisibility(View.VISIBLE);
                                                imageButtonAddOrSetting.setTag(null);

                                                imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        addFollowing(userUid, (String) view.getTag());
                                                    }
                                                });
                                            } else {
                                                imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                                                imageButtonAddOrSetting.setVisibility(View.VISIBLE);
                                                imageButtonAddOrSetting.setTag("1");

                                                imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        addFollowing(userUid, (String) view.getTag());
                                                    }
                                                });
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
