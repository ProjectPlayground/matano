package matano.apkode.net.matano.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.fragment.user.friend.ProfilFriendFollowerFragment;
import matano.apkode.net.matano.fragment.user.friend.ProfilFriendFollowingFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserFriendFragment extends Fragment {
    private App app;
    private String incomeUserUid;
    private Db db;

    private Context context;
    private Button buttonFollower;
    private Button buttonFollowing;
    private FrameLayout fragmentLayoutContainer;
    private ProfilFriendFollowerFragment profilFriendFollowerFragment;
    private ProfilFriendFollowingFragment profilFriendFollowingFragment;

    public UserFriendFragment() {
    }

    public static UserFriendFragment newInstance(String userUid) {
        UserFriendFragment userFriendFragment = new UserFriendFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Utils.ARG_USER_UID, userUid);

        userFriendFragment.setArguments(bundle);
        return userFriendFragment;
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

        View view = inflater.inflate(R.layout.fragment_user_friend, container, false);


        incomeUserUid = getArguments().getString(Utils.ARG_USER_UID);

        if (incomeUserUid == null) {
            finishActivity();
        }


        buttonFollower = (Button) view.findViewById(R.id.buttonFollower);
        buttonFollowing = (Button) view.findViewById(R.id.buttonFollowing);

        buttonFollower.setTextColor(getResources().getColor(R.color.colorPrimary));

        fragmentLayoutContainer = (FrameLayout) view.findViewById(R.id.fragmentLayoutContainer);

        profilFriendFollowerFragment = ProfilFriendFollowerFragment.newInstance(incomeUserUid);
        profilFriendFollowingFragment = ProfilFriendFollowingFragment.newInstance(incomeUserUid);

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
                    buttonFollower.setTextColor(getResources().getColor(R.color.colorPrimary));
                    buttonFollowing.setTextColor(getResources().getColor(R.color.black));
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
                    buttonFollowing.setTextColor(getResources().getColor(R.color.colorPrimary));
                    buttonFollower.setTextColor(getResources().getColor(R.color.black));
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


    private void finishActivity() {
        getActivity().finish();
    }


}
