package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.PhotoDialogFragment;
import matano.apkode.net.matano.holder.event.EventPhotoHolder;
import matano.apkode.net.matano.model.Event;
import matano.apkode.net.matano.model.Photo;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EventInfoFragment extends Fragment {
    private App app;
    private String incomeEventUid;
    private Db db;

    private Context context;

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

    public EventInfoFragment() {
    }

    public static EventInfoFragment newInstance(String eventUid) {
        EventInfoFragment eventInfoFragment = new EventInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventInfoFragment.setArguments(bundle);

        return eventInfoFragment;
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

        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);
        textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
        textViewPlace = (TextView) view.findViewById(R.id.textViewPlace);
        textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        textViewTarification = (TextView) view.findViewById(R.id.textViewTarification);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);
        button_participer = (Button) view.findViewById(R.id.button_participer);


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


        Query query = app.getRefEventPhotos(incomeEventUid).limitToFirst(10);

        FirebaseRecyclerAdapter<String, EventPhotoHolder> adapter = new FirebaseRecyclerAdapter<String, EventPhotoHolder>(String.class, R.layout.card_event_info_top_photo, EventPhotoHolder.class, query) {
            @Override
            protected void populateViewHolder(EventPhotoHolder eventPhotoHolder, String s, int position) {
                if (s != null) {
                    getPhoto(eventPhotoHolder, getRef(position).getKey(), position);
                }
            }
        };

        recyclerViewTopPhoto.setAdapter(adapter);


        Query query1 = app.getRefEvent(incomeEventUid);
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
                        db.setParticipantion(tag, incomeEventUid, app.getCurrentUserUid());
                    }
                }
            });
        }

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

        Query query = app.getRefUserEvents(app.getCurrentUserUid()).child(incomeEventUid);

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
                            button_participer.setText("En attente");
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


    private void getPhoto(final EventPhotoHolder eventPhotoHolder, String photoUid, final int position) {
        Query query = app.getRefPhoto(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(eventPhotoHolder, photo, position);

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

    private void displayLayout(EventPhotoHolder eventPhotoHolder, Photo photo, final int position) {
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

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                    PhotoDialogFragment photoDialogFragment = new PhotoDialogFragment();

                    photoDialogFragment.setArguments(bundle);

                    photoDialogFragment.show(fragmentTransaction, Utils.TAG_PHOTO_DIALOG);
                }
            });

        }

    }

    private void finishActivity() {
        getActivity().finish();
    }

}
