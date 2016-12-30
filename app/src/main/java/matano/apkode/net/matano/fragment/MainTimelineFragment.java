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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainTimelineFragment extends Fragment {
    private App app;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String incomeUserUid;
    private String currentUserUid;
    private Db db;

    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<String, MainTimelineHolder> adapter;


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
        app = (App) getApplicationContext();
        db = new Db(context);

        mAuth = FirebaseAuth.getInstance();
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

        View view = inflater.inflate(R.layout.fragment_main_timeline, container, false);

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

        Query query = app.getRefUserFollowings(currentUserUid);

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

    private void getUserPhotos(final MainTimelineHolder mainTimelineHolder, String userUid) {
        DatabaseReference reference = app.getRefUserPhotos(userUid);

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
        DatabaseReference reference = app.getRefPhoto(photoUid);
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

        Query query = app.getRefUser(currentUserUid);

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


    private void finishActivity() {
        getActivity().finish();
    }


}
