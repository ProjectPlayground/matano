package net.apkode.matano.api;

import android.content.Context;
import android.database.Cursor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBEvennement;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IEvennement;
import net.apkode.matano.model.Evennement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class APIEvennement {
    private static final String COLUMN_ID = "id";
    // private final static String url = "http://niameyzze.apkode.net/event.php";
    private final static String urls = "https://matano-api.herokuapp.com/evenements";
    private final static String url = "https://matano-api.herokuapp.com/evenements/utilisateurs/";
    private IEvennement iEvennement;
    private List<Evennement> evennements;
    private DBEvennement dbEvennement;

    public APIEvennement(IEvennement reference, Context ctx) {
        iEvennement = reference;
        dbEvennement = new DBEvennement(ctx);
        evennements = new ArrayList<>();
    }

    /**
     * get data from server
     *
     * @return
     */
    public void getData() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urls, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                evennements.add(new Evennement(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("categorie"),
                                        jsonObject.getString("rubrique"),
                                        jsonObject.getString("titre"),
                                        jsonObject.getString("tarif"),
                                        jsonObject.getString("lieu"),
                                        jsonObject.getString("presentation"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("horaire"),
                                        jsonObject.getString("lien"),
                                        jsonObject.getString("jour"),
                                        jsonObject.getString("video"),
                                        jsonObject.getString("imagefull")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        iEvennement.getResponses(evennements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvennement.getResponses(evennements);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);

    }

    public void getMyData(Integer id) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + id, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                evennements.add(new Evennement(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("categorie"),
                                        jsonObject.getString("rubrique"),
                                        jsonObject.getString("titre"),
                                        jsonObject.getString("tarif"),
                                        jsonObject.getString("lieu"),
                                        jsonObject.getString("presentation"),
                                        jsonObject.getString("image"),
                                        jsonObject.getString("horaire"),
                                        jsonObject.getString("lien"),
                                        jsonObject.getString("jour"),
                                        jsonObject.getString("video"),
                                        jsonObject.getString("imagefull")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        iEvennement.getResponse(null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvennement.getResponse(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Match data base de donnée avec server
     *
     * @param eventsServer
     */
    public void compareAndCharge(List<Evennement> eventsServer) {
        // Log.e("e", "eventsServer "+eventsServer.size());
        Cursor cursor = dbEvennement.getDatasCursor();
        Integer lastIdLocal;
        if (cursor.getCount() == 0) {
            lastIdLocal = 0;
        } else {
            cursor.moveToLast();
            lastIdLocal = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }

        //Log.e("e","local "+cursor.getCount());

        Integer lastIdServer;
        if (eventsServer.size() == 0) {
            lastIdServer = 0;
        } else {
            lastIdServer = eventsServer.get(eventsServer.size() - 1).getId();
        }

        Integer difference = lastIdServer - lastIdLocal;

        for (int i = 0; difference > 0; i++) {
            int j = lastIdLocal + i + 1;

            for (Evennement evennement : eventsServer) {
                if (evennement.getId() == j) {
                    dbEvennement.createData(evennement.getId(), evennement.getCategorie(), evennement.getRubrique(), evennement.getTitre(), evennement.getTarif(), evennement.getLieu(), evennement.getPresentation(), evennement.getImage(), evennement.getHoraire(), evennement.getLien(), evennement.getJour(), evennement.getVideo(), evennement.getImagefull());
                    difference--;
                }
            }

        }
    }

}
