package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Adapter.SimpleItemAdapter;

/**
 * Created by Lumin on 2015-11-26.
 */
public class ProblemSelectActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView problemRecyclerView;
    private TextView toolbarTextView;
    private AppCompatImageView toolbarImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String siteName = getIntent().getExtras().getString("SiteName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_select_view);
        ArrayList<Problem> problemList = AlgomoaSQLHelper.getInstance(this).getAllProblems(SiteList.valueOf(siteName));
        this.problemRecyclerView = (RecyclerView) findViewById(R.id.prob_list_recycler);
        this.toolbarTextView = (TextView) findViewById(R.id.toolbar_text);
        this.toolbarImageView = (AppCompatImageView) findViewById(R.id.toolbar_image);

        this.toolbarImageView.setImageDrawable(SiteList.valueOf(siteName).fetchDrawable(getApplicationContext()));
        this.problemRecyclerView.setHasFixedSize(true);
        this.problemRecyclerView.setLayoutManager(new LinearLayoutManager(ProblemSelectActivity.this));
        this.problemRecyclerView.setAdapter(new SimpleItemAdapter(ProblemSelectActivity.this, problemList, R.layout.simple_itemview));

        this.toolbarTextView.setText(siteName);

        this.initializeToolBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, SiteSelectActivity.class);
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
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}