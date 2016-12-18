package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.activity.SignInActivity;
import matano.apkode.net.matano.adapter.event.EventTopParticipantAdapter;
import matano.apkode.net.matano.adapter.event.EventTopPhotoAdapter;
import matano.apkode.net.matano.holder.profil.ProfilEventHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;

public class EventInfoFragment extends Fragment {
    private static final String ARG_EVENT_KEY = null;
    private Context context;
    private RecyclerView recyclerViewTopPhoto;
    private RecyclerView recyclerViewTopUser;
    private List<Photo> photos = new ArrayList<>();
    private EventTopPhotoAdapter eventTopPhotoAdapter;
    private EventTopParticipantAdapter eventTopParticipantAdapter;
    private String eventKey;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private DatabaseReference refEvent;
    private FirebaseUser user;
    private FirebaseRecyclerAdapter<Event, ProfilEventHolder> adapter;
    private LinearLayoutManager manager;
    private ImageView imageViewPhotoProfil;
    private TextView textViewTitle;
    private TextView textViewPlace;
    private TextView textViewAddress;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private TextView textViewTarification;
    private TextView textViewPresentation;
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
    private String dateStart = null;
    private String dateEnd = null;
    private String presentation = null;
    private String photoProfil = null; // idPhoto
    private String videoProfil = null; //  idVideo
    private String tarification = null; // gratuit - payant - free

    private List<String> tarifs = new ArrayList<>(); // Uid
    private Map<String, String> users = new HashMap<>();  // uId - status


    public EventInfoFragment() {
    }

    public EventInfoFragment newInstance(Context ctx, String key) {
        context = ctx;
        eventKey = key;

        EventInfoFragment eventInfoFragment = new EventInfoFragment();

        Bundle args = new Bundle();
        args.putString(ARG_EVENT_KEY, key);
        eventInfoFragment.setArguments(args);
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

        String key = getArguments().getString(ARG_EVENT_KEY);

        if (key == null) {
            // TODO something
        } else {
            refEvent = mRootRef.child("event").child(key);

            if (refEvent == null) {
                //TODO do something
            } else {
                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            //  Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                        } else {
                            // Log.d(TAG, "onAuthStateChanged:signed_out");
                            goSignIn();
                        }
                    }
                };
            }
        }

        // eventTopPhotoAdapter = new EventTopPhotoAdapter(photos);
        // eventTopParticipantAdapter = new EventTopParticipantAdapter(users);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_info, container, false);

        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) view.findViewById(R.id.textViewPlace);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        textViewDateStart = (TextView) view.findViewById(R.id.textViewDateStart);
        textViewDateEnd = (TextView) view.findViewById(R.id.textViewDateEnd);
        textViewTarification = (TextView) view.findViewById(R.id.textViewTarification);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

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
                    if (event.getDateStart() != null) {
                        dateStart = simpleDateFormat.format(event.getDateStart());
                    }
                    if (event.getDateEnd() != null) {
                        dateEnd = simpleDateFormat.format(event.getDateEnd());
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
                    if (textViewDateStart != null) {
                        if (dateStart != null) {
                            textViewDateStart.setText(dateStart);
                        }
                    }
                    if (textViewDateEnd != null) {
                        if (dateEnd != null) {
                            textViewDateEnd.setText(dateEnd);
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


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


       /* recyclerViewTopPhoto = (RecyclerView) view.findViewById(R.id.recyclerViewTopPhoto);
        recyclerViewTopUser = (RecyclerView) view.findViewById(R.id.recyclerViewTopParticipant);

        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());
        photos.add(new Photo());


        if (null != recyclerViewTopPhoto) {
            recyclerViewTopPhoto.setHasFixedSize(true);
            recyclerViewTopPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopPhoto.setAdapter(eventTopPhotoAdapter);
        }

        recyclerViewTopPhoto.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewTopPhoto, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("Photo", (Serializable) photos);
                bundle.putInt("position", position);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                PhotoDialog photoDialog = new PhotoDialog();

                photoDialog.setArguments(bundle);

                photoDialog.show(fragmentTransaction, "PhotoDialog");


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());
        users.add(new User());

        if (null != recyclerViewTopUser) {
            recyclerViewTopUser.setHasFixedSize(true);
            recyclerViewTopUser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewTopUser.setAdapter(eventTopParticipantAdapter);
        }
*/

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

    private void goSignIn() {
        startActivity(new Intent(getContext(), SignInActivity.class));
        getActivity().finish();
    }

}
