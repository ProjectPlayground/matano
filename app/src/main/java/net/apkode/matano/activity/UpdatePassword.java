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

public class UpdatePassword extends AppCompatActivity implements IUtilisateur {
    private EditText edtUpdatePasswordAncien;
    private EditText edtUpdatePasswordNouveau;
    private EditText edtUpdatePasswordNouveauConfirme;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Utilisateur utilisateur;
    private APIUtilisateur apiUtilisateur;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiUtilisateur = new APIUtilisateur(this, this);


        edtUpdatePasswordAncien = (EditText) findViewById(R.id.edtUpdatePasswordAncien);
        edtUpdatePasswordNouveau = (EditText) findViewById(R.id.edtUpdatePasswordNouveau);
        edtUpdatePasswordNouveauConfirme = (EditText) findViewById(R.id.edtUpdatePasswordNouveauConfirme);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            finish();
        }
    }

    public void updatePassword(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUpdatePasswordNouveauConfirme.getWindowToken(), 0);

        String result = null;

        String passwordAncien = edtUpdatePasswordAncien.getText().toString();
        String passwordNouveau = edtUpdatePasswordNouveau.getText().toString();
        String passwordNouveauConfirme = edtUpdatePasswordNouveauConfirme.getText().toString();


        if (passwordAncien.equals("")) {
            edtUpdatePasswordAncien.setError("Required");
            result = "error";
        }

        if (passwordNouveau.equals("")) {
            edtUpdatePasswordNouveau.setError("Required");
            result = "error";
        }

        if (passwordNouveauConfirme.equals("")) {
            edtUpdatePasswordNouveauConfirme.setError("Required");
            result = "error";
        }

        if (!passwordNouveau.equals(passwordNouveauConfirme)) {
            edtUpdatePasswordNouveau.setError("Incorrect");
            edtUpdatePasswordNouveauConfirme.setError("Incorrect");
            result = "error";
        }

        if (result == null) {
            utilisateur = utilisateurLocalStore.getUtilisateur();
            if (!passwordAncien.equals(utilisateur.getPassword())) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_password), Toast.LENGTH_LONG).show();
            } else {
                utilisateur.setPassword(passwordNouveau);
                apiUtilisateur.doUpdate(utilisateur);
                progress = new ProgressDialog(this);
                progress.setMessage("Loading... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();
            }
        }
    }

    @Override
    public void responseUpdate(String response) {
        try {
            progress.hide();
        } catch (Exception e) {
            e.getMessage();
        }

        if (response == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
        } else {
            if (response.equals("0")) {
                try {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_update_password), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (response.equals("1")) {
                utilisateurLocalStore.clearUtilisateur();
                utilisateurLocalStore.storeUtilisateur(utilisateur);
                utilisateurLocalStore.setUtilisateurLogin(true);
                try {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.update_password), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
                finish();
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

    @Override
    public void responseDeleteCompte(String response) {

    }
}
