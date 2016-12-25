package matano.apkode.net.matano.fragment.event;

import android.content.Context;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.dialogfragment.PhotoDialog;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;

public class EventInfoFragment extends Fragment {
    private static String ARG_EVENT_UID = "eventUid";
    private Context context;
    private RecyclerView recyclerViewTopPhoto;
    private RecyclerView recyclerViewTopUser;
    private List<Photo> photos = new ArrayList<>();
    private String eventKey;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEventUser;
    private DatabaseReference refEvent;
    private DatabaseReference refPhotos;
    private DatabaseReference refPhoto;
    private FirebaseUser user;
    private FirebaseRecyclerAdapter<String, EventPhotoHolder> adapter;
    private LinearLayoutManager manager;

    private ImageView imageViewPhotoProfil;
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewAddress;
    private TextView textViewDate;
    private TextView textViewTarification;
    private TextView textViewPresentation;
    private Button button_participer;

    private SimpleDateFormat simpleDateFormat;

    private String title = null;
    private String category = null;
    private String subCategory = null;
    private String contry = null;
    private String city = null;
    private String place = null;
    private String address = null;
    private Double longitude = null;
    private Double latitude = null;
    private Double altitude = null;
    private String date = null;
    private String presentation = null;
    private String photoProfil = null; // idPhoto
    private String videoProfil = null; //  idVideo
    private String tarification = null; // gratuit - payant - free

    private List<String> tarifs = null; // Uid
    private Map<String, String> users = null;  // uId - status


    public EventInfoFragment() {
    }

    public EventInfoFragment newInstance(Context context, String eventUid) {
        this.context = context;
        eventKey = eventUid;

        EventInfoFragment eventInfoFragment = new EventInfoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_EVENT_UID, eventUid);
        eventInfoFragment.setArguments(args);
        ARG_EVENT_UID = eventUid;
        return eventInfoFragment;
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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // TODO go sign in
                }
            }
        };

        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) view.findViewById(R.id.textViewPlace);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewTarification = (TextView) view.findViewById(R.id.textViewTarification);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);
        button_participer = (Button) view.findViewById(R.id.button_participer);


        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewTopPhoto = (RecyclerView) view.findViewById(R.id.recyclerViewTopPhoto);
        recyclerViewTopPhoto.setHasFixedSize(true);
        recyclerViewTopPhoto.setLayoutManager(manager);
        recyclerViewTopPhoto.setItemAnimator(new DefaultItemAnimator());

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);


        Query query = refPhotos.limitToFirst(10);

        adapter = new FirebaseRecyclerAdapter<String, EventPhotoHolder>(String.class, R.layout.card_event_info_top_photo, EventPhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPhotoHolder eventPhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPhotoHolder, getRef(position).getKey());
                }
            }
        };

        recyclerViewTopPhoto.setAdapter(adapter);


        refEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                if (event == null) {
                    // TODO someting

                } else {
                    if (event.getTitle() != null) {
                        title = event.getTitle();
                    }
                    if (event.getCategory() != null) {
                        category = event.getCategory();
                    }
                    if (event.getSubCategory() != null) {
                        subCategory = event.getSubCategory();
                    }
                    if (event.getContry() != null) {
                        contry = event.getContry();
                    }
                    if (event.getCity() != null) {
                        city = event.getCity();
                    }
                    if (event.getPlace() != null) {
                        place = event.getPlace();
                    }
                    if (event.getAddress() != null) {
                        address = event.getAddress();
                    }
                    if (event.getLongitude() != null) {
                        longitude = event.getLongitude();
                    }
                    if (event.getLatitude() != null) {
                        latitude = event.getLatitude();
                    }
                    if (event.getAltitude() != null) {
                        altitude = event.getAltitude();
                    }
                    if (event.getAltitude() != null) {
                        altitude = event.getAltitude();
                    }
                    if (event.getDate() != null) {
                        date = simpleDateFormat.format(event.getDate());
                    }

                    if (event.getPresentation() != null) {
                        presentation = event.getPresentation();
                    }
                    if (event.getPhotoProfil() != null) {
                        photoProfil = event.getPhotoProfil();
                    }
                    if (event.getVideoProfil() != null) {
                        videoProfil = event.getVideoProfil();
                    }
                    if (event.getTarification() != null) {
                        tarification = event.getTarification();
                    }
                    if (event.getUsers() != null) {
                        users = event.getUsers();
                    }

                    if (imageViewPhotoProfil != null) {
                        if (photoProfil != null) {
                            Glide
                                    .with(getContext())
                                    .load(photoProfil)
                                    //  .centerCrop()
                                    .into(imageViewPhotoProfil);
                        }
                    }
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
                            textViewDate.setText(date);
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
                                        // button_participer.setText("En attente");
                                        break;
                                    case "0":
                                        button_participer.setTag("9");
                                        // button_participer.setText("Participer");
                                        break;
                                    case "1":
                                        button_participer.setTag("9");
                                        //   button_participer.setText("Participer");
                                        break;
                                }
                                setUserToEvent();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void isUserParticipe() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        refEventUser = refEvent.child("users").child(currentUser.getUid());

        refEventUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    button_participer.setText("Participer");
                    button_participer.setTag("9");
                    button_participer.setVisibility(View.VISIBLE);
                } else {
                    String status = dataSnapshot.getValue(String.class);
                    switch (status) {
                        case "0":
                            button_participer.setText("En attente");
                            button_participer.setTag("0");
                            button_participer.setVisibility(View.VISIBLE);
                            break;
                        case "1":
                            button_participer.setText("Je participe");
                            button_participer.setTag("1");
                            button_participer.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setUserToEvent() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        String tag = button_participer.getTag().toString();

        switch (tag) {
            case "9":
                tag = null;
                break;
            case "0":
                tag = "0";
                break;
        }


        Map hashMap = new HashMap();
        hashMap.put("event/" + ARG_EVENT_UID + "/users/" + currentUser.getUid(), tag);
        hashMap.put("user/" + currentUser.getUid() + "/events/" + ARG_EVENT_UID, tag);

        mRootRef.updateChildren(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(Utils.TAG, "error " + databaseError.getMessage());
                }
            }
        });
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

}
