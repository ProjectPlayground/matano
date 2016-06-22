package net.apkode.matano.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBActualite;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IActualite;
import net.apkode.matano.model.Actualite;
import net.apkode.matano.model.Evennement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class APIActualite {
    private static final String COLUMN_ID = "id";
    //private final static String url = "http://niameyzze.apkode.net/actualites.php?id=";
    private final static String url = "https://matano-api.herokuapp.com/actualites/evenements/";
    private IActualite iActualite;
    private List<Actualite> actualites;
    private DBActualite dbActualite;

    public APIActualite(IActualite reference, Context ctx) {
        iActualite = reference;
        dbActualite = new DBActualite(ctx);
        actualites = new ArrayList<>();
    }

    public void getData(Evennement evennement) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + evennement.getId(), null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                actualites.add(new Actualite(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("jour"),
                                        jsonObject.getString("actualite")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        iActualite.getResponse(actualites);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        actualites = null;
                        iActualite.getResponse(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }
}
