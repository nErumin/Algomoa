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

import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Algospot;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
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
    private Parsable parsableObject;

    public ParsingTask(Context parsingContext, Parsable parsableObject, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
        this.parsableObject = parsableObject;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", "Codeforces API의 응답을 기다리는 중입니다...", false, false);
        this.contextDialog.show();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(parsableObject, this);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }

    @Override
    public void executeOnNetwork(String response) {
        this.taskListener.executeOnPostTask(null);
        ArrayList<Problem> codeforcesProblemList = Codeforces.getInstance().parseJSONObject(response);

        for (Problem problem : codeforcesProblemList) {
            AlgomoaSQLHelper.getInstance(parsingContext).addProblem(problem);
            Log.e("Site Parsing Problems", "ID : " + problem.getProblemNumber() + " , Name : " + problem.getProblemName() + " , Url : " + problem.getRequestURL());
        }

        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {
        ParsingTask redoingTask = new ParsingTask(parsingContext, this.parsableObject, this.taskListener);
        Toast.makeText(parsingContext, "Codeforces API 로드에 실패했습니다. 재시도합니다.", Toast.LENGTH_SHORT).show();

        redoingTask.execute();
        this.contextDialog.dismiss();
    }
}