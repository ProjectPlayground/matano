package matano.apkode.net.matano.fragment.event.privates;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.holder.event.privates.EventPrivatePhotoHolder;
import matano.apkode.net.matano.model.Photo;

public class EventPrivatePhotoFragment extends Fragment {
    private static String ARG_EVENT_UID = "eventUid";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refPhotos;
    private DatabaseReference refPhoto;
    private RecyclerView recyclerView;
    private GridLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder> adapter;

    public EventPrivatePhotoFragment() {
    }

    public EventPrivatePhotoFragment newInstance(Context ctx, String eventUid) {
        context = ctx;
        EventPrivatePhotoFragment eventPrivatePhotoFragment = new EventPrivatePhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EVENT_UID, eventUid);
        eventPrivatePhotoFragment.setArguments(bundle);
        ARG_EVENT_UID = eventUid;
        return eventPrivatePhotoFragment;
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
        refEvent = mRootRef.child("event").child(ARG_EVENT_UID);
        refPhotos = refEvent.child("photos");
        refPhoto = mRootRef.child("photo");

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
        View view = inflater.inflate(R.layout.fragment_event_private_photo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new GridLayoutManager(getContext(), 2);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Query query = refPhotos;

        adapter = new FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder>(String.class, R.layout.card_event_private_photo, EventPrivatePhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPrivatePhotoHolder eventPrivatePhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPrivatePhotoHolder, getRef(position).getKey());
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

    private void getPhoto(final EventPrivatePhotoHolder eventPrivatePhotoHolder, String s) {
        Query query = refPhoto.child(s);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(eventPrivatePhotoHolder, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(EventPrivatePhotoHolder eventPrivatePhotoHolder, Photo photo) {
        String url = photo.getUrl();

        if (url != null) {
            eventPrivatePhotoHolder.setImageViewPhoto(getContext(), photo.getUrl());

            eventPrivatePhotoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Bundle bundle = new Bundle();
                    bundle.putSerializable("Photo", (Serializable) photos);
                    // TODO fix position
                    int position = 1;
                    bundle.putInt("position", position);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    PhotoDialog photoDialog = new PhotoDialog();

                    photoDialog.setArguments(bundle);

                    photoDialog.show(fragmentTransaction, "PhotoDialog");*/
                }
            });

        }

    }

}
