package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.SimpleWebViewClient;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ReferenceViewActivity extends AppCompatActivity {
    private WebView referWebView;
    private Toolbar toolbar;
    private TextView nameTextView;
    private AppCompatImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String languageName = this.getIntent().getExtras().getString("Language");
        String className = this.getIntent().getExtras().getString("Reference");
        String targetURL = this.getIntent().getExtras().getString("URL");

        super.onCreate(savedInstanceState);
        // TODO : What?
        setContentView(R.layout.activity_webview);

        this.nameTextView = (TextView) findViewById(R.id.toolbar_text);
        this.imageView = (AppCompatImageView) findViewById(R.id.toolbar_image);
        this.nameTextView.setText(languageName + " - " + className);
        this.imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.codeforce_ic));

        this.initializeToolBar();
        targetURL = AlgomoaSQLHelper.getInstance(this).getReferenceURL(LanguageList.valueOf(languageName), className);
        this.referWebView = (WebView) findViewById(R.id.refer_webview);
        this.referWebView.setWebViewClient(new SimpleWebViewClient());
        this.referWebView.getSettings().setJavaScriptEnabled(true);
        this.referWebView.loadUrl(targetURL);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, ReferenceSearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_refer_view);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayUseLogoEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
