package matano.apkode.net.matano;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Share;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.profil.ProfilTimelineHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

public class UserActivity extends AppCompatActivity {
    private FbDatabase fbDatabase;
    private String incomeUserUid;
    private Db db;
    private Share share;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private List<Photo> photos = new ArrayList<>();
    private RecyclerView recyclerView;

    private FirebaseRecyclerAdapter<String, ProfilTimelineHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        incomeUserUid = getIntent().getStringExtra(Utils.ARG_USER_UID);

        if (incomeUserUid == null) {
            finish();
        }

        db = new Db(this);
        fbDatabase = new FbDatabase();
        share = new Share(this, this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);


        createAuthStateListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
                    createView();
                }
            }
        };
    }

    private void createView() {
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

    private void getPhoto(final ProfilTimelineHolder profilTimelineHolder, final String photoUid, final int position) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getDate() != null && photo.getUser() != null) {
                    getUser(profilTimelineHolder, photoUid, photo, position);
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
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    getSupportActionBar().setTitle(user.getUsername());
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
                    getPhotoLikesNumber(profilTimelineHolder, photoUid, photo, user, eventUid, event, position);
                    //  displayLayout(profilTimelineHolder, photoUid, eventUid, photo, user, event, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPhotoLikesNumber(final ProfilTimelineHolder profilTimelineHolder, final String photoUid, final Photo photo, final User user, final String eventUid, final Event event, final int position) {
        Query query = fbDatabase.getRefPhotoLikes(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayLayout(profilTimelineHolder, photoUid, eventUid, photo, user, event, position, dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(ProfilTimelineHolder profilTimelineHolder, final String photoUid, final String eventUid, final Photo photo, User user, Event event, final int position, final long likeCount) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();
        String title = event.getTitle();

        photos.add(photo);

        profilTimelineHolder.setTextViewTitle(title);
        profilTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        profilTimelineHolder.setImageViewPhoto(this, url);
        profilTimelineHolder.getImageViewPhoto().setTag(position);
        profilTimelineHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Utils.ARG_PHOTO_DIALOG, (Serializable) photos);
                bundle.putInt(Utils.ARG_PHOTO_DIALOG_POSITION, 0);
                bundle.putString(Utils.ARG_USER_UID, userUid);
                bundle.putString(Utils.ARG_PHOTO_UID, photoUid);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

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

        ImageButton imageButtonLikePhoto = profilTimelineHolder.getImageButtonLikePhoto();
        ImageButton imageButtonSharePhoto = profilTimelineHolder.getImageButtonSharePhoto();
        profilTimelineHolder.setTextViewCountLike(likeCount + "");

        getPhotoLike(imageButtonLikePhoto, photoUid);

        imageButtonLikePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.setPhotoLike(photoUid, (String) view.getTag(), currentUserUid);
            }
        });

        imageButtonSharePhoto.setVisibility(View.VISIBLE);
        imageButtonSharePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share.shareLink("title", "description", "hashatag", photo.getUrl());
            }
        });


    }


    private void getPhotoLike(final ImageButton imageButtonLikePhoto, final String photoUid) {
        Query query = fbDatabase.getRefPhotoLikes(photoUid).child(currentUserUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tag = dataSnapshot.getValue(String.class);
                if (tag == null) {
                    imageButtonLikePhoto.setTag("0");
                    imageButtonLikePhoto.setVisibility(View.VISIBLE);
                    imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                } else {
                    imageButtonLikePhoto.setTag(null);
                    imageButtonLikePhoto.setVisibility(View.VISIBLE);
                    imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_padding);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goEventActivity(String eventUid) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, eventUid);
        startActivity(intent);
    }


}
