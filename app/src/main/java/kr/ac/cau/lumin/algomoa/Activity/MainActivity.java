package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.cau.lumin.algomoa.Network.LanguageCrawlTask;
import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.Network.AlgorithmSiteCrawlTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.APIList;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Codeforces;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.AlgorithmSettingAdapter;
import kr.ac.cau.lumin.algomoa.Util.Language.Java;
import kr.ac.cau.lumin.algomoa.Util.Language.Language;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.Ruby;
import kr.ac.cau.lumin.algomoa.Util.LanguageSettingAdapter;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggleButton;
    private FloatingActionButton settingFAB;
    private FloatingActionButton referenceFAB;
    private FloatingActionButton problemFAB;
    private NavigationView navigationView;
    private RecyclerView favorLanguageRecyclerView;
    private RecyclerView favorSiteRecyclerView;
    private RecyclerView contestRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initializeToolbar();
        View headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        this.favorLanguageRecyclerView = (RecyclerView) headerView.findViewById(R.id.fav_lang_recycler);
        this.favorSiteRecyclerView = (RecyclerView) headerView.findViewById(R.id.fav_site_recycler);

        this.favorLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        this.favorLanguageRecyclerView.setHasFixedSize(true);
        this.favorLanguageRecyclerView.setAdapter(new LanguageSettingAdapter(MainActivity.this, LanguageList.fetchAllLanguageList(), R.layout.setting_itemview));

        this.favorSiteRecyclerView.setHasFixedSize(true);
        this.favorSiteRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        this.favorSiteRecyclerView.setAdapter(new AlgorithmSettingAdapter(MainActivity.this, SiteList.fetchAllSiteList(), R.layout.setting_itemview));
        

        this.contestRecyclerView = (RecyclerView) findViewById(R.id.contest_recycler);
        this.settingFAB = (FloatingActionButton) findViewById(R.id.setting_fab);
        this.referenceFAB = (FloatingActionButton) findViewById(R.id.search_fab);
        this.problemFAB = (FloatingActionButton) findViewById(R.id.prob_fab);

        this.contestRecyclerView.setHasFixedSize(true);
        this.contestRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        this.contestRecyclerView.setAdapter(new LanguageSettingAdapter(MainActivity.this, LanguageList.fetchAllLanguageList(), R.layout.setting_itemview));

        Log.e("Preference", prefs.getBoolean("FirstExecute", false) + "");
        ParsingTask contestParsingTask = new ParsingTask(MainActivity.this, Codeforces.getInstance(), APIList.CodeforcesContest, new MainActivityPostListener());
        contestParsingTask.execute();
        if (!prefs.getBoolean("FirstExecute", false)) {
            ParsingTask parsingTask = new ParsingTask(MainActivity.this, Codeforces.getInstance(), APIList.CodeforcesProblem, new PostTaskListener() {
                @Override
                public void executeOnPostTask(Object helper) {
                    ArrayList<Problem> codeforcesProblemList = Codeforces.getInstance().parseJSONObject((String) helper);

                    for (Problem problem : codeforcesProblemList) {
                        AlgomoaSQLHelper.getInstance(MainActivity.this).addProblem(problem);
                        Log.e("Site Parsing Problems", "ID : " + problem.getProblemNumber() + " , Name : " + problem.getProblemName() + " , Url : " + problem.getRequestURL());
                    }
                }
            });
            AlgorithmSiteCrawlTask baekjoonCrawlTask = new AlgorithmSiteCrawlTask(BaekjoonOnlineJudge.getInstance(), MainActivity.this, new MainActivityPostListener());
            AlgorithmSiteCrawlTask algospotCrawlTask = new AlgorithmSiteCrawlTask(Algospot.getInstance(), MainActivity.this, new MainActivityPostListener());
            LanguageCrawlTask rubyCrawlTask = new LanguageCrawlTask(Ruby.getInstance(), MainActivity.this, new MainActivityPostListener());
            LanguageCrawlTask javaCrawlTask = new LanguageCrawlTask(Java.getInstance(), MainActivity.this, new MainActivityPostListener());

            parsingTask.execute();
            baekjoonCrawlTask.execute();
            algospotCrawlTask.execute();
            rubyCrawlTask.execute();
            javaCrawlTask.execute();
            prefs.edit().putBoolean("FirstExecute", true).apply();
        }


        this.settingFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.drawerToggleButton.syncState();
    }

    private void initializeToolbar() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);


        if (toolbar != null) {
            this.setSupportActionBar(toolbar);
        }

        this.initializeDrawerLayout();
        this.navigationView = (NavigationView) findViewById(R.id.main_drawer_view);
        //navigationView.setNavigationItemSelectedListener();
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
            Codeforces.getInstance().parseContestJSONObject((String) helper);
            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            //startActivity(intent);
        }
    }
}
