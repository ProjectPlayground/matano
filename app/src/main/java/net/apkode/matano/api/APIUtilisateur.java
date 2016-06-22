package net.apkode.matano.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IUtilisateur;
import net.apkode.matano.model.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class APIUtilisateur {
    private final static String urlInscription = "https://matano-api.herokuapp.com/inscription";
    private final static String urlConnexion = "https://matano-api.herokuapp.com/connexion";
    private final static String urlUpdate = "https://matano-api.herokuapp.com/utilisateurs/update";
    private IUtilisateur iUtilisateur;

    public APIUtilisateur(IUtilisateur reference, Context ctx) {
        iUtilisateur = reference;
    }


    public void doInscription(final Utilisateur utilisateur) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlInscription,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iUtilisateur.responseInscription(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iUtilisateur.responseInscription(null);
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

    public void doConnexion(final Utilisateur utilisateur) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                urlConnexion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iUtilisateur.responseConnexion(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iUtilisateur.responseConnexion(null);
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

    public void doUpdate(final Utilisateur utilisateur) {
        StringRequest request = new StringRequest(Request.Method.POST, urlUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iUtilisateur.responseUpdate(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iUtilisateur.responseUpdate(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("id", utilisateur.getId().toString());
                map.put("nom", utilisateur.getNom());
                map.put("prenom", utilisateur.getPrenom());
                map.put("telephone", utilisateur.getTelephone());
                map.put("password", utilisateur.getPassword());
                map.put("email", utilisateur.getEmail());
                map.put("presentation", utilisateur.getPresentation());
                map.put("image", utilisateur.getImage());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
