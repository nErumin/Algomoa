package kr.ac.cau.lumin.algomoa.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.SimpleWebViewClient;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ProblemViewActivity extends AppCompatActivity {
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String languageName = this.getIntent().getExtras().getString("lang_name");
        String className = this.getIntent().getExtras().getString("class_name");
        String targetURL;

        super.onCreate(savedInstanceState);
        // TODO : What?
        setContentView(R.layout.activity_main);

        targetURL = AlgomoaSQLHelper.getInstance(this).getReferenceURL(LanguageList.valueOf(languageName), className);
        //this.referWebView = (WebView) findViewById(R.id.);
        //this.backButton = (Button) findViewById(R.id.);

    }
}
