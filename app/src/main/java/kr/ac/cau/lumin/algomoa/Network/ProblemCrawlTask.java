package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-25.
 */
public class ProblemCrawlTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    private Context parsingContext;
    private ProgressDialog contextDialog;
    private PostTaskListener taskListener;
    private Problem crawlProblem;

    public ProblemCrawlTask(Problem crawlProblem, Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
        this.crawlProblem = crawlProblem;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", crawlProblem.getSiteList().toString() + "의 문제를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(crawlProblem, this);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {
        this.taskListener.executeOnPostTask(crawlProblem.crawlContentFromHtml(response));
        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {
        ParsingTask redoingTask = new ParsingTask(parsingContext, this.taskListener);
        Toast.makeText(parsingContext, crawlProblem.getSiteList().toString() + "의 문제 로드에 실패했습니다. 재시도합니다.", Toast.LENGTH_SHORT).show();

        redoingTask.execute();
        this.contextDialog.dismiss();
    }
}
