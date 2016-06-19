package net.apkode.matano.api;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBParticipant;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfac.IParticipant;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.Participant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIParticipant {
    private static final String COLUMN_ID = "id";
    private final static String url = "http://niameyzze.apkode.net/participants.php?id=";
    private IParticipant iParticipant;
    private List<Participant> participants;
    private DBParticipant dbParticipant;

    public APIParticipant(IParticipant reference, Context ctx) {
        iParticipant = reference;
        participants = new ArrayList<>();
        dbParticipant = new DBParticipant(ctx);
    }

    public void getData(Event event) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,

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
                        iParticipant.getResponse(participants);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

}
