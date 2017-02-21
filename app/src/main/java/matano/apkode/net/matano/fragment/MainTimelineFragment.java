package matano.apkode.net.matano.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.MainTimelineAdapter;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Share;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Timeline;
import matano.apkode.net.matano.model.User;


public class MainTimelineFragment extends Fragment {
    private FbDatabase fbDatabase;
    private Db db;
    private Share share;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser;
    private String currentUserUid;
    private List<Photo> photos = new ArrayList<>();
    private List<Timeline> timelines = new ArrayList<>();
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

        db = new Db(context);
        fbDatabase = new FbDatabase();
        share = new Share(context, getActivity());
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

        createAuthStateListener();

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
        Query query = fbDatabase.getRefUserFollowings(currentUserUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userUid = snapshot.getKey();
                    getUser(userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* query.addChildEventListener(new ChildEventListener() {
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
        });*/

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
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    getUserPhoto(userUid, user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUserPhoto(final String userUid, final User user) {
        Query query = fbDatabase.getRefUserPhotos(userUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    getPhoto(user, userUid, snapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       /* query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String photoUid = dataSnapshot.getValue(String.class);
                if(photoUid != null) {
                    getPhoto(user, userUid, dataSnapshot.getKey());
                }
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
*/

/*
        Query keyRef = fbDatabase.getRefUserPhotos(userUid);
        Query dataRef = fbDatabase.getRefPhotos();

        adapter = new FirebaseIndexRecyclerAdapter<Photo, MainTimelineHolder>(Photo.class, R.layout.card_main_timeline, MainTimelineHolder.class, keyRef, dataRef) {

            @Override
            protected void populateViewHolder(MainTimelineHolder mainTimelineHolder, Photo photo, int position) {
                if (photo != null && photo.getEvent() != null ) {
                    getEvent(mainTimelineHolder, user, photo, getRef(position).getKey());
                }
            }
        };

        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);*/
    }

    private void getPhoto(final User user, final String userUid, final String photoUid) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                if (photo != null) {
                    getEvent(user, userUid, photo, photoUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getEvent(final User user, final String userUid, final Photo photo, final String photoUid) {
        Query query = fbDatabase.getRefEvent(photo.getEvent());
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    getPhotoLikesNumber(user, userUid, photo, photoUid, event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPhotoLikesNumber(final User user, final String userUid, final Photo photo, final String photoUid, final Event event) {
        Query query = fbDatabase.getRefPhotoLikes(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayLayout(user, userUid, photo, photoUid, event, dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void displayLayout(User user, String userUid, final Photo photo, final String photoUid, Event event, final long likeCount) {
        // photos.add(photo);

        String username = user.getUsername();
        String photoProfl = user.getPhotoProfl();
        String url = photo.getUrl();
        String title = event.getTitle();
        Date date = photo.getDate();

        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date);

        timelines.add(new Timeline(username, photoProfl, url, title, date, dateString, context, getActivity(), photo.getEvent(), photo.getUser(), photoUid, currentUserUid));

        recyclerView.setAdapter(new MainTimelineAdapter(timelines));

    /*    if (username != null && photoProfl != null && url != null && dateString != null && title != null) {
            mainTimelineHolder.setImageViewPhotoProfil(context, photoProfl);
            mainTimelineHolder.setTextViewUsername(username);
            mainTimelineHolder.setTextViewTitle(title);
            mainTimelineHolder.setTextViewDate(dateString);

            mainTimelineHolder.setImageViewPhoto(getActivity(), url);

            mainTimelineHolder.setContainerVisibility();
            
            photos.add(photo);

            mainTimelineHolder.setTextViewTitle(title);
            mainTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
            mainTimelineHolder.setImageViewPhoto(context, url);
            mainTimelineHolder.getImageViewPhoto().setTag(0);
            mainTimelineHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Utils.ARG_PHOTO_DIALOG, (Serializable) photos);
                    bundle.putInt(Utils.ARG_PHOTO_DIALOG_POSITION, 0);
                    bundle.putString(Utils.ARG_USER_UID, userUid);
                    bundle.putString(Utils.ARG_PHOTO_UID, photoUid);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    PhotoDialogFragment photoDialogFragment = new PhotoDialogFragment();

                    photoDialogFragment.setArguments(bundle);

                    photoDialogFragment.show(fragmentTransaction, Utils.TAG_PHOTO_DIALOG);

                }
            });


            mainTimelineHolder.getLinearLayoutTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goEventActivity(eventUid);
                }
            });

            ImageButton imageButtonLikePhoto = mainTimelineHolder.getImageButtonLikePhoto();
            ImageButton imageButtonSharePhoto = mainTimelineHolder.getImageButtonSharePhoto();
            mainTimelineHolder.setTextViewCountLike(likeCount + "");

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
            

        }*/
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
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goEventActivity(String eventUid) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, eventUid);
        startActivity(intent);
    }

}
