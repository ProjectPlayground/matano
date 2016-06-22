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
    private final static String urlSuppression = "http://niameyzze.apkode.net/delete-participant.php";
    private final static String urlAjout = "http://niameyzze.apkode.net/send-participant.php";
    private IParticipant iParticipant;
    private List<Participant> participants;
    private DBParticipant dbParticipant;

    public APIParticipant(IParticipant reference, Context ctx) {
        iParticipant = reference;
        participants = new ArrayList<>();
        dbParticipant = new DBParticipant(ctx);
    }

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

    public void sendParticipant(final Evennement evennement, final String telephone, final String status) {

        String url = "";

        if (status.equals("suppression")) {
            url = urlSuppression;
        } else if (status.equals("ajout")) {
            url = urlAjout;
        }

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iParticipant.sendResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iParticipant.sendResponse(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("telephone", telephone);
                map.put("id", evennement.getId().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

}
