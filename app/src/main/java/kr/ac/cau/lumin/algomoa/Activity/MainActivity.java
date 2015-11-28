package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.APIList;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Codeforces;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Contest;
import kr.ac.cau.lumin.algomoa.Util.Adapter.ContestSettingAdapter;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggleButton;
    private FloatingActionButton settingFAB;
    private FloatingActionButton referenceFAB;
    private FloatingActionButton problemFAB;
    private RecyclerView contestRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initializeToolbar();

        this.contestRecyclerView = (RecyclerView) findViewById(R.id.contest_recycler);
        this.settingFAB = (FloatingActionButton) findViewById(R.id.setting_fab);
        this.referenceFAB = (FloatingActionButton) findViewById(R.id.search_fab);
        this.problemFAB = (FloatingActionButton) findViewById(R.id.prob_fab);

        this.contestRecyclerView.setHasFixedSize(true);
        this.contestRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        ParsingTask contestParsingTask = new ParsingTask(MainActivity.this, Codeforces.getInstance(), APIList.CodeforcesContest, new MainActivityPostListener());
        contestParsingTask.execute();


        this.settingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                //drawerLayout.openDrawer(navigationView);
            }
        });

        this.referenceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReferenceSearchActivity.class);
                startActivity(intent);
            }
        });

        this.problemFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SiteSelectActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.drawer_ruby:
                Toast.makeText(this, "설정이 눌렸어요~!", Toast.LENGTH_LONG).show();
                break;

        }

        drawerLayout.closeDrawers();
        item.setChecked(true);

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void initializeToolbar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }

       // this.navigationView = (NavigationView) findViewById(R.id.main_drawer_view);
        //this.initializeDrawerLayout();
        //this.navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeDrawerLayout() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.initializeToggleButton();
        this.drawerLayout.setDrawerListener(this.drawerToggleButton);
    }

    private void initializeToggleButton() {
        this.drawerToggleButton = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.app_name, R.string.app_name);
        this.drawerToggleButton.setDrawerIndicatorEnabled(true);
    }

    private class MainActivityPostListener implements PostTaskListener {
        @Override
        public void executeOnPostTask(Object helper) {
            ArrayList<Contest> contests = Codeforces.getInstance().parseContestJSONObject((String) helper);
            contestRecyclerView.setAdapter(new ContestSettingAdapter(MainActivity.this, contests, R.layout.contest_itemview));
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        }
    }
}