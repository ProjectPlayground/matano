package net.apkode.matano.api;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import net.apkode.matano.db.DBEvent;
import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IEvent;
import net.apkode.matano.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class APIEvent {
    private static final String COLUMN_ID = "id";
    private final static String url = "http://niameyzze.apkode.net/event.php";
    private IEvent iEvent;
    private List<Event> events;
    private DBEvent dbEvent;

    public APIEvent(IEvent reference, Context ctx) {
        iEvent = reference;
        dbEvent = new DBEvent(ctx);
        events = new ArrayList<>();
    }

    /**
     * get data from server
     *
     * @return
     */
    public void getData() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                events.add(new Event(
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
                        iEvent.getResponse(events);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iEvent.getResponse(events);
                        Log.e("e", "error " + error.getMessage());
                    }

                });

        AppController.getInstance().addToRequestQueue(request);

    }

    /**
     * Match data base de donnée avec server
     *
     * @param eventsServer
     */
    public void compareAndCharge(List<Event> eventsServer) {
        // Log.e("e", "eventsServer "+eventsServer.size());
        Cursor cursor = dbEvent.getDatasCursor();
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

            for (Event event : eventsServer) {
                if (event.getId() == j) {
                    dbEvent.createData(event.getId(), event.getCategorie(), event.getRubrique(), event.getTitre(), event.getTarif(), event.getLieu(), event.getPresentation(), event.getImage(), event.getHoraire(), event.getLien(), event.getJour(), event.getVideo(), event.getImagefull());
                    difference--;
                }
            }

        }
    }

}
