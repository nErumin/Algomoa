package kr.ac.cau.lumin.algomoa.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;

import kr.ac.cau.lumin.algomoa.Network.LanguageCrawlTask;
import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.Network.AlgorithmSiteCrawlTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.Java;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.Ruby;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeToolbar();

        if (!prefs.getBoolean("FirstExecute", false)) {
            ParsingTask parsingTask = new ParsingTask(MainActivity.this, new MainActivityPostListener());
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_drawer_view);
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
        public void executeOnPostTask() {
            //Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            //startActivity(intent);
        }
    }
}
