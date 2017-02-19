package matano.apkode.net.matano;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import matano.apkode.net.matano.config.FbDatabase;
import matano.apkode.net.matano.config.Utils;
import matano.apkode.net.matano.holder.ModuleHolder;
import matano.apkode.net.matano.model.Programme;

public class ModuleActivity extends AppCompatActivity {
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
        } else {

            getSupportActionBar().setTitle(incomeModule.toUpperCase());

            LinearLayoutManager manager = new LinearLayoutManager(this);

            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(manager);

            Query query = fbDatabase.getRefEvent(incomeEventUid).child(incomeModule);

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


    }

}