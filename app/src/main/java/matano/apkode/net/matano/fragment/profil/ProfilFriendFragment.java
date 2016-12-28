package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import matano.apkode.net.matano.CityActivity;
import matano.apkode.net.matano.ContryActivity;
import matano.apkode.net.matano.ProfilActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.LocalStorage;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.profil.friend.ProfilFriendFollowerFragment;
import matano.apkode.net.matano.fragment.profil.friend.ProfilFriendFollowingFragment;

public class ProfilFriendFragment extends Fragment {
    private static final String CURRENT_FRAGMENT = "Mes Friends";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private Button buttonFollower;
    private Button buttonFollowing;
    private FrameLayout fragmentLayoutContainer;
    private ProfilFriendFollowerFragment profilFriendFollowerFragment;
    private ProfilFriendFollowingFragment profilFriendFollowingFragment;
    private String currentUserContry;
    private String currentUserCity;
    private LocalStorage localStorage;
    private FirebaseUser user;
    private String currentUserUid;
    private String userUid;

    public ProfilFriendFragment() {
    }

    public static ProfilFriendFragment newInstance(String userUid) {
        ProfilFriendFragment profilFriendFragment = new ProfilFriendFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        profilFriendFragment.setArguments(bundle);
        return profilFriendFragment;
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

        View view = inflater.inflate(R.layout.fragment_profil_friend, container, false);

        buttonFollower = (Button) view.findViewById(R.id.buttonFollower);
        buttonFollowing = (Button) view.findViewById(R.id.buttonFollowing);

        fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragmentLayoutContainer);

        profilFriendFollowerFragment = ProfilFriendFollowerFragment.newInstance(userUid);
        profilFriendFollowingFragment = ProfilFriendFollowingFragment.newInstance(userUid);

        getFragmentManager().beginTransaction().add(R.id.fragmentLayoutContainer, profilFriendFollowerFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (buttonFollower != null) {
            buttonFollower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentLayoutContainer, profilFriendFollowerFragment);
                    transaction.commit();
                }
            });
        }


        if (buttonFollowing != null) {
            buttonFollowing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentLayoutContainer, profilFriendFollowingFragment);
                    transaction.commit();
                }
            });
        }
        
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
