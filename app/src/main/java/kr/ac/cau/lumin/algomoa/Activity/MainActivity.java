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
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import java.io.InvalidObjectException;
import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Network.LanguageCrawlTask;
import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.Network.AlgorithmSiteCrawlTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.APIList;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Codeforces;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Contest;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Adapter.ContestSettingAdapter;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.Java;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.Ruby;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;
import kr.ac.cau.lumin.algomoa.Util.User;

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
    private AppCompatCheckBox[] langCheckBoxs;
    private AppCompatCheckBox[] siteCheckBoxs;
    private AppCompatTextView[] langTextViews;
    private AppCompatTextView[] siteTextViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initializeToolbar();
        View headerView = navigationView.inflateHeaderView(R.layout.drawer_header);
        this.langCheckBoxs = new AppCompatCheckBox[2];
        this.siteCheckBoxs = new AppCompatCheckBox[3];
        this.langTextViews = new AppCompatTextView[2];
        this.siteTextViews = new AppCompatTextView[3];

        this.langCheckBoxs[0] = (AppCompatCheckBox) headerView.findViewById(R.id.ruby_checkbox);
        this.langCheckBoxs[1] = (AppCompatCheckBox) headerView.findViewById(R.id.java_checkbox);
        this.langTextViews[0] = (AppCompatTextView) headerView.findViewById(R.id.ruby_textview);
        this.langTextViews[1] = (AppCompatTextView) headerView.findViewById(R.id.java_textview);

        this.siteCheckBoxs[0] = (AppCompatCheckBox) headerView.findViewById(R.id.codeforce_checkbox);
        this.siteCheckBoxs[1] = (AppCompatCheckBox) headerView.findViewById(R.id.baekjoon_checkbox);
        this.siteCheckBoxs[2] = (AppCompatCheckBox) headerView.findViewById(R.id.algospot_checkbox);
        this.siteTextViews[0] = (AppCompatTextView) headerView.findViewById(R.id.codeforce_textview);
        this.siteTextViews[1] = (AppCompatTextView) headerView.findViewById(R.id.baekjoon_textview);
        this.siteTextViews[2] = (AppCompatTextView) headerView.findViewById(R.id.algospot_textview);

        this.initializeCheckboxListener(langCheckBoxs, langTextViews, siteCheckBoxs, siteTextViews);
        /*this.favorLanguageRecyclerView = (RecyclerView) headerView.findViewById(R.id.fav_lang_recycler);
        this.favorSiteRecyclerView = (RecyclerView) headerView.findViewById(R.id.fav_site_recycler);

        this.favorLanguageRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        this.favorLanguageRecyclerView.setHasFixedSize(true);
        this.favorLanguageRecyclerView.setAdapter(new LanguageSettingAdapter(MainActivity.this, LanguageList.fetchAllLanguageList(), R.layout.setting_itemview));

        this.favorSiteRecyclerView.setHasFixedSize(true);
        this.favorSiteRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        this.favorSiteRecyclerView.setAdapter(new AlgorithmSettingAdapter(MainActivity.this, SiteList.fetchAllSiteList(), R.layout.setting_itemview));*/


        this.contestRecyclerView = (RecyclerView) findViewById(R.id.contest_recycler);
        this.settingFAB = (FloatingActionButton) findViewById(R.id.setting_fab);
        this.referenceFAB = (FloatingActionButton) findViewById(R.id.search_fab);
        this.problemFAB = (FloatingActionButton) findViewById(R.id.prob_fab);

        this.contestRecyclerView.setHasFixedSize(true);
        this.contestRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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

    private void initializeCheckboxListener(AppCompatCheckBox[] langCheckBoxs, final AppCompatTextView[] langTextViews, AppCompatCheckBox[] siteCheckBoxs, final AppCompatTextView[] siteTextViews) {
        for (int i = 0; i < langCheckBoxs.length; i++) {
            final int index = i;
            langCheckBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        User.getInstance().addNewFavoriteLanguage(LanguageList.valueOf(langTextViews[index].getText().toString()));
                    } else {
                        try {
                            User.getInstance().deleteFavoriteLanguage(LanguageList.valueOf(langTextViews[index].getText().toString()));
                        } catch (InvalidObjectException e) {
                            Log.e("CheckBoxListener", "Cannot find User language." + e.getMessage());
                        }
                    }
                }
            });
        }

        for (int i = 0; i < siteCheckBoxs.length; i++) {
            final int index = i;
            siteCheckBoxs[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        User.getInstance().addNewFavoriteSite(SiteList.valueOf(siteTextViews[index].getText().toString()));
                    } else {
                        try {
                            User.getInstance().deleteFavoriteSite(SiteList.valueOf(siteTextViews[index].getText().toString()));
                        } catch (InvalidObjectException e) {
                            Log.e("CheckBoxListener", "Cannot find User site." + e.getMessage());
                        }
                    }
                }
            });
        }
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