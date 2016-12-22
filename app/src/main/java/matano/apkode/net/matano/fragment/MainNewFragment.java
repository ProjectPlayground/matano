package matano.apkode.net.matano.fragment;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.MainNewHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;


public class MainNewFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, MainNewHolder> adapter;

    public MainNewFragment() {
    }

    public MainNewFragment newInstance(Context context) {
        this.context = context;
        MainNewFragment mainNewFragment = new MainNewFragment();
        return mainNewFragment;
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
        View view = inflater.inflate(R.layout.fragment_main_new, container, false);

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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserUid = currentUser.getUid();

        Query query = refUser.child(currentUserUid).child("followings");

        /*refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String userUid = snap.getKey();
                    getUserPhotos(userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        adapter = new FirebaseRecyclerAdapter<String, MainNewHolder>(String.class, R.layout.card_main_new, MainNewHolder.class, query) {
            @Override
            protected void populateViewHolder(MainNewHolder mainNewHolder, String s, int position) {
                if (s != null) {
                    getUserPhotos(mainNewHolder, getRef(position).getKey());
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

    private void getUserPhotos(final MainNewHolder mainNewHolder, String userUid) {
        DatabaseReference reference = mRootRef.child("user").child(userUid).child("photos");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String photoUid = snap.getValue(String.class);
                    if (photoUid != null) {
                        getUserPhoto(mainNewHolder, snap.getKey());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUserPhoto(final MainNewHolder mainNewHolder, final String photoUid) {
        DatabaseReference reference = mRootRef.child("photo").child(photoUid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(mainNewHolder, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(final MainNewHolder mainNewHolder, Photo p) {
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
                    mainNewHolder.setImageViewPhotoProfil(getActivity(), photoProfil);
                    mainNewHolder.setTextViewUsername(username);
                    mainNewHolder.setImageViewPhoto(getActivity(), photo);

                    mainNewHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
