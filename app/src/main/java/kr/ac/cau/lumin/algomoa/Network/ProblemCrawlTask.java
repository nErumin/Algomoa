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
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", "의 문제 리스트를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {
        Source htmlSource = new Source(response);
        List<Element> elementList = htmlSource.getAllElements(HTMLElementName.SECTION);
        Hashtable<String, ArrayList<String>> infoTable = new Hashtable<>();
        infoTable.put("description", new ArrayList<String>());
        infoTable.put("input", new ArrayList<String>());
        infoTable.put("output", new ArrayList<String>());
        infoTable.put("sampleinput", new ArrayList<String>());
        infoTable.put("sampledata", new ArrayList<String>());
        infoTable.put("sampleoutput", new ArrayList<String>());

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("id");
            TextExtractor extractor = element.getTextExtractor();

            if (attrValue != null && infoTable.containsKey(attrValue)) {
                infoTable.get(attrValue).add(attrValue);
            }
        }

        //Log.e("Jericho Parse", infoTable.get("description"));
        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {

    }
}
