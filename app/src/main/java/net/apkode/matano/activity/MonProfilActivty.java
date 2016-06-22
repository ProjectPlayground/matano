package net.apkode.matano.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import net.apkode.matano.R;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Utilisateur;

public class MonProfilActivty extends AppCompatActivity {
    private UtilisateurLocalStore utilisateurLocalStore;
    private TextView txtUpdateTelephone;
    private TextView txtUpdateNom;
    private TextView txtUpdatePrenom;
    private TextView txtUpdateEmail;
    private TextView txtUpdatePresentation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        txtUpdateTelephone = (TextView) findViewById(R.id.txtUpdateTelephone);
        txtUpdateNom = (TextView) findViewById(R.id.txtUpdateNom);
        txtUpdatePrenom = (TextView) findViewById(R.id.txtUpdatePrenom);
        txtUpdateEmail = (TextView) findViewById(R.id.txtUpdateEmail);
        txtUpdatePresentation = (TextView) findViewById(R.id.txtUpdatePresentation);

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
    protected void onResume() {
        super.onResume();

        Utilisateur utilisateur = utilisateurLocalStore.getUtilisateur();

        txtUpdateTelephone.setText(utilisateur.getTelephone());
        txtUpdateNom.setText(utilisateur.getNom());
        txtUpdatePrenom.setText(utilisateur.getPrenom());
        txtUpdateEmail.setText(utilisateur.getEmail());
        txtUpdatePresentation.setText(utilisateur.getPresentation());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void startUpdateContact(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateContact.class));
    }

    public void startUpdateProfil(View view) {
        startActivity(new Intent(getApplicationContext(), UpdateProfil.class));
    }

    public void startUpdatePresentation(View view) {
        startActivity(new Intent(getApplicationContext(), UpdatePresentation.class));
    }
}
