package matano.apkode.net.matano.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.user.UserTimelineHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserTimelineFragment extends Fragment {
    private App app;
    private String incomeUserUid;
    private Db db;

    private Context context;
    private RecyclerView recyclerView;
    private List<Photo> photos = new ArrayList<>();
    private FirebaseRecyclerAdapter<String, UserTimelineHolder> adapter;

    public UserTimelineFragment() {
    }

    public static UserTimelineFragment newInstance(String userUid) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        userTimelineFragment.setArguments(bundle);

        return userTimelineFragment;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_user_timeline, container, false);


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

        Query query = app.getRefUserPhotos(incomeUserUid);

        adapter = new FirebaseRecyclerAdapter<String, UserTimelineHolder>(String.class, R.layout.card_user_timeline, UserTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(UserTimelineHolder userTimelineHolder, String s, int position) {
                if (s != null) {
                    getPhoto(userTimelineHolder, getRef(position).getKey(), position);
                }
            }
        };

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
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

    private void getPhoto(final UserTimelineHolder userTimelineHolder, final String photoUid, final int position) {
        Query query = app.getRefPhoto(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getDate() != null && photo.getUser() != null) {
                    getUser(userTimelineHolder, photoUid, photo, position);

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

    private void getUser(final UserTimelineHolder userTimelineHolder, final String photoUid, final Photo photo, final int position) {
        String userUid = photo.getUser();

        Query query = app.getRefUser(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                // getEvent(userTimelineHolder, photoUid, photo, user, position);
                // TODO traiter l'erreur
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    getEvent(userTimelineHolder, photoUid, photo, user, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getEvent(final UserTimelineHolder userTimelineHolder, final String photoUid, final Photo photo, final User user, final int position) {
        final String eventUid = photo.getEvent();

        Query query = app.getRefEvent(eventUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    displayLayout(userTimelineHolder, photoUid, eventUid, photo, user, event, position);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void displayLayout(UserTimelineHolder userTimelineHolder, final String photoUid, final String eventUid, Photo photo, User user, Event event, final int position) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();
        String title = event.getTitle();

        userTimelineHolder.setTextViewTitle(title);
        userTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        userTimelineHolder.setImageViewPhoto(context, url);
        userTimelineHolder.getImageViewPhoto().setTag(position);
        userTimelineHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
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


        userTimelineHolder.getLinearLayoutTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEventActivity(eventUid);
            }
        });

        final ImageButton imageButtonLikePhoto = userTimelineHolder.getImageButtonLikePhoto();

        Query query = app.getRefUserLikes(userUid);

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
                db.setPhotoLike(photoUid, (String) view.getTag(), app.getCurrentUserUid());
            }
        });


    }


    private void goEventActivity(String eventUid) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(Utils.ARG_EVENT_UID, eventUid);
        startActivity(intent);
    }


    private void finishActivity() {
        getActivity().finish();
    }


}
