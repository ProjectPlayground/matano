package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.api.APIUtilisateur;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

public class UpdateContact extends AppCompatActivity implements IUtilisateur {
    private EditText edtUpdateTelephone;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Utilisateur utilisateur;
    private APIUtilisateur apiUtilisateur;
    private Utilisateur utilisateurUpdate;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiUtilisateur = new APIUtilisateur(this, this);

        utilisateur = utilisateurLocalStore.getUtilisateur();

        edtUpdateTelephone = (EditText) findViewById(R.id.edtUpdateTelephone);
        edtUpdateTelephone.setText(utilisateur.getTelephone());
        edtUpdateTelephone.setHint(utilisateur.getTelephone());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }
    }

    public void updateContact(View view) {
        Log.e("e", "updateContact");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtUpdateTelephone.getWindowToken(), 0);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        utilisateurUpdate = utilisateur;

        utilisateurUpdate.setTelephone(edtUpdateTelephone.getText().toString());

        apiUtilisateur.doUpdate(utilisateurUpdate);
    }


    @Override
    public void responseUpdate(String response) {
        progress.hide();
        if (response == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
        } else {
            if (response.equals("0")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_update), Toast.LENGTH_SHORT).show();
            } else if (response.equals("1")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.ok_update), Toast.LENGTH_SHORT).show();
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
