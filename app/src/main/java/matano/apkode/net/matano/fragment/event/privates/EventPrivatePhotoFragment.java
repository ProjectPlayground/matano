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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.event.privates.EventPrivatePhotoHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

public class EventPrivatePhotoFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeEventUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder> adapter;
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

        View view = inflater.inflate(R.layout.fragment_event_private_photo, container, false);

        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = fbDatabase.getRefEventTchats(incomeEventUid);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<String, EventPrivatePhotoHolder>(String.class, R.layout.card_event_private_photo, EventPrivatePhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPrivatePhotoHolder eventPrivatePhotoHolder, String s, int position) {
                if (s != null) {
                    //  getTchat(eventPrivatePhotoHolder, getRef(position).getKey(), position);
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
/*

    private void getTchat(final EventPrivatePhotoHolder eventPrivatePhotoHolder, String tchatUid, final int position) {
        Query query = fbDatabase.getRefTchat(tchatUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tchat tchat = dataSnapshot.getValue(Tchat.class);
                if (tchat != null && tchat.getPhoto() != null) {
                    getPhoto(eventPrivatePhotoHolder, dataSnapshot.getKey(), position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

    private void getPhoto(final EventPrivatePhotoHolder eventPrivatePhotoHolder, final String photoUid, final int position) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

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

        Query query = fbDatabase.getRefUser(userUid);
        query.keepSynced(true);

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

                PhotoDialogFragment photoDialogFragment = new PhotoDialogFragment();

                photoDialogFragment.setArguments(bundle);

                photoDialogFragment.show(fragmentTransaction, Utils.TAG_PHOTO_DIALOG);

            }
        });

        eventPrivatePhotoHolder.setImageViewPhotoProfil(context, user.getPhotoProfl());

        eventPrivatePhotoHolder.getLinearLayoutUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfilActivity(userUid);
            }
        });

        eventPrivatePhotoHolder.getCardViewContainer().setVisibility(View.VISIBLE);
        eventPrivatePhotoHolder.getLinearLayoutContainer().setVisibility(View.VISIBLE);


    }

    private void goProfilActivity(String userUid) {
        Intent intent = new Intent(context, ProfilActivity.class);
        intent.putExtra(Utils.ARG_USER_UID, userUid);
        startActivity(intent);
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
