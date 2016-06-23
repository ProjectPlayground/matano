package net.apkode.matano.api;

import android.content.Context;
import android.database.Cursor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBEvenement;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IEvenement;
import net.apkode.matano.model.Evenement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class APIEvenement {
    private static final String COLUMN_ID = "id";
    // private final static String url = "http://niameyzze.apkode.net/event.php";
    private final static String urls = "https://matano-api.herokuapp.com/evenements";
    private final static String urlCategorie = "https://matano-api.herokuapp.com/evenements/categories/";
    private final static String url = "https://matano-api.herokuapp.com/evenements/utilisateurs/";
    private IEvenement iEvenement;
    private List<Evenement> evenements;
    private DBEvenement dbEvenement;

    public APIEvenement(IEvenement reference, Context ctx) {
        iEvenement = reference;
        dbEvenement = new DBEvenement(ctx);
        evenements = new ArrayList<>();
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
                                evenements.add(new Evenement(
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
                        iEvenement.getResponses(evenements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvenement.getResponses(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);

    }

    /**
     *
     */
    public void getDataCulture() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlCategorie + "Culture", null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                evenements.add(new Evenement(
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
                        iEvenement.getResponsesCulture(evenements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvenement.getResponsesCulture(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);

    }

    /**
     *
     */
    public void getDataEducation() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlCategorie + "Education", null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                evenements.add(new Evenement(
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
                        iEvenement.getResponsesEducation(evenements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvenement.getResponsesEducation(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);

    }

    /**
     *
     */
    public void getDataSport() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlCategorie + "Sport", null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                evenements.add(new Evenement(
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
                        iEvenement.getResponsesSport(evenements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvenement.getResponsesSport(null);
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
                                evenements.add(new Evenement(
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
                        iEvenement.getResponse(evenements);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvenement.getResponse(null);
                    }

                });

        AppController.getInstance().addToRequestQueue(request);
    }

    /**
     * Match data base de donnée avec server
     *
     * @param eventsServer
     */
    public void compareAndCharge(List<Evenement> eventsServer) {
        // Log.e("e", "eventsServer "+eventsServer.size());
        Cursor cursor = dbEvenement.getDatasCursor();
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

            for (Evenement evenement : eventsServer) {
                if (evenement.getId() == j) {
                    dbEvenement.createData(evenement.getId(), evenement.getCategorie(), evenement.getRubrique(), evenement.getTitre(), evenement.getTarif(), evenement.getLieu(), evenement.getPresentation(), evenement.getImage(), evenement.getHoraire(), evenement.getLien(), evenement.getJour(), evenement.getVideo(), evenement.getImagefull());
                    difference--;
                }
            }

        }
    }

}
