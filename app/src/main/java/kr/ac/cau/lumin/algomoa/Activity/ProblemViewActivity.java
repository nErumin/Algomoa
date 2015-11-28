package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.SimpleWebViewClient;

/**
 * Created by Lumin on 2015-11-26.
 */

public class ProblemViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private AppCompatImageView toolbarImageView;
    private TextView toolbarText;
    private AppCompatTextView probDescriptionText;
    private AppCompatTextView probInputText;
    private AppCompatTextView probOutputText;
    private AppCompatTextView probSampleInputText;
    private AppCompatTextView probSampleOutputText;
    private String siteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prob_view);
        this.initializeToolBar();
        this.siteName = getIntent().getExtras().getString("SiteName");
        this.probDescriptionText = (AppCompatTextView) findViewById(R.id.prob_description);
        this.probInputText = (AppCompatTextView) findViewById(R.id.prob_input);
        this.probOutputText = (AppCompatTextView) findViewById(R.id.prob_output);
        this.probSampleInputText = (AppCompatTextView) findViewById(R.id.prob_sample_input);
        this.probSampleOutputText = (AppCompatTextView) findViewById(R.id.prob_sample_output);

        this.probDescriptionText.setText(getIntent().getExtras().getString("Description"));
        this.probInputText.setText(getIntent().getExtras().getString("Input"));
        this.probOutputText.setText(getIntent().getExtras().getString("Output"));
        this.probSampleInputText.setText(getIntent().getExtras().getString("SampleInput"));
        this.probSampleOutputText.setText(getIntent().getExtras().getString("SampleOutput"));

        this.toolbarText.setText(getIntent().getExtras().getString("ProblemCode") + " - " + getIntent().getExtras().getString("ProblemName"));
        this.toolbarImageView.setImageDrawable(SiteList.valueOf(this.siteName).fetchDrawable(getApplicationContext()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, ProblemSelectActivity.class);
                intent.putExtra("SiteName", this.siteName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_refer_view);
        this.toolbarImageView = (AppCompatImageView) toolbar.findViewById(R.id.toolbar_image);
        this.toolbarText = (TextView) toolbar.findViewById(R.id.toolbar_text);


        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayUseLogoEnabled(false);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
