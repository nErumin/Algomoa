package kr.ac.cau.lumin.algomoa.Activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.Network.AlgorithmSiteCrawlTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //!prefs.getBoolean("FirstExecute", false)
        if (true) {
            ParsingTask parsingTask = new ParsingTask(MainActivity.this, new MainActivityPostListener());
            AlgorithmSiteCrawlTask baekjoonCrawlTask = new AlgorithmSiteCrawlTask(BaekjoonOnlineJudge.getInstance(), MainActivity.this, new MainActivityPostListener());
            AlgorithmSiteCrawlTask algospotCrawlTask = new AlgorithmSiteCrawlTask(Algospot.getInstance(), MainActivity.this, new MainActivityPostListener());
            prefs.edit().putBoolean("FirstExecute", true).apply();
            parsingTask.execute();
            baekjoonCrawlTask.execute();
            algospotCrawlTask.execute();
        }

    }

    private class MainActivityPostListener implements PostTaskListener {
        @Override
        public void executeOnPostTask() {
            //Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            //startActivity(intent);
        }
    }
}
