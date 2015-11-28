package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Network.AlgorithmSiteCrawlTask;
import kr.ac.cau.lumin.algomoa.Network.LanguageCrawlTask;
import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Adapter.ContestSettingAdapter;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.APIList;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Codeforces;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Contest;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Language.Java;
import kr.ac.cau.lumin.algomoa.Util.Language.Ruby;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;
import kr.ac.cau.lumin.algomoa.Util.User;

/**
 * Created by Lumin on 2015-11-24.
 */
public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Log.e("Preference", prefs.getBoolean("FirstExecute", false) + "");

        if (!prefs.getBoolean("FirstExecute", false)) {
            ParsingTask parsingTask = new ParsingTask(IntroActivity.this, Codeforces.getInstance(), APIList.CodeforcesProblem, new PostTaskListener() {
                @Override
                public void executeOnPostTask(Object helper) {
                    ArrayList<Problem> codeforcesProblemList = Codeforces.getInstance().parseJSONObject((String) helper);

                    for (Problem problem : codeforcesProblemList) {
                        AlgomoaSQLHelper.getInstance(getApplicationContext()).addProblem(problem);
                        Log.e("Site Parsing Problems", "ID : " + problem.getProblemNumber() + " , Name : " + problem.getProblemName() + " , Url : " + problem.getRequestURL());
                    }

                    AlgorithmSiteCrawlTask baekjoonCrawlTask = new AlgorithmSiteCrawlTask(BaekjoonOnlineJudge.getInstance(), IntroActivity.this, new PostTaskListener() {
                        @Override
                        public void executeOnPostTask(Object helper) {
                            AlgorithmSiteCrawlTask algospotCrawlTask = new AlgorithmSiteCrawlTask(Algospot.getInstance(), IntroActivity.this, new PostTaskListener() {
                                @Override
                                public void executeOnPostTask(Object helper) {
                                    LanguageCrawlTask rubyCrawlTask = new LanguageCrawlTask(Ruby.getInstance(), IntroActivity.this, new PostTaskListener() {
                                        @Override
                                        public void executeOnPostTask(Object helper) {
                                            LanguageCrawlTask javaCrawlTask = new LanguageCrawlTask(Java.getInstance(), IntroActivity.this, new MainActivityPostListener());
                                            javaCrawlTask.execute();
                                        }
                                    });
                                    rubyCrawlTask.execute();
                                }
                            });
                            algospotCrawlTask.execute();
                        }
                    });
                    baekjoonCrawlTask.execute();
                }
            });
            parsingTask.execute();
            prefs.edit().putBoolean("FirstExecute", true).apply();
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            User.getInstance().setUserFavoriteInfomation(getApplicationContext());
            startActivity(intent);
        }
    }


    private class MainActivityPostListener implements PostTaskListener {
        @Override
        public void executeOnPostTask(Object helper) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            User.getInstance().setUserFavoriteInfomation(getApplicationContext());
            startActivity(intent);
        }
    }
}
