package net.apkode.matano.api;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.apkode.matano.helper.AppController;
import net.apkode.matano.interfaces.IFeedback;
import net.apkode.matano.model.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class APIFeedback {
    private final static String urlSendFeedback = "https://matano-api.herokuapp.com/feedbacks";
    private IFeedback iFeedback;

    public APIFeedback(IFeedback reference, Context ctx) {
        iFeedback = reference;
    }

    public void sendFeedback(final Utilisateur utilisateur, final String message) {

        StringRequest request = new StringRequest(Request.Method.POST, urlSendFeedback,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iFeedback.getResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iFeedback.getResponse(null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();

                map.put("telephone", utilisateur.getTelephone());
                map.put("message", message);

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }
}
