package com.example.volleytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private String urlArray;
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<JSONObject> jsonObjects = new ArrayList<>();

        // this url have the json data of joke messages
        urlArray = "https://official-joke-api.appspot.com/random_ten";

        //request queue creates another thread sepparated from UI thread which is helpful
        // to handle heavy task and reduces the work load of main thread
        /*
         *RequestQueue requestQueue = Volley.newRequestQueue(this);
         */
        requestQueueArray = VolleySingleton.getInstance().getRequestQueue();

        //this request contains the url, request methode, response listener and the error listener
        //response listener contains the JsonObject or JsonArray. We can access data from that
        //Error listener invoked when there is any error occurs. then the JsonObject or array contains null value
        arrayRequest = new JsonArrayRequest(Request.Method.GET, urlArray, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    jsonObjects.clear();
                    for (int index = 0; index < response.length(); index++) {
                        jsonObjects.add(response.getJSONObject(index));

                        Log.i("MainActivity", "JsonArrayResp: " +
                                jsonObjects.get(index).getString("setup") + " - " +
                                jsonObjects.get(index).getString("punchline") + "\n");
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ArrayError", "onErrorResponse: " + error.getMessage());
            }
        });

        // we need to add our JsonRequest to the RequestQueue
        /*
         * requestQueue.add(arrayRequest)
         */
        requestQueueArray.add(arrayRequest);

    }
}