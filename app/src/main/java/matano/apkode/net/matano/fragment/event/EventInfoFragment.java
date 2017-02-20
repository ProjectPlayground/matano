package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.ModuleActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;

public class EventInfoFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeEventUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;

    private FirebaseRecyclerAdapter<String, EventPhotoHolder> adapter;

    private RecyclerView recyclerViewTopPhoto;
    private RecyclerView recyclerViewTopUser;
    private List<Photo> photos = new ArrayList<>();
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewAddress;
    private TextView textViewDate;
    private TextView textViewTarification;
    private TextView textViewPresentation;
    private Button button_participer;
    private ImageView imageViewPhotoProfil;
    private ListView listViewProgramme;
    private Button buttonProgramme;
    private Button buttonInvite;
    private Button buttonIntervenant;
    private Button buttonActivite;


    public EventInfoFragment() {
    }

    public static EventInfoFragment newInstance(String eventUid) {
        EventInfoFragment eventInfoFragment = new EventInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        Log.e(Utils.TAG, "newInstance Info");

        eventInfoFragment.setArguments(bundle);

        return eventInfoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Log.e(Utils.TAG, "onAttach Info");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Db(context);
        fbDatabase = new FbDatabase();

        Log.e(Utils.TAG, "onCreate Info");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }

        Log.e(Utils.TAG, "onCreateView Info");

        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) view.findViewById(R.id.textViewPlace);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewTarification = (TextView) view.findViewById(R.id.textViewTarification);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);
        button_participer = (Button) view.findViewById(R.id.button_participer);

        buttonProgramme = (Button) view.findViewById(R.id.buttonProgramme);
        buttonInvite = (Button) view.findViewById(R.id.buttonInvite);
        buttonIntervenant = (Button) view.findViewById(R.id.buttonIntervenant);
        buttonActivite = (Button) view.findViewById(R.id.buttonActivite);


        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewTopPhoto = (RecyclerView) view.findViewById(R.id.recyclerViewTopPhoto);
        recyclerViewTopPhoto.setHasFixedSize(true);
        recyclerViewTopPhoto.setLayoutManager(manager);
        recyclerViewTopPhoto.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(Utils.TAG, "onViewCreated Info");
        createAuthStateListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(Utils.TAG, "onStart info");
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        Log.e(Utils.TAG, "onResume Info");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e(Utils.TAG, "onPause Info");
        super.onPause();
    }


    @Override
    public void onStop() {
        Log.e(Utils.TAG, "onStop Info");
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroyView() {
        Log.e(Utils.TAG, "onDestroyView Info");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(Utils.TAG, "onDestroy Info");
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
            adapter.notifyDataSetChanged();
        }
        Log.e(Utils.TAG, "onDestroy Info fin");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e(Utils.TAG, "onDetach Info");
    }

    private void createAuthStateListener() {
        Log.e(Utils.TAG, "createAuthStateListener info");
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
        Query query = fbDatabase.getRefEventPhotos(incomeEventUid).limitToFirst(10);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<String, EventPhotoHolder>(String.class, R.layout.card_event_info_top_photo, EventPhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPhotoHolder eventPhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPhotoHolder, getRef(position).getKey(), position);
                }
            }
        };

        recyclerViewTopPhoto.setAdapter(adapter);

        Query query1 = fbDatabase.getRefEvent(incomeEventUid);
        query1.keepSynced(true);

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);
                if (event != null) {
                    displayLayoutMain(event);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonProgramme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModuleActivity.class);
                intent.putExtra(Utils.ARG_EVENT_UID, incomeEventUid);
                intent.putExtra(Utils.ARG_MODULE, Utils.FIREBASE_DATABASE_EVENT_PROGRAMMES);

                startActivity(intent);
            }
        });

        buttonIntervenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModuleActivity.class);
                intent.putExtra(Utils.ARG_EVENT_UID, incomeEventUid);
                intent.putExtra(Utils.ARG_MODULE, Utils.FIREBASE_DATABASE_EVENT_INTERVENANTS);

                startActivity(intent);
            }
        });

        buttonInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModuleActivity.class);
                intent.putExtra(Utils.ARG_EVENT_UID, incomeEventUid);
                intent.putExtra(Utils.ARG_MODULE, Utils.FIREBASE_DATABASE_EVENT_INVITES);

                startActivity(intent);
            }
        });

        buttonActivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ModuleActivity.class);
                intent.putExtra(Utils.ARG_EVENT_UID, incomeEventUid);
                intent.putExtra(Utils.ARG_MODULE, Utils.FIREBASE_DATABASE_EVENT_ACTIVITES);

                startActivity(intent);
            }
        });


       /* fbDatabase.getRefEventProgrammes(incomeEventUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Programme programme = snapshot.getValue(Programme.class);
                    Log.e(Utils.TAG, "snapshot : "+programme.getTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseListAdapter<Programme> adapterProgramme = new FirebaseListAdapter<Programme>(getActivity(), Programme.class, android.R.layout.simple_list_item_1, databaseReferenceProgramme) {

            @Override
            protected void populateView(View view, Programme programme, int position) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(programme.getTitle());
                Log.e(Utils.TAG, ""+programme.getTitle());
            }
        };

        listViewProgramme.setAdapter(adapterProgramme);*/


    }


    private void displayLayoutMain(Event event) {
        String title = event.getTitle();
        String place = event.getPlace();
        String address = event.getAddress();
        Date date = event.getDate();
        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date);
        String presentation = event.getPresentation();
        String tarification = event.getTarification();

        if (textViewTitle != null) {
            if (title != null) {
                textViewTitle.setText(title);
            }
        }
        if (textViewPlace != null) {
            if (place != null) {
                textViewPlace.setText(place);
            }
        }
        if (textViewAddress != null) {
            if (address != null) {
                textViewAddress.setText(address);
            }
        }
        if (textViewDate != null) {
            if (date != null) {
                textViewDate.setText(dateString);
            }
        }

        if (textViewTarification != null) {
            if (tarification != null) {
                textViewTarification.setText(tarification);
            }
        }

        if (textViewPresentation != null) {
            if (presentation != null) {
                textViewPresentation.setText(presentation);
            }
        }


        if (button_participer != null) {

            isUserParticipe();

            button_participer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (button_participer.getTag().toString()) {
                        case "9":
                            button_participer.setTag("0");
                            break;
                        case "0":
                            button_participer.setTag("9");
                            break;
                        case "1":
                            button_participer.setTag("9");
                            break;
                    }
                    String tag = button_participer.getTag().toString();
                    if (tag != null) {
                        db.setParticipantion(tag, incomeEventUid, currentUserUid);
                    }
                }
            });
        }

    }

    private void isUserParticipe() {

        Query query = fbDatabase.getRefUserEvents(currentUserUid).child(incomeEventUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    button_participer.setText("Participer");
                    button_participer.setTag("9");
                } else {
                    String status = dataSnapshot.getValue(String.class);
                    switch (status) {
                        case "0":
                            // button_participer.setText("En attente");
                            button_participer.setText("Je participe");
                            button_participer.setTag("0");
                            break;
                        case "1":
                            button_participer.setText("Je participe");
                            button_participer.setTag("1");
                            break;
                    }
                }
                button_participer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getPhoto(final EventPhotoHolder eventPhotoHolder, final String photoUid, final int position) {
        Query query = fbDatabase.getRefPhoto(photoUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(eventPhotoHolder, photo, photoUid, position);

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

    private void displayLayout(EventPhotoHolder eventPhotoHolder, Photo photo, final String photoUid, final int position) {
        String url = photo.getUrl();
        final String userUid = photo.getUser();

        if (url != null) {
            eventPhotoHolder.setImageViewPhoto(context, photo.getUrl());

            eventPhotoHolder.itemView.setOnClickListener(new View.OnClickListener() {
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

        }

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
