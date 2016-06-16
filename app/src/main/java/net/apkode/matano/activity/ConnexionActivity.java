package net.apkode.matano.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.apkode.matano.R;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.helper.UtilisateurLocalStore;
import net.apkode.matano.model.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String url = "http://niameyzze.apkode.net/connexion.php";
    private EditText edtTelephone;
    private EditText edtPassword;
    private UtilisateurLocalStore utilisateurLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

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

        utilisateurLocalStore = new UtilisateurLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        String result = null;

        Integer id = v.getId();
        switch (id) {
            case R.id.btnConnexion:
                String telephone = edtTelephone.getText().toString();
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
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Connexion... ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.hide();
                        if (response.equals("1")) {
                            utilisateurLocalStore.storeUtilisateur(new Utilisateur(utilisateur.getTelephone(), utilisateur.getPassword()));
                            utilisateurLocalStore.setUtilisateurLogin(true);
                            finish();
                            startActivity(new Intent(getApplicationContext(), Launch.class));
                        } else if (response.equals("0")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_connexion), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.hide();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_reseau), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("telephone", utilisateur.getTelephone());
                map.put("password", utilisateur.getPassword());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);

    }
}
