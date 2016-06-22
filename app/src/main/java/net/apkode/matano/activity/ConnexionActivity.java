package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.api.APIUtilisateur;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener, IUtilisateur {
    private EditText edtTelephone;
    private EditText edtPassword;
    private UtilisateurLocalStore utilisateurLocalStore;
    private APIUtilisateur apiUtilisateur;
    private ProgressDialog progress;
    private String telephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiUtilisateur = new APIUtilisateur(this, getApplicationContext());

        edtTelephone = (EditText) findViewById(R.id.edtTelephone);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");
        TextView nom = (TextView) findViewById(R.id.nom);
        assert nom != null;
        nom.setTypeface(custom_font);

        Button btnConnexion = (Button) findViewById(R.id.btnConnexion);
        assert btnConnexion != null;
        btnConnexion.setOnClickListener(this);

        Button btnInscription = (Button) findViewById(R.id.btnInscription);
        assert btnInscription != null;
        btnInscription.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String result = null;

        Integer id = v.getId();
        switch (id) {
            case R.id.btnConnexion:
                telephone = edtTelephone.getText().toString();
                String password = edtPassword.getText().toString();

                if (telephone.equals("")) {
                    edtTelephone.setError("Required");
                    result = "error";
                }

                if (password.equals("")) {
                    edtPassword.setError("Required");
                    result = "error";
                }

                if (result == null) {
                    connexion(new Utilisateur(telephone, password));
                }
                break;
            case R.id.btnInscription:
                finish();
                startActivity(new Intent(getApplicationContext(), InscriptionActivity.class));
                break;
        }
    }

    private void connexion(final Utilisateur utilisateur) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);

        progress = new ProgressDialog(this);
        progress.setMessage("Connexion... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        apiUtilisateur.doConnexion(utilisateur);
    }

    @Override
    public void responseUpdate(String response) {

    }

    @Override
    public void responseInscription(String response) {

    }

    @Override
    public void responseConnexion(String response) {
        progress.hide();
        if (response == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
        } else {
            if (response.equals("0")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
            } else if (response.equals("1")) {
                apiUtilisateur.getUtilisateur(telephone);
            }
        }
    }

    @Override
    public void responseGetUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.error_connexion), Toast.LENGTH_LONG).show();
        } else {
            utilisateurLocalStore.clearUtilisateur();
            utilisateurLocalStore.storeUtilisateur(utilisateur);
            utilisateurLocalStore.setUtilisateurLogin(true);
            finish();
            startActivity(new Intent(getApplicationContext(), Launch.class));
        }
    }

}
