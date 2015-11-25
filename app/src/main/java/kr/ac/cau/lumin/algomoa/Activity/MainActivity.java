package kr.ac.cau.lumin.algomoa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;

import kr.ac.cau.lumin.algomoa.Network.ParsingTask;
import kr.ac.cau.lumin.algomoa.R;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-21.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (prefs.getBoolean("FirstExecute", false)) {
            prefs.edit().putBoolean("FirstExecute", true).apply();
        }

        Log.e("Visit", prefs.getBoolean("FirstExecute", false) + "");
        //ParsingTask parsingTask = new ParsingTask(MainActivity.this, new MainActivityPostListener());
        //parsingTask.execute();
    }

    private class MainActivityPostListener implements PostTaskListener {
        @Override
        public void executeOnPostTask() {
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        }
    }
}
