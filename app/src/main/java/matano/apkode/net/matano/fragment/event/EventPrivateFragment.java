package matano.apkode.net.matano.fragment.event;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.event.privates.EventPrivatePhotoFragment;
import matano.apkode.net.matano.fragment.event.privates.EventPrivateTchatFragment;

public class EventPrivateFragment extends Fragment {
    private static String ARG_EVENT_UID = "eventUid";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refEvent;
    private Button buttonPrivateTchat;
    private Button buttonPrivatePhoto;
    private FrameLayout fragmentLayoutContainer;
    private EventPrivateTchatFragment eventPrivateTchatFragment;
    private EventPrivatePhotoFragment eventPrivatePhotoFragment;

    public EventPrivateFragment() {
    }

    public EventPrivateFragment newInstance(Context ctx, String eventUid) {
        context = ctx;
        EventPrivateFragment eventPrivateFragment = new EventPrivateFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_EVENT_UID, eventUid);
        eventPrivateFragment.setArguments(bundle);
        ARG_EVENT_UID = eventUid;
        return eventPrivateFragment;
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
        View view = inflater.inflate(R.layout.fragment_event_private, container, false);
        ButterKnife.bind(this, view);

        buttonPrivateTchat = (Button) view.findViewById(R.id.buttonPrivateTchat);
        buttonPrivatePhoto = (Button) view.findViewById(R.id.buttonPrivatePhoto);

        fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragmentLayoutContainer);

        eventPrivateTchatFragment = new EventPrivateTchatFragment().newInstance(getContext(), ARG_EVENT_UID);
        eventPrivatePhotoFragment = new EventPrivatePhotoFragment().newInstance(getContext(), ARG_EVENT_UID);

        getFragmentManager().beginTransaction().add(R.id.fragmentLayoutContainer, eventPrivateTchatFragment).commit();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (buttonPrivateTchat != null) {
            buttonPrivateTchat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentLayoutContainer, eventPrivateTchatFragment);
                    transaction.commit();
                }
            });
        }

        if (buttonPrivatePhoto != null) {
            buttonPrivatePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.fragmentLayoutContainer, eventPrivatePhotoFragment);
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
}
