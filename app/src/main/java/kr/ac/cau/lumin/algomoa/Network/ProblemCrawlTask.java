package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-25.
 */
public class ProblemCrawlTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    private Context parsingContext;
    private ProgressDialog contextDialog;
    private PostTaskListener taskListener;

    public ProblemCrawlTask(Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", "의 문제 리스트를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {

    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {

    }
}
