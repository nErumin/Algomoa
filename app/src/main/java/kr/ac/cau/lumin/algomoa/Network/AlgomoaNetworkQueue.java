package kr.ac.cau.lumin.algomoa.Network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Lumin on 2015-11-21.
 */
public class AlgomoaNetworkQueue implements Response.ErrorListener {
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

    public String sendHttpGetRequest(Transmittable transmittable) {
        final String getRequestResult = null;
        // TODO : GET Operation
        StringRequest request = new StringRequest(Request.Method.GET, transmittable.getRequestURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRequestResult = response;
            }
        }, this);

        requestQueue.add(request);
        return getRequestResult;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(queueContext, "페이지를 불러오는 중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
    }
}
