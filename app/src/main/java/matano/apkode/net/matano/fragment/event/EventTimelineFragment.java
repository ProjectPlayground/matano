package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.FbStorage;
import matano.apkode.net.matano.config.Share;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.event.EventTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static android.app.Activity.RESULT_OK;


public class EventTimelineFragment extends Fragment {
    private static final int ARG_PHOTO_PICKER = 2;
    private FbDatabase fbDatabase;
    private FbStorage fbStorage;
    private String incomeEventUid;
    private Share share;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Db db;
    private Context context;
    private RecyclerView recyclerView;
    private List<Photo> photos = new ArrayList<>();
    private FirebaseRecyclerAdapter<String, EventTimelineHolder> adapter;
    private FloatingActionButton floatingButtonPhoto;
    private ProgressBar progressBar;

    public EventTimelineFragment() {
    }

    public static EventTimelineFragment newInstance(String eventUid) {
        EventTimelineFragment eventTimelineFragment = new EventTimelineFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventTimelineFragment.setArguments(bundle);

        return eventTimelineFragment;
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
        fbStorage = new FbStorage();
        share = new Share(context, getActivity());

        Log.e(Utils.TAG, "onCreate timeline");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_event_timeline, container, false);

        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        Log.e(Utils.TAG, "onCreateView timeline");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        floatingButtonPhoto = (FloatingActionButton) view.findViewById(R.id.floatingButtonPhoto);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(Utils.TAG, "onViewCreated timeline");
        createAuthStateListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(Utils.TAG, "onStart timeline");
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(Utils.TAG, "onResume timeline");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(Utils.TAG, "onPause timeline");
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
            adapter.notifyDataSetChanged();
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
        Query query = fbDatabase.getRefEventPhotos(incomeEventUid);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<String, EventTimelineHolder>(String.class, R.layout.card_event_timeline, EventTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(EventTimelineHolder eventTimelineHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventTimelineHolder, getRef(position).getKey(), position);
                }
            }
        };

        recyclerView.setAdapter(adapter);

        floatingButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), ARG_PHOTO_PICKER);
            }
        });
    }

    private void getPhoto(final EventTimelineHolder eventTimelineHolder, final String photoUid, final int position) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getDate() != null && photo.getUser() != null) {
                    getUser(eventTimelineHolder, photoUid, photo, position);

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

    private void getUser(final EventTimelineHolder eventTimelineHolder, final String photoUid, final Photo photo, final int position) {
        String userUid = photo.getUser();

        Query query = fbDatabase.getRefUser(userUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    getPhotoLikesNumber(eventTimelineHolder, photoUid, photo, user, position);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPhotoLikesNumber(final EventTimelineHolder eventTimelineHolder, final String photoUid, final Photo photo, final User user, final int position) {
        Query query = fbDatabase.getRefPhotoLikes(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayLayout(eventTimelineHolder, photoUid, photo, user, position, dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void displayLayout(EventTimelineHolder eventTimelineHolder, final String photoUid, final Photo photo, User user, final int position, final long likeCount) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();

        eventTimelineHolder.setTextViewUsername(user.getUsername());
        eventTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        eventTimelineHolder.setImageViewPhoto(context, url);
        eventTimelineHolder.getImageViewPhoto().setTag(position);
        eventTimelineHolder.getImageViewPhoto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Utils.ARG_PHOTO_DIALOG, (Serializable) photos);
                bundle.putInt(Utils.ARG_PHOTO_DIALOG_POSITION, position);
                bundle.putString(Utils.ARG_USER_UID, userUid);
                bundle.putString(Utils.ARG_PHOTO_UID, photoUid);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                PhotoDialogFragment photoDialogFragment = new PhotoDialogFragment();

                photoDialogFragment.setArguments(bundle);

                photoDialogFragment.show(fragmentTransaction, Utils.TAG_PHOTO_DIALOG);

            }
        });

        eventTimelineHolder.setImageViewPhotoProfil(context, user.getPhotoProfl());

        eventTimelineHolder.getLinearLayoutUser().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goProfilActivity(userUid);
            }
        });


        ImageButton imageButtonLikePhoto = eventTimelineHolder.getImageButtonLikePhoto();
        ImageButton imageButtonSharePhoto = eventTimelineHolder.getImageButtonSharePhoto();
        eventTimelineHolder.setTextViewCountLike(likeCount + "");

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

    private void uploadPhoto(Uri selectedImageUri) {

        final String uuid = UUID.randomUUID().toString();

        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "En cours...", Toast.LENGTH_SHORT).show();

        StorageReference photoRef = fbStorage.getRefRoot().child(uuid);
        photoRef.putFile(selectedImageUri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        long totalByteCount = taskSnapshot.getTotalByteCount();
                        long bytesTransferred = taskSnapshot.getBytesTransferred();

                        Log.e(Utils.TAG, "bytesTransferred : " + bytesTransferred + " , totalByteCount : " + totalByteCount);

                        progressBar.setMax((int) totalByteCount);
                        progressBar.setProgress((int) bytesTransferred);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        db.setPhotoTimeline(downloadUri, uuid, incomeEventUid, currentUserUid);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARG_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            uploadPhoto(selectedImageUri);

        }
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
