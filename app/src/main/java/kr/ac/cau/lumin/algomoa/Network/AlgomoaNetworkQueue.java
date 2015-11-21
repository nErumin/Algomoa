package kr.ac.cau.lumin.algomoa.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Lumin on 2015-11-21.
 */
public class AlgomoaNetworkQueue {
    private static AlgomoaNetworkQueue algomoaQueue;
    private static RequestQueue requestQueue;
    private static Context queueContext;

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

    public String sendHttpGetRequest() {
        String getRequestResult = null;
        // TODO : GET Operation
        return getRequestResult;
    }
}
