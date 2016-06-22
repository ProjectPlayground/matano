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
import net.apkode.matano.model.Evennement;
import net.apkode.matano.model.ImageGalerie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIImageGalerie {
    private static final String COLUMN_ID = "id";
    //private final static String url = "http://niameyzze.apkode.net/image-galeries.php?id=";
    private final static String url = "https://matano-api.herokuapp.com/images/evenements/";
    private final static String urlImage = "http://niameyzze.apkode.net/send-commentaire.php";
    private IImageGalerie iImageGalerie;
    private List<ImageGalerie> imageGaleries;
    private DBImageGalerie dbImageGalerie;

    public APIImageGalerie(IImageGalerie reference, Context ctx) {
        iImageGalerie = reference;
        imageGaleries = new ArrayList<>();
        dbImageGalerie = new DBImageGalerie(ctx);
    }

    public void getData(Evennement evennement) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + evennement.getId(), null,

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

    public void sendImage(final File image, final String telephone) {
        StringRequest request = new StringRequest(Request.Method.POST, urlImage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iImageGalerie.sendResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iImageGalerie.sendResponse(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                // map.put("image", image);
                map.put("telephone", telephone);
                map.put("jour", new Date().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
