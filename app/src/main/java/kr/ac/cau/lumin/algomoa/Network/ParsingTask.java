package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InvalidObjectException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.Codeforces;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;

/**
 * Created by Lumin on 2015-11-24.
 */
public class ParsingTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    private Context parsingContext;
    private ProgressDialog contextDialog;
    private PostTaskListener taskListener;

    public ParsingTask(Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", "Codeforces API의 응답을 기다리는 중입니다...", false, false);
        this.contextDialog.show();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(Codeforces.getInstance(), this);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    @Override
    public void executeOnNetwork(String response) {
        try {
            SiteCrawlTask newCrawlTask = new SiteCrawlTask(parsingContext, this.taskListener);
            ArrayList<Problem> codeforcesProblemList = Codeforces.getInstance().parseJSONObject(response);
            Codeforces.getInstance().addProblem(codeforcesProblemList);

            newCrawlTask.execute();
            this.contextDialog.dismiss();
            //this.taskListener.executeOnPostTask();
        } catch (InvalidObjectException e) {
            Toast.makeText(parsingContext, "Codeforces API를 불러오는 과정에 문제가 발생하였습니다.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {
        Log.e("Test", "Failed Execute");
        this.contextDialog.dismiss();
       // this.taskListener.executeOnPostTask();
    }
}