package net.apkode.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EvennementAdapter;
import net.apkode.matano.api.APIEvennement;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IEvennement;
import net.apkode.matano.model.Evennement;
import net.apkode.matano.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class MesEvennementsActivity extends AppCompatActivity implements IEvennement {
    private UtilisateurLocalStore utilisateurLocalStore;
    private APIEvennement apiEvennement;
    private RecyclerView recyclerView;
    private List<Evennement> evennementsListe = new ArrayList<>();
    private ProgressBar progressBar;
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_evennements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.loading);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiEvennement = new APIEvennement(this, getApplicationContext());

        utilisateur = utilisateurLocalStore.getUtilisateur();

        apiEvennement.getMyData(utilisateur.getId());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EvennementAdapter(evennementsListe));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }
    }

    @Override
    public void getResponses(List<Evennement> evennements) {

    }

    @Override
    public void getResponse(List<Evennement> evennements) {
        if (evennements == null) {
            apiEvennement.getMyData(utilisateur.getId());
        } else {
            evennementsListe = evennements;
            progressBar.setVisibility(View.GONE);
            recyclerView.setAdapter(new EvennementAdapter(evennementsListe));
        }
    }
}
