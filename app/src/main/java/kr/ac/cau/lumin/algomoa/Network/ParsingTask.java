package kr.ac.cau.lumin.algomoa.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatDialog;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;
import kr.ac.cau.lumin.algomoa.Util.Problem;

/**
 * Created by Lumin on 2015-11-24.
 */
public class ParsingTask extends AsyncTask<Void, Void, ArrayList<Problem>> implements NetworkListener {
    private Context parsingContext;
    private AppCompatDialog contextDialog;
    private PostTaskListener taskListener;

    public ParsingTask(Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = new AppCompatDialog(this.parsingContext);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Problem> doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Problem> problems) {
        super.onPostExecute(problems);
    }

    @Override
    public void executeOnNetwork() {
        this.contextDialog.dismiss();
        this.taskListener.executeOnPostTask();
    }

    @Override
    public void executeFailOnNetwork() {

    }
}