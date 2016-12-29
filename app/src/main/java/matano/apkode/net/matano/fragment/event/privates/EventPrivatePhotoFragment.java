package matano.apkode.net.matano.fragment.event.privates;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.holder.event.privates.EventPrivatePhotoHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

public class EventPrivatePhotoFragment extends Fragment {
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refPhotos;
    private DatabaseReference refPhoto;
    private DatabaseReference refUser;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder> adapter;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;
    private String eventUid;
    private List<Photo> photos = new ArrayList<>();

    public EventPrivatePhotoFragment() {
    }

    public static EventPrivatePhotoFragment newInstance(String eventUid) {
        EventPrivatePhotoFragment eventPrivatePhotoFragment = new EventPrivatePhotoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventPrivatePhotoFragment.setArguments(bundle);

        return eventPrivatePhotoFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        eventUid = getArguments().getString(Utils.ARG_EVENT_UID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localStorage = new LocalStorage(context);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        if (eventUid == null) {
            finishActivity();
        }

        if (!localStorage.isContryStored() || currentUserContry == null) {
            goContryActivity();
        }

        if (!localStorage.isCityStored() || currentUserCity == null) {
            goCityActivity();
        }


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refEvent = mRootRef.child("event").child(eventUid);
        refPhotos = refEvent.child("photos");
        refPhoto = mRootRef.child("photo");
        refUser = mRootRef.child("user");

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_event_private_photo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refPhotos;

        adapter = new FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder>(String.class, R.layout.card_event_private_photo, EventPrivatePhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPrivatePhotoHolder eventPrivatePhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPrivatePhotoHolder, getRef(position).getKey(), position);
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

    private void getPhoto(final EventPrivatePhotoHolder eventPrivatePhotoHolder, final String photoUid, final int position) {
        Query query = refPhoto.child(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getUser() != null) {
                    getUser(eventPrivatePhotoHolder, photoUid, photo, position);

                    if (!photos.contains(photo)) {
                        photos.add(photo);
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getUser(final EventPrivatePhotoHolder eventPrivatePhotoHolder, final String photoUid, final Photo photo, final int position) {
        String userUid = photo.getUser();

        Query query = refUser.child(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    displayLayout(eventPrivatePhotoHolder, photoUid, photo, user, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(EventPrivatePhotoHolder eventPrivatePhotoHolder, final String photoUid, Photo photo, User user, final int position) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();

        eventPrivatePhotoHolder.setTextViewUsername(user.getUsername());
        eventPrivatePhotoHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        eventPrivatePhotoHolder.setImageViewPhoto(context, url);
        eventPrivatePhotoHolder.getImageViewPhoto().setTag(position);
        eventPrivatePhotoHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Utils.ARG_PHOTO_DIALOG, (Serializable) photos);
                bundle.putInt(Utils.ARG_PHOTO_DIALOG_POSITION, position);
                bundle.putString(Utils.ARG_USER_UID, userUid);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                PhotoDialog photoDialog = new PhotoDialog();

                photoDialog.setArguments(bundle);

                photoDialog.show(fragmentTransaction, Utils.TAG_PHOTO_DIALOG);

            }
        });

        eventPrivatePhotoHolder.setImageViewPhotoProfil(context, user.getPhotoProfl());

        eventPrivatePhotoHolder.getLinearLayoutUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfilActivity(userUid);
            }
        });


    }

    private void goProfilActivity(String userUid) {
        Intent intent = new Intent(context, ProfilActivity.class);
        intent.putExtra(Utils.ARG_USER_UID, userUid);
        startActivity(intent);
    }

    private void goContryActivity() {
        Intent intent = new Intent(context, ContryActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void goCityActivity() {
        Intent intent = new Intent(context, CityActivity.class);
        startActivity(intent);
        finishActivity();
    }

    private void finishActivity() {
        getActivity().finish();
    }

}
