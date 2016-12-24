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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Photo;

import static android.app.Activity.RESULT_OK;

public class EventPhotoFragment extends Fragment {
    private static final int ARG_PHOTO_PICKER = 2;
    private static String ARG_EVENT_UID = "eventUid";
    private Context context;
    private RecyclerView recyclerView;
    private List<Photo> photos = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refPhotos;
    private DatabaseReference refPhoto;
    private FirebaseStorage storage;
    private StorageReference refStoragePhoto;
    private StorageReference mRootStorageRef;
    private FloatingActionButton floatingButtonPhoto;
    private GridLayoutManager manager;
    private FirebaseRecyclerAdapter<String, EventPhotoHolder> adapter;

    public EventPhotoFragment() {
    }

    public EventPhotoFragment newInstance(Context ctx, String eventUid) {
        context = ctx;
        EventPhotoFragment eventPhotoFragment = new EventPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EVENT_UID, eventUid);
        eventPhotoFragment.setArguments(args);
        ARG_EVENT_UID = eventUid;
        return eventPhotoFragment;
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

        storage = FirebaseStorage.getInstance();
        mRootStorageRef = storage.getReference();

        refStoragePhoto = mRootStorageRef.child("photos");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_event_photo, container, false);
        ButterKnife.bind(this, view);

        floatingButtonPhoto = (FloatingActionButton) view.findViewById(R.id.floatingButtonPhoto);

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

        adapter = new FirebaseRecyclerAdapter<String, EventPhotoHolder>(String.class, R.layout.card_event_photo, EventPhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPhotoHolder eventPhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPhotoHolder, getRef(position).getKey());
                }
            }
        };

        floatingButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), ARG_PHOTO_PICKER);
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

    private void getPhoto(final EventPhotoHolder eventPhotoHolder, String s) {
        Query query = refPhoto.child(s);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(eventPhotoHolder, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(EventPhotoHolder eventPhotoHolder, Photo photo) {
        String url = photo.getUrl();

        if (url != null) {
            eventPhotoHolder.setImageViewPhoto(getContext(), photo.getUrl());

            eventPhotoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Photo", (Serializable) photos);
                    // TODO fix position
                    int position = 1;
                    bundle.putInt("position", position);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    PhotoDialog photoDialog = new PhotoDialog();

                    photoDialog.setArguments(bundle);

                    photoDialog.show(fragmentTransaction, "PhotoDialog");
                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ARG_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            uploadPhoto(selectedImageUri);

        }
    }

    private void uploadPhoto(Uri selectedImageUri) {

        final String uuid = UUID.randomUUID().toString();

        StorageReference photoRef = refStoragePhoto.child(uuid);
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        savePhoto(downloadUri, uuid);
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

    private void savePhoto(Uri downloadUri, String uuid) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserUid = currentUser.getUid();

        Photo photo = new Photo(ARG_EVENT_UID, currentUserUid, downloadUri.toString(), new Date(), "1", null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + ARG_EVENT_UID + "/photos/" + uuid, "1");
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
}