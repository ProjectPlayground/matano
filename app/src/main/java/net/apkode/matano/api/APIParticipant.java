package net.apkode.matano.api;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import net.apkode.matano.db.DBParticipant;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IParticipant;
import net.apkode.matano.model.Evennement;
import net.apkode.matano.model.Participant;
import net.apkode.matano.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIParticipant {
    private static final String COLUMN_ID = "id";
    // private final static String url = "http://niameyzze.apkode.net/participants.php?id=";
    private final static String url = "https://matano-api.herokuapp.com/participants/evenements/";
    private final static String urlDeleteParticipant = "http://matano-api.herokuapp.com/participants/evenements/";
    private final static String urlCreateParticipant = "https://matano-api.herokuapp.com/participants";
    private IParticipant iParticipant;
    private List<Participant> participants;
    private DBParticipant dbParticipant;

    public APIParticipant(IParticipant reference, Context ctx) {
        iParticipant = reference;
        participants = new ArrayList<>();
        dbParticipant = new DBParticipant(ctx);
    }

    /**
     * @param evennement
     */
    public void getData(Evennement evennement) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + evennement.getId(), null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                participants.add(new Participant(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nom"),
                                        jsonObject.getString("prenom"),
                                        jsonObject.getString("telephone"),
                                        jsonObject.getString("image")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        iParticipant.getResponse(participants);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iParticipant.getResponse(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     *
     * @param evennement
     * @param utilisateur
     */
    public void createPartiticpant(final Evennement evennement, final Utilisateur utilisateur) {
        StringRequest request = new StringRequest(Request.Method.POST, urlCreateParticipant,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iParticipant.sendResponseCreateParticipant(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iParticipant.sendResponseCreateParticipant(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                map.put("evenement.id", evennement.getId().toString());
                map.put("utilisateur.id", utilisateur.getId().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * @param evennement
     * @param utilisateur
     */
    public void deletePartiticpant(final Evennement evennement, final Utilisateur utilisateur) {

        StringRequest request = new StringRequest(Request.Method.GET, urlDeleteParticipant + evennement.getId() + "/utilisateurs/" + utilisateur.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iParticipant.sendResponseDeleteParticipant(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iParticipant.sendResponseDeleteParticipant(null);
                    }
                });

        AppController.getInstance().addToRequestQueue(request);
    }

}
