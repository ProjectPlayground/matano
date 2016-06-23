package net.apkode.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.adapter.EvenementAdapter;
import net.apkode.matano.api.APIEvenement;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IEvenement;
import net.apkode.matano.model.Evenement;
import net.apkode.matano.model.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class MesEvenementsActivity extends AppCompatActivity implements IEvenement {
    private UtilisateurLocalStore utilisateurLocalStore;
    private APIEvenement apiEvenement;
    private RecyclerView recyclerView;
    private List<Evenement> evennementsListe = new ArrayList<>();
    private ProgressBar progressBar;
    private Utilisateur utilisateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_evenements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.loading);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiEvenement = new APIEvenement(this, getApplicationContext());

        utilisateur = utilisateurLocalStore.getUtilisateur();

        apiEvenement.getMyData(utilisateur.getId());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EvenementAdapter(evennementsListe));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            finish();
        }
    }

    @Override
    public void getResponses(List<Evenement> evenements) {

    }

    @Override
    public void getResponsesCulture(List<Evenement> evenements) {

    }

    @Override
    public void getResponsesEducation(List<Evenement> evenements) {

    }

    @Override
    public void getResponsesSport(List<Evenement> evenements) {

    }

    @Override
    public void getResponse(List<Evenement> evenements) {

        if (evenements == null) {
            apiEvenement.getMyData(utilisateur.getId());
        } else {
            try{
                progressBar.setVisibility(View.GONE);
            }catch (Exception e){
                e.getMessage();
            }
            if (evenements.size() == 0) {
                try{
                    Toast.makeText(getApplicationContext(), getString(R.string.error_mes_evenements), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.getMessage();
                }
            } else {
                evennementsListe = evenements;
                try{
                    recyclerView.setAdapter(new EvenementAdapter(evennementsListe));
                }catch (Exception e){
                    e.getMessage();
                }
            }
        }
    }
}
