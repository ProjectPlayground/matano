package net.apkode.matano.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import net.apkode.matano.R;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Utilisateur;

public class UpdatePresentation extends AppCompatActivity {
    private EditText edtUpdatePresentation;
    private UtilisateurLocalStore utilisateurLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_presentation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);

        edtUpdatePresentation = (EditText) findViewById(R.id.edtUpdatePresentation);

        Utilisateur utilisateur = utilisateurLocalStore.getUtilisateur();

        edtUpdatePresentation.setText(utilisateur.getPresentation());
        edtUpdatePresentation.setHint(utilisateur.getPresentation());
    }

}
