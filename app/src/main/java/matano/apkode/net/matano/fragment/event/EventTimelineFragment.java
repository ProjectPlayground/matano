package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.EventTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static android.app.Activity.RESULT_OK;


public class EventTimelineFragment extends Fragment {
    private static final String CURRENT_FRAGMENT = "Timeline";
    private static final int ARG_PHOTO_PICKER = 2;
    private Context context;
    private RecyclerView recyclerView;
    private List<Photo> aNews = new ArrayList<>();
    private String eventUid;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;
    private DatabaseReference refPhotos;
    private DatabaseReference refPhoto;
    private FirebaseStorage storage;
    private StorageReference refStoragePhoto;
    private StorageReference mRootStorageRef;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventTimelineHolder> adapter;
    private FloatingActionButton floatingButtonPhoto;
    private ProgressBar progressBar;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;


    public EventTimelineFragment() {
    }

    public static EventTimelineFragment newInstance(String eventUid) {
        EventTimelineFragment eventTimelineFragment = new EventTimelineFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.TAG_EVENT_UID, eventUid);

        eventTimelineFragment.setArguments(bundle);

        return eventTimelineFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        eventUid = getArguments().getString(Utils.TAG_EVENT_UID);

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

        storage = FirebaseStorage.getInstance();
        mRootStorageRef = storage.getReference();

        refStoragePhoto = mRootStorageRef.child("photos");

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
        super.onCreateView(inflater, container, savedInstanceState);

        ActionBar supportActionBar = ((EventActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(CURRENT_FRAGMENT);
        }

        View view = inflater.inflate(R.layout.fragment_event_timeline, container, false);

        floatingButtonPhoto = (FloatingActionButton) view.findViewById(R.id.floatingButtonPhoto);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

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

        adapter = new FirebaseRecyclerAdapter<String, EventTimelineHolder>(String.class, R.layout.card_event_timeline, EventTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(EventTimelineHolder eventTimelineHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventTimelineHolder, getRef(position).getKey());
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

    private void getPhoto(final EventTimelineHolder eventTimelineHolder, final String photoUid) {
        Query query = refPhoto.child(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null && photo.getDate() != null) {
                    getUser(eventTimelineHolder, photoUid, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getUser(final EventTimelineHolder eventTimelineHolder, final String photoUid, final Photo photo) {
        String userUid = photo.getUser();

        Query query = refUser.child(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null && user.getPhotoProfl() != null && user.getUsername() != null) {
                    displayLayout(eventTimelineHolder, photoUid, photo, user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(EventTimelineHolder eventTimelineHolder, final String photoUid, Photo photo, User user) {
        final String userUid = photo.getUser();
        String url = photo.getUrl();
        Date date = photo.getDate();

        eventTimelineHolder.setTextViewUsername(user.getUsername());
        eventTimelineHolder.setTextViewDate(new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date));
        eventTimelineHolder.setImageViewPhoto(context, url);
        eventTimelineHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        eventTimelineHolder.setImageViewPhotoProfil(context, user.getPhotoProfl());

        final ImageButton imageButtonLikePhoto = eventTimelineHolder.getImageButtonLikePhoto();

        Query query = refUser.child(userUid).child("likes");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    imageButtonLikePhoto.setTag("1");
                    imageButtonLikePhoto.setVisibility(View.VISIBLE);
                    imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (photoUid.equals(snap.getKey())) {
                            imageButtonLikePhoto.setTag(null);
                            imageButtonLikePhoto.setVisibility(View.VISIBLE);
                            imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_padding);
                        } else {
                            imageButtonLikePhoto.setTag("1");
                            imageButtonLikePhoto.setVisibility(View.VISIBLE);
                            imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageButtonLikePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPhotoLike(photoUid, (String) view.getTag());
            }
        });

    }

    private void addPhotoLike(String photoUid, String tag) {
        Map hashMap = new HashMap();
        hashMap.put("photo/" + photoUid + "/likes/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/likes/" + photoUid, tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });

    }

    private void uploadPhoto(Uri selectedImageUri) {

        final String uuid = UUID.randomUUID().toString();

        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "En cours...", Toast.LENGTH_SHORT).show();

        StorageReference photoRef = refStoragePhoto.child(uuid);
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
                        savePhoto(downloadUri, uuid);
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

    private void savePhoto(Uri downloadUri, String uuid) {

        Photo photo = new Photo(eventUid, currentUserUid, downloadUri.toString(), new Date(), "1", null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + eventUid + "/photos/" + uuid, "1");
        hashMap.put("photo/" + uuid, photo);
        hashMap.put("user/" + currentUserUid + "/photos/" + uuid, "1");

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
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
