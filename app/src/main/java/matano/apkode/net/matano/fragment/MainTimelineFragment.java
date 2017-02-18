package matano.apkode.net.matano.fragment;

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

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;


public class MainTimelineFragment extends Fragment {
    private FbDatabase fbDatabase;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser;
    private String currentUserUid;

    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Photo, MainTimelineHolder> adapter;


    public MainTimelineFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_timeline, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        Log.e(Utils.TAG, "TImeline");
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    goLogin();
                } else {
                    currentUserUid = currentUser.getUid();
                    setViewAdaper();
                }
            }
        };
    }

    private void setViewAdaper() {
        Query query = fbDatabase.getRefUserFollowings(currentUserUid);


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                dataSnapshot.getValue(String.class);
                getUser(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userUid = dataSnapshot.getValue(String.class);

                if(userUid != null) {
                    getUser(userUid);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void getUser(final String userUid) {
        Query query = fbDatabase.getRefUser(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    getAdaterPhotos(userUid, user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getAdaterPhotos(String userUid, final User user) {
        Query keyRef = fbDatabase.getRefUserPhotos(userUid);
        Query dataRef = fbDatabase.getRefPhotos();

        Log.e(Utils.TAG, "dataRef " + dataRef.getRef() + " keyRef " + keyRef.getRef());

        adapter = new FirebaseIndexRecyclerAdapter<Photo, MainTimelineHolder>(Photo.class, R.layout.card_main_timeline, MainTimelineHolder.class, keyRef, dataRef) {

            @Override
            protected void populateViewHolder(MainTimelineHolder mainTimelineHolder, Photo photo, int position) {
                Log.e(Utils.TAG, "populateViewHolder");
                if (photo != null) {
                    Log.e(Utils.TAG, "photo != null ");
                    displayLayout(mainTimelineHolder, user, photo);
                    // getPhotos(mainTimelineHolder, user,  getRef(position).getKey());
                } else {
                    Log.e(Utils.TAG, "photo == null ");
                }
            }
        };

        recyclerView.setAdapter(adapter);
    }



    private void displayLayout(final MainTimelineHolder mainTimelineHolder, User user, Photo photo) {
        String username = user.getUsername();
        String photoProfl = user.getPhotoProfl();
        String url = photo.getUrl();
        Date date = photo.getDate();

        Log.e(Utils.TAG, "photoProfl " + photoProfl);

        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date);

        if (username != null && photoProfl != null && url != null && dateString != null) {
            mainTimelineHolder.setImageViewPhotoProfil(context, photoProfl);
            mainTimelineHolder.setTextViewUsername(username);
            mainTimelineHolder.setImageViewPhoto(getActivity(), url);

            mainTimelineHolder.setTextViewDate(dateString);
        }
    }

    private void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
