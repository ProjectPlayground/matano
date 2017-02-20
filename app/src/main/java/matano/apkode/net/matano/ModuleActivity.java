package matano.apkode.net.matano;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;

import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.ModuleHolder;
import matano.apkode.net.matano.model.Programme;

public class ModuleActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseUser currentUser = null;
    private String currentUserUid;

    private String incomeEventUid;
    private String incomeModule;

    private FbDatabase fbDatabase;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Programme, ModuleHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fbDatabase = new FbDatabase();

        incomeEventUid = getIntent().getStringExtra(Utils.ARG_EVENT_UID);
        incomeModule = getIntent().getStringExtra(Utils.ARG_MODULE);


        if (incomeEventUid == null || incomeModule == null) {
            finish();
        }


        createAuthStateListener();

        getSupportActionBar().setTitle(incomeModule.toUpperCase());

        LinearLayoutManager manager = new LinearLayoutManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cleanup();
        }
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
                    createView();
                }
            }
        };
    }

    private void createView() {
        Query query = fbDatabase.getRefEvent(incomeEventUid).child(incomeModule);
        query.keepSynced(true);

        adapter = new FirebaseRecyclerAdapter<Programme, ModuleHolder>(Programme.class, R.layout.card_module, ModuleHolder.class, query) {

            @Override
            protected void populateViewHolder(ModuleHolder moduleHolder, Programme programme, int position) {

                String content = programme.getContent();
                String image = programme.getImage();

                moduleHolder.setTextViewTitle(programme.getTitle());
                moduleHolder.setTextViewSubTitle(programme.getSubTitle());

                if (content != null) {
                    moduleHolder.setTextViewContent(programme.getContent());
                }

                if (image != null) {
                    moduleHolder.setImageViewPhoto(getApplicationContext(), image);
                }


            }
        };

        recyclerView.setAdapter(adapter);
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
