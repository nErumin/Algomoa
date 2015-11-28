package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String siteName = getIntent().getExtras().getString("SiteName");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);
        ArrayList<Problem> problemList = AlgomoaSQLHelper.getInstance(this).getAllProblems(SiteList.valueOf(siteName));
        this.problemRecyclerView = (RecyclerView) findViewById(R.id.prob_list_recycler);
        this.problemRecyclerView.setHasFixedSize(true);
        this.problemRecyclerView.setLayoutManager(new LinearLayoutManager(ProblemSelectActivity.this));
        this.problemRecyclerView.setAdapter(new SimpleItemAdapter(ProblemSelectActivity.this, problemList, R.layout.simple_itemview));
        this.initializeToolBar();
        this.initializeRecyclerView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void initializeToolBar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (this.toolbar != null) {
            this.setSupportActionBar(this.toolbar);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initializeRecyclerView() {
        // TODO : This

    }
}