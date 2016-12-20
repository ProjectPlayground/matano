package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.event.EventNewAdapter;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.EventNewHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static android.app.Activity.RESULT_OK;


public class EventNewFragment extends Fragment {
    private static final String ARG_PHOTO_KEY = null;
    private static final int RC_PHOTO_PICKER = 2;
    private Context context;
    private RecyclerView recyclerView;
    private EventNewAdapter eventNewAdapter;
    private List<Photo> aNews = new ArrayList<>();
    private String eventKey;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refUser;
    private DatabaseReference refPhoto;
    private FirebaseStorage storage;
    private StorageReference refStoragePhotos;
    private StorageReference mRootStorageRef;
    private LinearLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventNewHolder> adapter;
    private FloatingActionButton floatingButtonPhoto;

    public EventNewFragment() {
    }

    public EventNewFragment newInstance(Context ctx, String key) {
        context = ctx;
        eventKey = key;
        EventNewFragment eventNewFragment = new EventNewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_KEY, key);
        eventNewFragment.setArguments(args);
        return eventNewFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refPhoto = mRootRef.child("photo");
        refUser = mRootRef.child("user");

        storage = FirebaseStorage.getInstance();
        mRootStorageRef = storage.getReference();

        refStoragePhotos = mRootStorageRef.child("photos");

        String key = getArguments().getString(ARG_PHOTO_KEY);

        if (key == null) {
            // TODO something
        } else {
            refEvent = mRootRef.child("event").child(key);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_new, container, false);
        ButterKnife.bind(this, view);

        floatingButtonPhoto = (FloatingActionButton) view.findViewById(R.id.floatingButtonPhoto);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);


        Query query = refEvent.child("photos");


        adapter = new FirebaseRecyclerAdapter<String, EventNewHolder>(String.class, R.layout.card_event_new, EventNewHolder.class, query) {
            @Override
            protected void populateViewHolder(final EventNewHolder eventNewHolder, String s, int position) {
                if (s != null) {
                    final String ref = getRef(position).getKey();
                    refPhoto.child(ref).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Photo photo = dataSnapshot.getValue(Photo.class);

                            if (photo != null && photo.getUrl() != null && photo.getUser() != null) {
                                eventNewHolder.setImageViewPhoto(getContext(), photo.getUrl());

                                refUser.child(photo.getUser()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User user = dataSnapshot.getValue(User.class);

                                        if (user != null) {
                                            eventNewHolder.setImageViewPhotoProfil(getContext(), user.getPhotoProfl());
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                final ImageButton imageButtonLikePhoto = eventNewHolder.getImageButtonLikePhoto();

                                DatabaseReference reference = mRootRef.child("photo").child(ref).child("likes");

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.getChildrenCount() == 0) {
                                            imageButtonLikePhoto.setTag("1");
                                            imageButtonLikePhoto.setVisibility(View.VISIBLE);
                                            imageButtonLikePhoto.setImageResource(R.mipmap.ic_action_action_favorite_outline_padding);
                                        } else {
                                            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(snap.getKey())) {
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

                                if (imageButtonLikePhoto != null) {

                                    imageButtonLikePhoto.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            addPhotoLike(ref, (String) view.getTag());
                                        }
                                    });

                                }


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
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
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            uploadPhoto(selectedImageUri);

        }
    }

    private void uploadPhoto(Uri selectedImageUri) {

        final String uuid = UUID.randomUUID().toString();

        StorageReference photoRef = refStoragePhotos.child(uuid);
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        Log.e(Utils.TAG, "name " + taskSnapshot.getMetadata().getName());
                        savePhoto(FirebaseAuth.getInstance().getCurrentUser(), downloadUri, uuid);
                        Log.e(Utils.TAG, "uploadPhoto:onSuccess:" + taskSnapshot.getDownloadUrl());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(Utils.TAG, "uploadPhoto:onError", e);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });
    }

    private void savePhoto(FirebaseUser currentUser, Uri downloadUri, String uuid) {

        Photo photo = new Photo(getArguments().getString(ARG_PHOTO_KEY), currentUser.getUid(), downloadUri.toString(), new Date(), "1", null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + getArguments().getString(ARG_PHOTO_KEY) + "/photos/" + uuid, "1");
        hashMap.put("photo/" + uuid, photo);
        hashMap.put("user/" + currentUser.getUid() + "/photos/" + uuid, "1");

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });

    }

    private void addPhotoLike(String keyPhoto, String tag) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserUid = currentUser.getUid();

        Map hashMap = new HashMap();
        hashMap.put("photo/" + keyPhoto + "/likes/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/likes/" + keyPhoto, tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });

    }

}
