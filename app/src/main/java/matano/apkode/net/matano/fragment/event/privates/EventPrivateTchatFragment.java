package matano.apkode.net.matano.fragment.event.privates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.UserActivity;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.event.privates.EventPrivateTchatHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.Tchat;
import matano.apkode.net.matano.model.User;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class EventPrivateTchatFragment extends Fragment {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private static final int ARG_PHOTO_PICKER = 2;
    private App app;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private String incomeEventUid;
    private String currentUserUid;
    private Db db;
    private Context context;
    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<String, EventPrivateTchatHolder> adapter;
    private ImageButton imageButtonPhotoPicker;
    private EditText editTextMessage;
    private ImageButton buttonButtonSendMessage;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplicationContext();
        db = new Db(context);

        mAuth = FirebaseAuth.getInstance();
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

        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);


        imageButtonPhotoPicker = (ImageButton) view.findViewById(R.id.imageButtonPhotoPicker);
        buttonButtonSendMessage = (ImageButton) view.findViewById(R.id.buttonButtonSendMessage);
        editTextMessage = (EditText) view.findViewById(R.id.editTextMessage);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = app.getRefEventTchats(incomeEventUid);

        adapter = new FirebaseRecyclerAdapter<String, EventPrivateTchatHolder>(String.class, R.layout.card_event_private_tchat, EventPrivateTchatHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPrivateTchatHolder eventPrivateTchatHolder, String s, int position) {
                if (s != null) {
                    getTchat(eventPrivateTchatHolder, getRef(position).getKey());
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
                db.setTchatMessage(editTextMessage.getText().toString(), UUID.randomUUID().toString(), incomeEventUid, currentUserUid);
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


    private void getTchat(final EventPrivateTchatHolder eventPrivateTchatHolder, final String tchatUid) {
        Query query = app.getRefTchat(tchatUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tchat tchat = dataSnapshot.getValue(Tchat.class);
                if (tchat != null) {
                    getUser(eventPrivateTchatHolder, tchat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getUser(final EventPrivateTchatHolder eventPrivateTchatHolder, final Tchat tchat) {
        Query query = app.getRefUser(tchat.getUser());

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
        Query query = app.getRefPhoto(photoUid);

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
        final String userUid = tchat.getUser();

        eventPrivateTchatHolder.setTextViewUsername(username);
        eventPrivateTchatHolder.setImageViewPhotoProfil(getContext(), photoProfil);
        eventPrivateTchatHolder.setImageViewPhoto(getContext(), photo);

        if (userUid.equals(currentUserUid)) {
            eventPrivateTchatHolder.setIsSender(getContext(), true);
        } else {
            eventPrivateTchatHolder.setIsSender(getContext(), false);
        }

        eventPrivateTchatHolder.getImageViewPhotoProfil().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(Utils.TAG, "click");
                goProfilActivity(userUid);
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

        StorageReference photoRef = app.getRefStorageRoot().child(uuid);
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUri = taskSnapshot.getDownloadUrl();
                        db.setTchatPhoto(downloadUri, uuid, incomeEventUid, currentUserUid);
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

    private void goProfilActivity(String userUid) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(Utils.ARG_USER_UID, userUid);
        startActivity(intent);
    }

    private void finishActivity() {
        getActivity().finish();
    }

}