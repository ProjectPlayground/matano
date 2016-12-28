package matano.apkode.net.matano.fragment.event.privates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.EventActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.privates.EventPrivateTchatHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Tchat;
import matano.apkode.net.matano.model.User;

import static android.app.Activity.RESULT_OK;

public class EventPrivateTchatFragment extends Fragment {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final String CURRENT_FRAGMENT = "Tchat";
    private static final int ARG_PHOTO_PICKER = 2;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refTchat;
    private DatabaseReference refUser;
    private DatabaseReference refPhoto;
    private FirebaseStorage storage;
    private StorageReference refStoragePhoto;
    private StorageReference mRootStorageRef;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Tchat, EventPrivateTchatHolder> adapter;
    private ImageButton imageButtonPhotoPicker;
    private EditText editTextMessage;
    private ImageButton buttonButtonSendMessage;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;
    private String eventUid;

    public EventPrivateTchatFragment() {
    }

    public static EventPrivateTchatFragment newInstance(String eventUid) {
        EventPrivateTchatFragment eventPrivateTchatFragment = new EventPrivateTchatFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventPrivateTchatFragment.setArguments(bundle);

        return eventPrivateTchatFragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

        eventUid = getArguments().getString(Utils.ARG_EVENT_UID);
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
        refTchat = mRootRef.child("tchat");
        refUser = mRootRef.child("user");
        refPhoto = mRootRef.child("photo");

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event_private_tchat, container, false);

        ActionBar supportActionBar = ((EventActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(CURRENT_FRAGMENT);
        }

        imageButtonPhotoPicker = (ImageButton) view.findViewById(R.id.imageButtonPhotoPicker);
        buttonButtonSendMessage = (ImageButton) view.findViewById(R.id.buttonButtonSendMessage);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

        Query query = refTchat.child(eventUid);

        adapter = new FirebaseRecyclerAdapter<Tchat, EventPrivateTchatHolder>(Tchat.class, R.layout.card_event_private_tchat, EventPrivateTchatHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPrivateTchatHolder eventPrivateTchatHolder, Tchat tchat, int position) {
                if (tchat != null && tchat.getUser() != null) {
                    getUser(eventPrivateTchatHolder, tchat);
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
                manager.smoothScrollToPosition(recyclerView, null, adapter.getItemCount());
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

        recyclerView.setAdapter(adapter);

        imageButtonPhotoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), ARG_PHOTO_PICKER);
            }
        });


        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    buttonButtonSendMessage.setEnabled(true);
                } else {
                    buttonButtonSendMessage.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextMessage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});


        buttonButtonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                addTchatMessage(editTextMessage.getText().toString());
                editTextMessage.setText("");
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


    private void getUser(final EventPrivateTchatHolder eventPrivateTchatHolder, final Tchat tchat) {
        Query query = refUser.child(tchat.getUser());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);

                if (user != null && user.getUsername() != null && user.getPhotoProfl() != null) {

                    String messsage = tchat.getMesssage();
                    String photo = tchat.getPhoto();

                    if (messsage != null && photo == null) {
                        displayLayoutMessage(eventPrivateTchatHolder, tchat, user);
                    }

                    if (photo != null && messsage == null) {
                        getPhoto(eventPrivateTchatHolder, tchat, user, photo);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getPhoto(final EventPrivateTchatHolder eventPrivateTchatHolder, final Tchat tchat, final User user, String photoUid) {
        Query query = refPhoto.child(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null && photo.getUrl() != null) {
                    displayLayoutPhoto(eventPrivateTchatHolder, tchat, user, photo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void displayLayoutMessage(final EventPrivateTchatHolder eventPrivateTchatHolder, Tchat tchat, User user) {
        String username = user.getUsername();
        String photoProfil = user.getPhotoProfl();
        String messsage = tchat.getMesssage();
        String userUid = tchat.getUser();

        eventPrivateTchatHolder.setTextViewUsername(username);
        eventPrivateTchatHolder.setImageViewPhotoProfil(getContext(), photoProfil);
        eventPrivateTchatHolder.setTextViewMessage(messsage);

        if (userUid.equals(currentUserUid)) {
            eventPrivateTchatHolder.setIsSender(getContext(), true);
        } else {
            eventPrivateTchatHolder.setIsSender(getContext(), false);
        }

    }

    private void displayLayoutPhoto(EventPrivateTchatHolder eventPrivateTchatHolder, Tchat tchat, User user, Photo p) {
        String username = user.getUsername();
        String photoProfil = user.getPhotoProfl();
        String photo = p.getUrl();
        String userUid = tchat.getUser();

        eventPrivateTchatHolder.setTextViewUsername(username);
        eventPrivateTchatHolder.setImageViewPhotoProfil(getContext(), photoProfil);
        eventPrivateTchatHolder.setImageViewPhoto(getContext(), photo);

        if (userUid.equals(currentUserUid)) {
            eventPrivateTchatHolder.setIsSender(getContext(), true);
        } else {
            eventPrivateTchatHolder.setIsSender(getContext(), false);
        }

    }


    private void addTchatMessage(String message) {
        Tchat tchat = new Tchat(currentUserUid, new Date(), message, null, null);

        refTchat.child(eventUid).push().setValue(tchat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

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

    private void uploadPhoto(Uri selectedImageUri) {

        final String uuid = UUID.randomUUID().toString();

        StorageReference photoRef = refStoragePhoto.child(uuid);
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        addTchatPhoto(downloadUri, uuid);
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

    private void addTchatPhoto(Uri downloadUri, String uuid) {

        Photo photo = new Photo(eventUid, currentUserUid, downloadUri.toString(), new Date(), "1", null);
        Tchat tchat = new Tchat(currentUserUid, new Date(), null, uuid, null);

        Map hashMap = new HashMap();

        hashMap.put("event/" + eventUid + "/photos/" + uuid, "1");
        hashMap.put("photo/" + uuid, photo);
        hashMap.put("user/" + currentUserUid + "/photos/" + uuid, "1");
        hashMap.put("tchat/" + eventUid + "/" + uuid, tchat);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });

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
