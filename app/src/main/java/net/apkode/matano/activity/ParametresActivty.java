package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import net.apkode.matano.R;
import net.apkode.matano.api.APIUtilisateur;
import net.apkode.matano.db.DBEvenement;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

public class ParametresActivty extends AppCompatActivity implements IUtilisateur {
    private UtilisateurLocalStore utilisateurLocalStore;
    private APIUtilisateur apiUtilisateur;
    private ProgressDialog progress;
    private DBEvenement dbEvenement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiUtilisateur = new APIUtilisateur(this, this);
        dbEvenement = new DBEvenement(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            finish();
        }
    }

    public void modifierPassword(View view) {
        startActivity(new Intent(this, UpdatePassword.class));
    }

    public void supprimerDonnees(View view) {
        dbEvenement.deleteAll();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.delete_database), Toast.LENGTH_SHORT).show();
    }

    public void supprimerCompte(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.supprimer_compte));

        alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                progress = new ProgressDialog(getApplicationContext());
                progress.setMessage("Loading... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();

                apiUtilisateur.deleteCompte(utilisateurLocalStore.getUtilisateur());
            }
        });

        alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void responseUpdate(String response) {

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
        try {
            progress.hide();
        } catch (Exception e) {
            e.getMessage();
        }

        if (response == null) {
            try {
                Toast.makeText(getApplicationContext(), getString(R.string.error_reseau), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            if (response.equals("0")) {
                try {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_supprimer_compte), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                utilisateurLocalStore.clearUtilisateur();
                finish();
            }
        }
    }

}
