package net.apkode.matano.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBCommentaire;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.ICommentaire;
import net.apkode.matano.model.Commentaire;
import net.apkode.matano.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APICommentaire {
    private static final String COLUMN_ID = "id";
    private final static String url = "http://niameyzze.apkode.net/commentaires.php?id=";
    private ICommentaire iCommentaire;
    private List<Commentaire> commentaires;
    private DBCommentaire dbCommentaire;

    public APICommentaire(ICommentaire reference, Context ctx) {
        iCommentaire = reference;
        commentaires = new ArrayList<>();
        dbCommentaire = new DBCommentaire(ctx);
    }

    public void getData(Event event) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + event.getId(), null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                commentaires.add(new Commentaire(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nom"),
                                        jsonObject.getString("prenom"),
                                        jsonObject.getString("telephone"),
                                        jsonObject.getString("jour"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("commentaire")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("e", "e " + e.getMessage());
                            }
                        }
                        iCommentaire.getResponse(commentaires);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iCommentaire.getResponse(commentaires);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }
}
