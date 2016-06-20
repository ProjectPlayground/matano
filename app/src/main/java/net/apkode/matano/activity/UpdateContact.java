package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.apkode.matano.R;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Utilisateur;

public class UpdateContact extends AppCompatActivity {
    private EditText edtUpdateTelephone;
    private UtilisateurLocalStore utilisateurLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        Utilisateur utilisateur = utilisateurLocalStore.getUtilisateur();

        edtUpdateTelephone = (EditText) findViewById(R.id.edtUpdateTelephone);
        edtUpdateTelephone.setText(utilisateur.getTelephone());
        edtUpdateTelephone.setHint(utilisateur.getTelephone());



    }

}
