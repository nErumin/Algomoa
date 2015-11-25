package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;

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
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", crawlProblem.getSiteList().toString() + "의 문제 리스트를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(new Transmittable() {
            @Override
            public String getRequestURL() {
                return "https://www.acmicpc.net/problem/1000";
            }
        }, this);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {
        Source htmlSource = new Source(response);
        List<Element> elementList = htmlSource.getAllElements(HTMLElementName.DIV);
        Hashtable<String, ArrayList<String>> infoTable = new Hashtable<>();
        infoTable.put("problem_description", new ArrayList<String>());
        infoTable.put("problem_input", new ArrayList<String>());
        infoTable.put("problem_output", new ArrayList<String>());
        infoTable.put("sample_input_1", new ArrayList<String>());
        infoTable.put("sample_output_1", new ArrayList<String>());

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("id");
            TextExtractor extractor = element.getTextExtractor();
            if (attrValue != null) {
                attrValue = attrValue.replaceAll("-", "_");
                if (infoTable.containsKey(attrValue)) {
                    infoTable.get(attrValue).add(extractor.toString());
                }
            }
        }

        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {

    }
}
