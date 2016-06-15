package net.apkode.matano.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import net.apkode.matano.model.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class InscriptionActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtTelephone;
    private EditText edtNom;
    private EditText edtPrenom;
    private EditText edtPassword;
    private EditText edtPasswordConfirme;
    private final static String url = "http://niameyzze.apkode.net/inscription.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        edtTelephone = (EditText)findViewById(R.id.edtTelephone);
        edtNom = (EditText)findViewById(R.id.edtNom);
        edtPrenom = (EditText)findViewById(R.id.edtPrenom);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtPasswordConfirme = (EditText)findViewById(R.id.edtPasswordConfirme);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "font/Bauhaus-93_6274.ttf");
        TextView nom = (TextView) findViewById(R.id.nom);
        assert nom != null;
        nom.setTypeface(custom_font);

        Button btnInscription = (Button) findViewById(R.id.btnInscription);
        assert btnInscription != null;
        btnInscription.setOnClickListener(this);

        Button btnConnexion = (Button)findViewById(R.id.btnConnexion);
        assert btnConnexion != null;
        btnConnexion.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        String result = null;

        Integer id = v.getId();
        switch (id){
            case R.id.btnInscription:
                String telephone = edtTelephone.getText().toString();
                String nom = edtNom.getText().toString();
                String prenom = edtPrenom.getText().toString();
                String password = edtPassword.getText().toString();
                String passwordConfirme = edtPasswordConfirme.getText().toString();

                if(telephone.equals("")){
                    edtTelephone.setError("Required");
                    result = "error";
                }

                if(nom.equals("")){
                    edtNom.setError("Required");
                    result = "error";
                }

                if(prenom.equals("")){
                    edtPrenom.setError("Required");
                    result = "error";
                }

                if(password.equals("")){
                    edtPassword.setError("Required");
                    result = "error";
                }

                if(passwordConfirme.equals("")){
                    edtPasswordConfirme.setError("Required");
                    result = "error";
                }

                if(!password.equals(passwordConfirme)){
                    edtPassword.setError("Incorrect");
                    edtPasswordConfirme.setError("Incorrect");
                    result = "error";
                }

                if(result == null){
                    inscription(new Utilisateur(nom, prenom,  telephone, password));
                }
                break;
            case R.id.btnConnexion:
                finish();
                startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
        }


    }

    private void inscription(final Utilisateur utilisateur){

       final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Inscription... ");
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
                        if(response.equals("1")){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ConnexionActivity.class));
                        }else if (response.equals("0")){
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_inscription), Toast.LENGTH_SHORT).show();
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
                map.put("nom", utilisateur.getNom());
                map.put("prenom", utilisateur.getPrenom());
                map.put("telephone", utilisateur.getTelephone());
                map.put("password", utilisateur.getPassword());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }


}
