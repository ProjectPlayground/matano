package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.api.APIUtilisateur;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

public class UpdateProfil extends AppCompatActivity implements IUtilisateur {
    private EditText edtUpdateNom;
    private EditText edtUpdatePrenom;
    private EditText edtUpdateEmail;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Utilisateur utilisateur;
    private APIUtilisateur apiUtilisateur;
    private Utilisateur utilisateurUpdate;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiUtilisateur = new APIUtilisateur(this, this);

        utilisateur = utilisateurLocalStore.getUtilisateur();

        edtUpdateNom = (EditText) findViewById(R.id.edtUpdateNom);
        edtUpdatePrenom = (EditText) findViewById(R.id.edtUpdatePrenom);
        edtUpdateEmail = (EditText) findViewById(R.id.edtUpdateEmail);

        edtUpdateNom.setText(utilisateur.getNom());
        edtUpdatePrenom.setText(utilisateur.getPrenom());
        edtUpdateEmail.setText(utilisateur.getEmail());

        edtUpdateNom.setHint(utilisateur.getNom());
        edtUpdatePrenom.setHint(utilisateur.getPrenom());
        edtUpdateEmail.setHint(utilisateur.getEmail());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }
    }

    public void updateProfil(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUpdateEmail.getWindowToken(), 0);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        utilisateurUpdate = utilisateur;

        utilisateurUpdate.setNom(edtUpdateNom.getText().toString());
        utilisateurUpdate.setPrenom(edtUpdatePrenom.getText().toString());
        utilisateurUpdate.setEmail(edtUpdateEmail.getText().toString());

        apiUtilisateur.doUpdate(utilisateurUpdate);
    }

    @Override
    public void responseUpdate(String response) {
        try {
            progress.hide();
        }catch (Exception e){
            e.getMessage();
        }
        if (response == null) {
            try{
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.getMessage();
            }
        } else {
            if (response.equals("0")) {
                try{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_update), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.getMessage();
                }
            } else if (response.equals("1")) {
               try {
                   Toast.makeText(getApplicationContext(), getResources().getString(R.string.ok_update), Toast.LENGTH_SHORT).show();
               }catch (Exception e){
                   e.getMessage();
               }
                utilisateurLocalStore.clearUtilisateur();
                utilisateurLocalStore.storeUtilisateur(utilisateurUpdate);
                utilisateurLocalStore.setUtilisateurLogin(true);
            }
        }
    }

    @Override
    public void responseInscription(String response) {

    }

    @Override
    public void responseConnexion(String response) {

    }

    @Override
    public void responseGetUtilisateur(Utilisateur utilisateur) {

    }
}
