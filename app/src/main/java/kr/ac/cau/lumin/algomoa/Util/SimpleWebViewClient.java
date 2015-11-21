package kr.ac.cau.lumin.algomoa.Util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by CAUCSE on 2015-11-21.
 */
public class SimpleWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // TODO : Need to check whether should load url or not.
        // view.loadUrl(url);
        return true;
    }
}
