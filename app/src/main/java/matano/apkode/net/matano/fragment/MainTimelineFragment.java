package matano.apkode.net.matano.fragment;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.MainActivity;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;


public class MainTimelineFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, MainTimelineHolder> adapter;


    @Override
    public void onAttach(Context context) {
        Log.e(Utils.TAG, "MainTimelineFragment onAttach");
        super.onAttach(context);
        this.context = context;

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Timeline");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(Utils.TAG, "MainTimelineFragment onCreate");

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //TODO something
                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_timeline, container, false);
        Log.e(Utils.TAG, "MainTimelineFragment onCreateView");

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(Utils.TAG, "MainTimelineFragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);


        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserUid = currentUser.getUid();

        Query query = refUser.child(currentUserUid).child("followings");

        adapter = new FirebaseRecyclerAdapter<String, MainTimelineHolder>(String.class, R.layout.card_main_timeline, MainTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(MainTimelineHolder mainTimelineHolder, String s, int position) {
                if (s != null) {
                    getUserPhotos(mainTimelineHolder, getRef(position).getKey());
                }
            }
        };


        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(Utils.TAG, "MainTimelineFragment onActivityCreated");

    }

    @Override
    public void onStart() {
        Log.e(Utils.TAG, "MainTimelineFragment onStart");
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Utils.TAG, "MainTimelineFragment onResume");
    }

    @Override
    public void onPause() {
        Log.e(Utils.TAG, "MainTimelineFragment onPause");
        super.onPause();
        if (mAuth != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(Utils.TAG, "MainTimelineFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(Utils.TAG, "MainTimelineFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        Log.e(Utils.TAG, "MainTimelineFragment onDestroy");
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(Utils.TAG, "MainTimelineFragment onDetach");
    }

    private void getUserPhotos(final MainTimelineHolder mainTimelineHolder, String userUid) {
        DatabaseReference reference = mRootRef.child("user").child(userUid).child("photos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String photoUid = snap.getValue(String.class);
                    if (photoUid != null) {
                        getUserPhoto(mainTimelineHolder, snap.getKey());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUserPhoto(final MainTimelineHolder mainTimelineHolder, final String photoUid) {
        DatabaseReference reference = mRootRef.child("photo").child(photoUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(mainTimelineHolder, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(final MainTimelineHolder mainTimelineHolder, Photo p) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserUid = currentUser.getUid();

        final String photo = p.getUrl();
        final Date date = p.getDate();

        Query query = refUser.child(currentUserUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                String username = user.getUsername();
                String photoProfil = user.getPhotoProfl();

                if (username != null && photoProfil != null && photo != null && date != null) {
                    mainTimelineHolder.setImageViewPhotoProfil(getActivity(), photoProfil);
                    mainTimelineHolder.setTextViewUsername(username);
                    mainTimelineHolder.setImageViewPhoto(getActivity(), photo);

                    mainTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
