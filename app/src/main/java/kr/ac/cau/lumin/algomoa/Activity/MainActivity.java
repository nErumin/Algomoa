package kr.ac.cau.lumin.algomoa.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import kr.ac.cau.lumin.algomoa.Network.AlgomoaNetworkQueue;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.Problem;
import kr.ac.cau.lumin.algomoa.Util.SimpleWebViewClient;
import kr.ac.cau.lumin.algomoa.Util.SiteList;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity {

    WebView wv;
    String[] str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = new String[1];
        str[0] = "";
        wv = (WebView) findViewById(R.id.testWebView);
        AlgomoaNetworkQueue.getInstance(this).sendHttpGetRequest(new Transmittable() {
            @Override
            public String getRequestURL() {
                return "http://codeforces.com/api/problemset.problems";
            }

        }, str);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TEST", "Receive : " + str[0]);
    }
}
