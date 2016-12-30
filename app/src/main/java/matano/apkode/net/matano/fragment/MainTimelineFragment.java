package matano.apkode.net.matano.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import matano.apkode.net.matano.R;
import matano.apkode.net.matano.config.App;
import matano.apkode.net.matano.config.Db;
import matano.apkode.net.matano.holder.MainTimelineHolder;
import matano.apkode.net.matano.model.Photo;
import matano.apkode.net.matano.model.User;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainTimelineFragment extends Fragment {
    private App app;
    private Db db;

    private Context context;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<String, MainTimelineHolder> adapter;


    public MainTimelineFragment() {
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

        View view = inflater.inflate(R.layout.fragment_main_timeline, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Query query = app.getRefUserFollowings(app.getCurrentUserUid());

        adapter = new FirebaseRecyclerAdapter<String, MainTimelineHolder>(String.class, R.layout.card_main_timeline, MainTimelineHolder.class, query) {
            @Override
            protected void populateViewHolder(MainTimelineHolder mainTimelineHolder, String s, int position) {
                if (s != null) {
                    getUser(mainTimelineHolder, getRef(position).getKey());
                }
            }
        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        if (adapter != null) {
            adapter.cleanup();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void getUser(final MainTimelineHolder mainTimelineHolder, final String userUid) {
        Query query = app.getRefUser(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    getPhotos(mainTimelineHolder, user, userUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getPhotos(final MainTimelineHolder mainTimelineHolder, final User user, String userUid) {
        Query query = app.getRefUserPhotos(userUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String photoUid = snap.getKey();
                    if (photoUid != null) {
                        getPhoto(mainTimelineHolder, user, photoUid);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPhoto(final MainTimelineHolder mainTimelineHolder, final User user, String photoUid) {
        Query query = app.getRefPhoto(photoUid);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);

                if (photo != null) {
                    displayLayout(mainTimelineHolder, user, photo);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayLayout(final MainTimelineHolder mainTimelineHolder, User user, Photo photo) {
        String username = user.getUsername();
        String photoProfl = user.getPhotoProfl();
        String url = photo.getUrl();
        Date date = photo.getDate();
        String dateString = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE).format(date);

        if (username != null && photoProfl != null && url != null && dateString != null) {
            mainTimelineHolder.setImageViewPhotoProfil(context, photoProfl);
            mainTimelineHolder.setTextViewUsername(username);
            mainTimelineHolder.setImageViewPhoto(getActivity(), url);

            mainTimelineHolder.setTextViewDate(dateString);
        }
    }

}
