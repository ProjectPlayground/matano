package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.model.User;

public class ProfilInfoFragment extends Fragment {
    TextView textViewFollowersNumber;
    TextView textViewFollowingsNumber;
    TextView textViewPhotosNumber;
    TextView textViewUsername;
    TextView textViewPresentation;
    ImageView imageViewPhotoProfil;

    //    @BindView(R.id.textViewFollowersNumber) TextView textViewFollowersNumber;
//    @BindView(R.id.textViewFollowingsNumber) TextView textViewFollowingsNumber;
//    @BindView(R.id.textViewPhotosNumber) TextView textViewPhotosNumber;
//    @BindView(R.id.textViewUsername) TextView textViewUsername;
//    @BindView(R.id.imageViewPhotoProfil) ImageView imageViewPhotoProfil;
//    @BindView(R.id.textViewPresentation) TextView textViewPresentation;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private String username = null;
    private String firstName = null;
    private String lastName = null;
    private String contry = null;
    private String city = null;
    private String email = null;
    private String telephone = null;
    private String sexe = null;
    private Date birthday = null;
    private String presentation = null;
    private String photoProfl = null;

    private Map<String, Boolean> events = null;  // idEvent - boolean
    private HashSet<String> followers = null;  // Uid
    private HashSet<String> followings = null;  // Uid
    private HashSet<String> photos = null;   // idPhoto
    private HashSet<String> videos = null;   // idVidoe
    private HashSet<String> tickets = null;   // idTicket

    private int eventsNumber = 0;
    private int followersNumber = 0;
    private int followingsNumber = 0;
    private int photosNumber = 0;
    private int videosNumber = 0;
    private int ticketsNumber = 0;


    public ProfilInfoFragment() {
    }

    public ProfilInfoFragment newInstance(Context ctx) {
        context = ctx;
        ProfilInfoFragment profilInfoFragment = new ProfilInfoFragment();
        return profilInfoFragment;
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
        refUser = mRootRef.child("user");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewFollowersNumber = (TextView) view.findViewById(R.id.textViewFollowersNumber);
        textViewFollowingsNumber = (TextView) view.findViewById(R.id.textViewFollowingsNumber);
        textViewPhotosNumber = (TextView) view.findViewById(R.id.textViewPhotosNumber);
        textViewUsername = (TextView) view.findViewById(R.id.textViewUsername);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);
        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    refUser = refUser.child(user.getUid());
                    getUserData();
                } else {
                    // TODO go sign in
                }
            }
        };


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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getUserData() {
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (null != user) {
                    displayUserInformation(user);
                } else {
                    // TODO check
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayUserInformation(User user) {

        if (user.getUsername() != null) {
            username = user.getUsername();
        }

        if (user.getFirstName() != null) {
            firstName = user.getFirstName();
        }
        if (user.getLastName() != null) {
            lastName = user.getLastName();
        }
        if (user.getContry() != null) {
            contry = user.getContry();
        }
        if (user.getCity() != null) {
            city = user.getCity();
        }
        if (user.getEmail() != null) {
            email = user.getEmail();
        }
        if (user.getTelephone() != null) {
            telephone = user.getTelephone();
        }
        if (user.getSexe() != null) {
            sexe = user.getSexe();
        }
        if (user.getBirthday() != null) {
            birthday = user.getBirthday();
        }
        if (user.getPresentation() != null) {
            presentation = user.getPresentation();
        }
        if (user.getPhotoProfl() != null) {
            photoProfl = user.getPhotoProfl();
        }
        if (user.getEvents() != null) {
            events = user.getEvents();
        }
        if (user.getFollowers() != null) {
            followers = user.getFollowers();
        }
        if (user.getFollowings() != null) {
            followings = user.getFollowings();
        }
        if (user.getPhotos() != null) {
            photos = user.getPhotos();
        }
        if (user.getVideos() != null) {
            videos = user.getVideos();
        }
        if (user.getTickets() != null) {
            tickets = user.getTickets();
        }


        if (events != null) {
            eventsNumber = events.size();
        }
        if (followers != null) {
            followersNumber = followers.size();
        }
        if (followings != null) {
            followingsNumber = followings.size();
        }
        if (photos != null) {
            photosNumber = photos.size();
        }
        if (videos != null) {
            videosNumber = videos.size();
        }
        if (tickets != null) {
            ticketsNumber = tickets.size();
        }


        if (textViewFollowersNumber != null) {
            textViewFollowersNumber.setText("" + followersNumber);
        }
        if (textViewFollowingsNumber != null) {
            textViewFollowingsNumber.setText("" + followingsNumber);
        }
        if (textViewPhotosNumber != null) {
            textViewPhotosNumber.setText("" + photosNumber);
        }
        if (textViewUsername != null) {
            if (username != null) {
                textViewUsername.setText("?" + username);
            }
        }
        if (imageViewPhotoProfil != null) {
            if (photoProfl != null) {
                Glide
                        .with(getContext())
                        .load(photoProfl)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
            }
        }
        if (textViewPresentation != null) {
            if (presentation != null) {
                textViewPresentation.setText(presentation);
            } else {
                textViewPresentation.setText("Introduce yourself ...");
            }
        }

    }


}
