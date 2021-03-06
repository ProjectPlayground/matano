package matano.apkode.net.matano.fragment.profil;

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
import android.widget.ImageButton;

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

import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.profil.ProfilTimelineHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

public class ProfilTimelineFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeUserUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;
    private RecyclerView recyclerView;
    private List<Photo> photos = new ArrayList<>();
    private FirebaseRecyclerAdapter<String, ProfilTimelineHolder> adapter;

    public ProfilTimelineFragment() {
    }

    public static ProfilTimelineFragment newInstance(String userUid) {
        ProfilTimelineFragment profilTimelineFragment = new ProfilTimelineFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilTimelineFragment.setArguments(bundle);

        return profilTimelineFragment;
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
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_profil_timeline, container, false);


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

        Query query = fbDatabase.getRefUserPhotos(incomeUserUid);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<String, ProfilTimelineHolder>(String.class, R.layout.card_profil_timeline, ProfilTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(ProfilTimelineHolder profilTimelineHolder, String s, int position) {
                if (s != null) {
                    getPhoto(profilTimelineHolder, getRef(position).getKey(), position);
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


    private void getPhoto(final ProfilTimelineHolder profilTimelineHolder, final String photoUid, final int position) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getDate() != null && photo.getUser() != null) {
                    getUser(profilTimelineHolder, photoUid, photo, position);

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

    private void getUser(final ProfilTimelineHolder profilTimelineHolder, final String photoUid, final Photo photo, final int position) {
        String userUid = photo.getUser();

        Query query = fbDatabase.getRefUser(userUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // getEvent(profilTimelineHolder, photoUid, photo, user, position);
                // TODO traiter l'erreur
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    getEvent(profilTimelineHolder, photoUid, photo, user, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getEvent(final ProfilTimelineHolder profilTimelineHolder, final String photoUid, final Photo photo, final User user, final int position) {
        final String eventUid = photo.getEvent();

        Query query = fbDatabase.getRefEvent(eventUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    displayLayout(profilTimelineHolder, photoUid, eventUid, photo, user, event, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(ProfilTimelineHolder profilTimelineHolder, final String photoUid, final String eventUid, Photo photo, User user, Event event, final int position) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();
        String title = event.getTitle();

        profilTimelineHolder.setTextViewTitle(title);
        profilTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        profilTimelineHolder.setImageViewPhoto(context, url);
        profilTimelineHolder.getImageViewPhoto().setTag(position);
        profilTimelineHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
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


        profilTimelineHolder.getLinearLayoutTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEventActivity(eventUid);
            }
        });

        final ImageButton imageButtonLikePhoto = profilTimelineHolder.getImageButtonLikePhoto();

        Query query = fbDatabase.getRefUserLikes(userUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    imageButtonLikePhoto.setTag("0");
                    imageButtonLikePhoto.setVisibility(View.VISIBLE);
                    imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (photoUid.equals(snap.getKey())) {
                            imageButtonLikePhoto.setTag(null);
                            imageButtonLikePhoto.setVisibility(View.VISIBLE);
                            imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_padding);
                        } else {
                            imageButtonLikePhoto.setTag("0");
                            imageButtonLikePhoto.setVisibility(View.VISIBLE);
                            imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle error
            }
        });

        imageButtonLikePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.setPhotoLike(photoUid, (String) view.getTag(), currentUserUid);
            }
        });


    }

    private void goEventActivity(String eventUid) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, eventUid);
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
