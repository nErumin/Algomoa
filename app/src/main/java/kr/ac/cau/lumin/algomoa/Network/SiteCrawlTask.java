package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.HTMLElements;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-25.
 */
public class SiteCrawlTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    private Context parsingContext;
    private ProgressDialog contextDialog;
    private PostTaskListener taskListener;

    public SiteCrawlTask(Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", "Baekjoon Online Judge의 문제 리스트를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(new Transmittable() {
            @Override
            public String getRequestURL() {
                return "https://www.acmicpc.net/problemset/1";
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
        List<Element> numberElementList = htmlSource.getAllElements(HTMLElementName.TD);
        List<Element> nameElementList = htmlSource.getAllElements(HTMLElementName.A);

            ArrayList<String> problemNumList = new ArrayList<>();
            ArrayList<String> problemNameList = new ArrayList<>();

            for (int i = 0; i < numberElementList.size(); i++) {
                Element numberElement = numberElementList.get(i);
                String numberAttrValue = numberElement.getAttributeValue("class");

                if (numberAttrValue != null && numberAttrValue.equals("list_problem_id")) {
                    TextExtractor extractor = numberElement.getTextExtractor();
                    problemNumList.add(extractor.toString());
                }
            }

        for (int i = 0; i < nameElementList.size(); i++) {
            Element nameElement = nameElementList.get(i);
            String nameAttrValue = nameElement.getAttributeValue("href");

            if (nameAttrValue != null && nameAttrValue.startsWith("/problem")) {
                TextExtractor extractor = nameElement.getTextExtractor();
                problemNameList.add(extractor.toString());
            }
        }



        for (int i = 0; i < nameElementList.size(); i++) {
            Log.e("Jericho Test", "ID : " + problemNumList.get(i)  + " Name : " + problemNameList.get(i));
        }
        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {

    }
}
