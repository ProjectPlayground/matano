package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.model.User;

public class ProfilInfoFragment extends Fragment {
    private static final String CURRENT_FRAGMENT = "Info";
    TextView textViewFollowersNumber;
    TextView textViewFollowingsNumber;
    TextView textViewPhotosNumber;
    TextView textViewUsername;
    TextView textViewPresentation;
    ImageView imageViewPhotoProfil;
    ImageButton imageButtonAddOrSetting;
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private String userUid;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;

    public static ProfilInfoFragment newInstance(String userUid) {
        ProfilInfoFragment profilInfoFragment = new ProfilInfoFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilInfoFragment.setArguments(bundle);

        return profilInfoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        userUid = getArguments().getString(Utils.ARG_USER_UID);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = new LocalStorage(context);
        currentUserContry = localStorage.getContry();
        currentUserCity = localStorage.getCity();

        if (userUid == null) {
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
        refUser = mRootRef.child("user").child(userUid);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        ActionBar supportActionBar = ((ProfilActivity) getActivity()).getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setTitle(CURRENT_FRAGMENT);
        }

        View view = inflater.inflate(R.layout.fragment_profil_info, container, false);

        textViewFollowersNumber = (TextView) view.findViewById(R.id.textViewFollowersNumber);
        textViewFollowingsNumber = (TextView) view.findViewById(R.id.textViewFollowingsNumber);
        textViewPhotosNumber = (TextView) view.findViewById(R.id.textViewPhotosNumber);
        textViewUsername = (TextView) view.findViewById(R.id.textViewUsername);
        textViewPresentation = (TextView) view.findViewById(R.id.textViewPresentation);
        imageViewPhotoProfil = (ImageView) view.findViewById(R.id.imageViewPhotoProfil);
        imageButtonAddOrSetting = (ImageButton) view.findViewById(R.id.imageButtonAddOrSetting);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = refUser;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    displayUserInformation(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle error
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void displayUserInformation(User user) {
        String username = user.getUsername();
        String photoProfl = user.getPhotoProfl();
        Map<String, String> followers = user.getFollowers();
        Map<String, String> followings = user.getFollowings();
        Map<String, String> photos = user.getPhotos();
        String presentation = user.getPresentation();


        if (textViewUsername != null && username != null) {
            textViewUsername.setText(username);
        }

        if (textViewPresentation != null && presentation != null) {
            textViewPresentation.setText(presentation);
        }


        if (imageViewPhotoProfil != null && photoProfl != null) {
                Glide
                        .with(context)
                        .load(photoProfl)
                        //  .centerCrop()
                        .into(imageViewPhotoProfil);
        }

        if (textViewFollowersNumber != null) {
            if (followers == null) {
                textViewFollowersNumber.setText("0" + " " + getResources().getString(R.string.followers));
            } else {
                textViewFollowersNumber.setText(followers.size() + " " + getResources().getString(R.string.followers));
            }
        }

        if (textViewFollowingsNumber != null) {
            if (followings == null) {
                textViewFollowingsNumber.setText("0" + " " + getResources().getString(R.string.followings));
            } else {
                textViewFollowingsNumber.setText(followings.size() + " " + getResources().getString(R.string.followings));
            }
        }

        if (textViewPhotosNumber != null) {
            if (photos == null) {
                textViewPhotosNumber.setText("0" + " " + getResources().getString(R.string.photos));
            } else {
                textViewPhotosNumber.setText(photos.size() + " " + getResources().getString(R.string.photos));
            }
        }

        if (imageButtonAddOrSetting != null) {

            if (currentUserUid.equals(userUid)) {
                imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_action_settings_padding);
                imageButtonAddOrSetting.setVisibility(View.VISIBLE);

                imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Intent intent = new Intent(getContext(), )
                    }
                });

            } else {
                isUserMyFriend(imageButtonAddOrSetting, userUid);
            }

            imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addFollowing(userUid, (String) view.getTag());
                }
            });

        }

    }

    private void isUserMyFriend(final ImageButton imageButtonAddOrSetting, final String userUid) {
        Query query = mRootRef.child("user").child(currentUserUid).child("followings");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    imageButtonAddOrSetting.setTag("1");
                    imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (snap.getKey().equals(userUid)) {
                            // We are friends
                            imageButtonAddOrSetting.setTag(null);
                            imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_people_padding);
                        } else {
                            // we are not friends
                            imageButtonAddOrSetting.setTag("1");
                            imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                        }
                    }
                }
                imageButtonAddOrSetting.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // TODO handle error
            }
        });
    }

    private void addFollowing(String userUid, String tag) {
        Map hashMap = new HashMap();
        hashMap.put("user/" + userUid + "/followers/" + currentUserUid, tag);
        hashMap.put("user/" + currentUserUid + "/followings/" + userUid, tag);

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
