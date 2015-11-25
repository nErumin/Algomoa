package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.HTMLElements;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.AlgorithmSite;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonOnlineJudge;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-25.
 */
public class SiteCrawlTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    private Context parsingContext;
    private ProgressDialog contextDialog;
    private PostTaskListener taskListener;
    private AlgorithmSite crawlSite;

    public SiteCrawlTask(AlgorithmSite crawlSite, Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
        this.crawlSite = crawlSite;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", crawlSite.getSiteName().toString() + "의 문제 리스트를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        this.crawlSite.refreshProblemNumber();

        for (int problemSet = 1; problemSet <= this.crawlSite.getMaxProblemSet(); problemSet++) {
            final int copiedProblemSetNumber = problemSet;
            AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(new Transmittable() {
                @Override
                public String getRequestURL() {
                    return crawlSite.getRequestURL() + copiedProblemSetNumber;
                }
            }, this);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {
        ArrayList<Problem> problems = (ArrayList<Problem>) this.crawlSite.crawlContentFromHtml(response);
        for (Problem problem : problems) {
           // AlgomoaSQLHelper.getInstance(parsingContext).addProblem(problem);
            Log.e("Site Crawling Problems", "ID : " + problem.getProblemNumber() + " , Name : " + problem.getProblemName() + " , Url : " + problem.getRequestURL());
        }

        ProblemCrawlTask t = new ProblemCrawlTask(problems.get(0), parsingContext, taskListener);
        t.execute();
        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {
        ParsingTask redoingTask = new ParsingTask(parsingContext, this.taskListener);
        Toast.makeText(parsingContext, crawlSite.getSiteName().toString() + "의 문제 리스트 로드에 실패했습니다. 재시도합니다.", Toast.LENGTH_SHORT).show();

        redoingTask.execute();
        this.contextDialog.dismiss();
    }
}
