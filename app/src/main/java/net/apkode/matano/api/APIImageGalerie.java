package net.apkode.matano.api;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBImageGalerie;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IImageGalerie;
import net.apkode.matano.model.Event;
import net.apkode.matano.model.ImageGalerie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIImageGalerie {
    private static final String COLUMN_ID = "id";
    private final static String url = "http://niameyzze.apkode.net/image-galeries.php?id=";
    private IImageGalerie iImageGalerie;
    private List<ImageGalerie> imageGaleries;
    private DBImageGalerie dbImageGalerie;

    public APIImageGalerie(IImageGalerie reference, Context ctx) {
        iImageGalerie = reference;
        imageGaleries = new ArrayList<>();
        dbImageGalerie = new DBImageGalerie(ctx);
    }

    public void getData(Event event) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,

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
                                        jsonObject.getString("image")
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
                        iImageGalerie.getResponse(imageGaleries);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

}
