package kr.ac.cau.lumin.algomoa.Util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Lumin on 2015-11-21.
 */
public class SimpleWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
