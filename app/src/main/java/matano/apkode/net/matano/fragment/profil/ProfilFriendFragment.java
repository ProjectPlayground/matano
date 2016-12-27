package matano.apkode.net.matano.fragment.profil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.fragment.profil.friend.ProfilFriendFollowerFragment;
import matano.apkode.net.matano.fragment.profil.friend.ProfilFriendFollowingFragment;
import matano.apkode.net.matano.holder.profil.ProfilFriendHolder;

public class ProfilFriendFragment extends Fragment {
    private static String ARG_USER_UID = "userUid";
    private Context context;
    private RecyclerView recyclerViewFollowers;
    private RecyclerView recyclerViewFollowings;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private DatabaseReference mRootRef;
    private DatabaseReference refUser;
    private FirebaseRecyclerAdapter<String, ProfilFriendHolder> adapterFollowers;
    private FirebaseRecyclerAdapter<String, ProfilFriendHolder> adapterFollowings;
    private LinearLayoutManager managerFollowers;
    private LinearLayoutManager managerFollowings;
    private Button buttonFollower;
    private Button buttonFollowing;
    private FrameLayout fragmentLayoutContainer;
    private ProfilFriendFollowerFragment profilFriendFollowerFragment;
    private ProfilFriendFollowingFragment profilFriendFollowingFragment;

    public ProfilFriendFragment() {
    }

    public ProfilFriendFragment newInstance(String userUid) {
        ProfilFriendFragment profilFriendFragment = new ProfilFriendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_UID, userUid);
        profilFriendFragment.setArguments(bundle);
        ARG_USER_UID = userUid;
        return profilFriendFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mRootRef = database.getReference();
        refUser = mRootRef.child("user").child(ARG_USER_UID);

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
        View view = inflater.inflate(R.layout.fragment_profil_friend, container, false);

        buttonFollower = (Button) view.findViewById(R.id.buttonFollower);
        buttonFollowing = (Button) view.findViewById(R.id.buttonFollowing);

        fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragmentLayoutContainer);

        profilFriendFollowerFragment = new ProfilFriendFollowerFragment().newInstance(getContext(), ARG_USER_UID);
        profilFriendFollowingFragment = new ProfilFriendFollowingFragment().newInstance(getContext(), ARG_USER_UID);

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


}
