package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.apkode.matano.R;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Utilisateur;

public class UpdateProfil extends AppCompatActivity {
    private EditText edtUpdateNom;
    private EditText edtUpdatePrenom;
    private EditText edtUpdateEmail;
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Utilisateur utilisateur = utilisateurLocalStore.getUtilisateur();

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

}
