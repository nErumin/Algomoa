package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lumin on 2015-11-21.
 */
public class AlgomoaNetworkQueue  {
    private static AlgomoaNetworkQueue algomoaQueue;
    private static RequestQueue requestQueue;
    private static Context queueContext;
    private static String LOG_TAG = "HTTP GET";


    public static synchronized AlgomoaNetworkQueue getInstance(Context context) {
        if (algomoaQueue == null) {
            algomoaQueue = new AlgomoaNetworkQueue(context);
        }

        return algomoaQueue;
    }

    private AlgomoaNetworkQueue(Context context) {
        queueContext = context;
        requestQueue = this.getRequestQueue();
    }

    private <T> void addObjectInRequestQueue(Request<T> request) {
        this.getRequestQueue().add(request);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(queueContext);
        }
        return requestQueue;
    }

    public String sendHttpPostRequest() {
        String postResult = null;
        // TODO : POST Operation.
        // StringRequest request = new StringRequest(Request.Method.GET, , , );
        return postResult;
    }

    public void sendHttpGetRequest(Transmittable transmittable, final NetworkListener networkListener) {
        // TODO : GET Operation

        Log.e("Test", "GET Request Reached");
        Log.e("Test", transmittable.getRequestURL());
        StringRequest request = new StringRequest(Request.Method.GET, transmittable.getRequestURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                networkListener.executeOnNetwork(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkListener.executeFailOnNetwork(error.getMessage());
            }
        });

        requestQueue.add(request);
    }
}
