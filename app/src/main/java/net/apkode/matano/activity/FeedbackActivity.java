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
import net.apkode.matano.api.APIFeedback;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.interfaces.IFeedback;
import net.apkode.matano.model.Utilisateur;

public class FeedbackActivity extends AppCompatActivity implements IFeedback {
    private UtilisateurLocalStore utilisateurLocalStore;
    private EditText edtFeedback;
    private Utilisateur utilisateur;
    private APIFeedback apiFeedback;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        utilisateurLocalStore = new UtilisateurLocalStore(this);
        apiFeedback = new APIFeedback(this, this);

        utilisateur = utilisateurLocalStore.getUtilisateur();

        edtFeedback = (EditText) findViewById(R.id.edtFeedback);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!utilisateurLocalStore.isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
            finish();
        }
    }

    public void sendFeedback(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtFeedback.getWindowToken(), 0);
        String result = null;

        String feedback = edtFeedback.getText().toString();

        if (feedback.equals("")) {
            edtFeedback.setError("Required");
            result = "error";
        }

        if (result == null) {
            apiFeedback.sendFeedback(utilisateur, feedback);
            progress = new ProgressDialog(this);
            progress.setMessage("Loading... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
        }

    }

    @Override
    public void getResponse(String response) {
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
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_send_feedback), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else if (response.equals("1")) {
                try {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.success_send_feedback), Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }
}
