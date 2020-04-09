package com.incture.taguig.network;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Connector {
    Context context;

    public Connector(Context context) {
        this.context = context;
    }

    public void connect(final String URL, int POST, final JSONObject body, final VolleyListener volleyListener, final HashMap<String, String> requestHeaders) {


        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                POST,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("RespondedData", body.toString());
                        Log.d("ResponseServer", response.toString());
                        volleyListener.onResponseReceived(URL, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("resp", "Error: " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");

                String token ="Token 02cdb6e3d294465c6ae8c1b9417b45be";
                headers.put("Authorization", token);
                headers.put("x-uuid", "84004a10-3828-4845-87e6-6a7d063d94af");
                return headers;
            }
        };


        BotApplication.getInstance(context).addToRequestQueue(jsonRequest);

    }
}
