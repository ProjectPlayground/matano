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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.adapter.event.EventPhotoAdapter;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Photo;

import static android.app.Activity.RESULT_OK;

public class EventPhotoFragment extends Fragment {
    private static final String ARG_PHOTO_KEY = null;
    private static final int RC_PHOTO_PICKER = 2;
    private Context context;
    private RecyclerView recyclerView;
    private EventPhotoAdapter eventPhotoAdapter;
    private List<Photo> photos = new ArrayList<>();
    private String eventKey;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private DatabaseReference refPhoto;
    private FirebaseStorage storage;
    private StorageReference refStoragePhotos;
    private StorageReference mRootStorageRef;
    private FloatingActionButton floatingButtonPhoto;
    private GridLayoutManager manager;
    private FirebaseRecyclerAdapter<Photo, EventPhotoHolder> adapter;
    private SimpleDateFormat simpleDateFormat;

    public EventPhotoFragment() {
    }

    public EventPhotoFragment newInstance(Context ctx, String key) {
        context = ctx;
        eventKey = key;
        EventPhotoFragment eventPhotoFragment = new EventPhotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_KEY, key);
        eventPhotoFragment.setArguments(args);
        return eventPhotoFragment;
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

        String key = getArguments().getString(ARG_PHOTO_KEY);

        if (key == null) {
            // TODO something
        } else {
            refEvent = mRootRef.child("event").child(key);
        }

        storage = FirebaseStorage.getInstance();
        mRootStorageRef = storage.getReference();

        refStoragePhotos = mRootStorageRef.child("photos");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

        Query query = refPhoto;


        adapter = new FirebaseRecyclerAdapter<Photo, EventPhotoHolder>(Photo.class, R.layout.card_event_photo, EventPhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPhotoHolder eventPhotoHolder, Photo photo, final int position) {
                if (photo != null) {
                    if (photo.getEvent().equals(getArguments().getString(ARG_PHOTO_KEY))) {

                        eventPhotoHolder.setImageViewPhoto(getContext(), photo.getUrl());

                        eventPhotoHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Photo", (Serializable) photos);
                                bundle.putInt("position", position);

                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                                PhotoDialog photoDialog = new PhotoDialog();

                                photoDialog.setArguments(bundle);

                                photoDialog.show(fragmentTransaction, "PhotoDialog");
                            }
                        });
                    }
                } else {
                    Log.e(Utils.TAG, "photo == null");
                }
            }
        };

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        });

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
                        savePhoto(FirebaseAuth.getInstance().getCurrentUser(), downloadUri, uuid);
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
}