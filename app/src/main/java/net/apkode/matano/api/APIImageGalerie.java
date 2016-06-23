package net.apkode.matano.api;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import net.apkode.matano.db.DBImageGalerie;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IImageGalerie;
import net.apkode.matano.model.Evenement;
import net.apkode.matano.model.ImageGalerie;
import net.apkode.matano.model.Utilisateur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIImageGalerie {
    private static final String COLUMN_ID = "id";
    //private final static String url = "http://niameyzze.apkode.net/image-galeries.php?id=";
    private final static String url = "https://matano-api.herokuapp.com/images/evenements/";
    private final static String urlImage = "https://matano-api.herokuapp.com/images";
    private IImageGalerie iImageGalerie;
    private List<ImageGalerie> imageGaleries;
    private DBImageGalerie dbImageGalerie;

    public APIImageGalerie(IImageGalerie reference, Context ctx) {
        iImageGalerie = reference;
        imageGaleries = new ArrayList<>();
        dbImageGalerie = new DBImageGalerie(ctx);
    }

    public void getData(Evenement evenement) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + evenement.getId(), null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                imageGaleries.add(new ImageGalerie(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nom"),
                                        jsonObject.getString("prenom"),
                                        jsonObject.getString("telephone"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("imagegalerie")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        iImageGalerie.getResponse(imageGaleries);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iImageGalerie.getResponse(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

    public void sendImageGalerie(final String imageGalerie, final Evenement evenement, final Utilisateur utilisateur) {

        StringRequest request = new StringRequest(Request.Method.POST, urlImage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iImageGalerie.responseSendImageGalerie(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iImageGalerie.responseSendImageGalerie(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                map.put("imagegalerie", imageGalerie);
                map.put("evenement.id", evenement.getId().toString());
                map.put("utilisateur.id", utilisateur.getId().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
