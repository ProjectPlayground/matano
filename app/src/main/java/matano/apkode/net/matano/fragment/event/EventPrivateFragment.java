package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import matano.apkode.net.matano.LoginActivity;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.event.privates.EventPrivatePhotoFragment;
import matano.apkode.net.matano.fragment.event.privates.EventPrivateTchatFragment;

public class EventPrivateFragment extends Fragment {
    private FbDatabase fbDatabase;
    private String incomeEventUid;
    private Db db;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private Context context;
    private ImageButton imageButtonPrivateTchat;
    private ImageButton imageButtonPrivatePhoto;
    private FrameLayout fragmentLayoutContainer;
    private EventPrivateTchatFragment eventPrivateTchatFragment;
    private EventPrivatePhotoFragment eventPrivatePhotoFragment;

    public EventPrivateFragment() {
    }

    public static EventPrivateFragment newInstance(String eventUid) {
        EventPrivateFragment eventPrivateFragment = new EventPrivateFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_EVENT_UID, eventUid);

        eventPrivateFragment.setArguments(bundle);
        return eventPrivateFragment;
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

        View view = inflater.inflate(R.layout.fragment_event_private, container, false);


        incomeEventUid = getArguments().getString(Utils.ARG_EVENT_UID);

        if (incomeEventUid == null) {
            finishActivity();
        }


        imageButtonPrivateTchat = (ImageButton) view.findViewById(R.id.imageButtonPrivateTchat);
        imageButtonPrivatePhoto = (ImageButton) view.findViewById(R.id.imageButtonPrivatePhoto);

        imageButtonPrivateTchat.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragmentLayoutContainer);

        eventPrivateTchatFragment = EventPrivateTchatFragment.newInstance(incomeEventUid);
        eventPrivatePhotoFragment = EventPrivatePhotoFragment.newInstance(incomeEventUid);


        getFragmentManager().beginTransaction().add(R.id.fragmentLayoutContainer, eventPrivateTchatFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageButtonPrivateTchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonPrivateTchat.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                imageButtonPrivatePhoto.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayoutContainer, eventPrivateTchatFragment);
                transaction.commit();
            }
        });


        imageButtonPrivatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButtonPrivatePhoto.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                imageButtonPrivateTchat.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentLayoutContainer, eventPrivatePhotoFragment);
                transaction.commit();
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

    private void goLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void finishActivity() {
        getActivity().finish();
    }

}
