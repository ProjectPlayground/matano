package matano.apkode.net.matano.fragment.profil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.model.User;

public class ProfilInfoFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeUserUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;

    private TextView textViewFollowersNumber;
    private TextView textViewFollowingsNumber;
    private TextView textViewPhotosNumber;
    private TextView textViewUsername;
    private TextView textViewPresentation;
    private ImageView imageViewPhotoProfil;
    private ImageButton imageButtonAddOrSetting;


    public ProfilInfoFragment() {
    }

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createAuthStateListener();

        db = new Db(context);
        fbDatabase = new FbDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_profil_info, container, false);

        incomeUserUid = getArguments().getString(Utils.ARG_USER_UID);

        if (incomeUserUid == null) {
            finishActivity();
        }

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

        Query query = fbDatabase.getRefUser(incomeUserUid);
        query.keepSynced(true);

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
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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


    private void createAuthStateListener() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser == null) {
                    goLogin();
                } else {
                    currentUserUid = currentUser.getUid();
                }
            }
        };
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


        if (imageViewPhotoProfil != null && photoProfl != null && context != null) {

            Activity activity = (Activity) context;
            if (activity.isFinishing())
                return;

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

            if (currentUserUid.equals(incomeUserUid)) {
                imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_action_settings_padding);
                imageButtonAddOrSetting.setVisibility(View.VISIBLE);

                imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Intent intent = new Intent(context, )
                    }
                });

            } else {
                isUserMyFriend(imageButtonAddOrSetting);
            }

            imageButtonAddOrSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.setFollowing(incomeUserUid, (String) view.getTag(), currentUserUid);
                }
            });

        }

    }

    private void isUserMyFriend(final ImageButton imageButtonAddOrSetting) {
        Query query = fbDatabase.getRefUserFollowings(currentUserUid);
        query.keepSynced(true);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    imageButtonAddOrSetting.setTag("1");
                    imageButtonAddOrSetting.setImageResource(R.mipmap.ic_action_social_group_add_padding);
                } else {
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        if (snap.getKey().equals(incomeUserUid)) {
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

    private void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void finishActivity() {
        getActivity().finish();
    }

}
