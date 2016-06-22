package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener, IUtilisateur {
    private EditText edtTelephone;
    private EditText edtNom;
    private EditText edtPrenom;
    private EditText edtPassword;
    private EditText edtPasswordConfirme;
    private APIUtilisateur apiUtilisateur;
    private ProgressDialog progress;
    private UtilisateurLocalStore utilisateurLocalStore;
    private Utilisateur utilisateurNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        apiUtilisateur = new APIUtilisateur(this, this);
        utilisateurLocalStore = new UtilisateurLocalStore(getApplicationContext());

        edtTelephone = (EditText) findViewById(R.id.edtTelephone);
        edtNom = (EditText) findViewById(R.id.edtNom);
        edtPrenom = (EditText) findViewById(R.id.edtPrenom);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPasswordConfirme = (EditText) findViewById(R.id.edtPasswordConfirme);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");
        TextView nom = (TextView) findViewById(R.id.nom);
        assert nom != null;
        nom.setTypeface(custom_font);

        Button btnInscription = (Button) findViewById(R.id.btnInscription);
        assert btnInscription != null;
        btnInscription.setOnClickListener(this);

        Button btnConnexion = (Button) findViewById(R.id.btnConnexion);
        assert btnConnexion != null;
        btnConnexion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String result = null;

        Integer id = v.getId();
        switch (id) {
            case R.id.btnInscription:
                String telephone = edtTelephone.getText().toString();
                String nom = edtNom.getText().toString();
                String prenom = edtPrenom.getText().toString();
                String password = edtPassword.getText().toString();
                String passwordConfirme = edtPasswordConfirme.getText().toString();

                if (telephone.equals("")) {
                    edtTelephone.setError("Required");
                    result = "error";
                }

                if (nom.equals("")) {
                    edtNom.setError("Required");
                    result = "error";
                }

                if (prenom.equals("")) {
                    edtPrenom.setError("Required");
                    result = "error";
                }

                if (password.equals("")) {
                    edtPassword.setError("Required");
                    result = "error";
                }

                if (passwordConfirme.equals("")) {
                    edtPasswordConfirme.setError("Required");
                    result = "error";
                }

                if (!password.equals(passwordConfirme)) {
                    edtPassword.setError("Incorrect");
                    edtPasswordConfirme.setError("Incorrect");
                    result = "error";
                }

                if (result == null) {
                    utilisateurNew = new Utilisateur(nom, prenom, telephone, password);
                    inscription(utilisateurNew);
                }
                break;
            case R.id.btnConnexion:
                finish();
                startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }


    }

    private void inscription(final Utilisateur utilisateur) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtPassword.getWindowToken(), 0);

        progress = new ProgressDialog(this);
        progress.setMessage("Inscription... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        apiUtilisateur.doInscription(utilisateur);

    }


    @Override
    public void responseUpdate(String response) {

    }

    @Override
    public void responseInscription(String response) {
        progress.hide();
        if (response == null) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
        } else {
            if (response.equals("0")) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_inscription), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("e", "id " + response);
                utilisateurNew.setId(Integer.parseInt(response));
                utilisateurLocalStore.storeUtilisateur(utilisateurNew);
                finish();
                startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            }
        }
    }

    @Override
    public void responseConnexion(String response) {

    }

    @Override
    public void responseGetUtilisateur(Utilisateur utilisateur) {

    }
}
