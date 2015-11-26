package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Language.Language;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by CAUCSE on 2015-11-26.
 */
public class LanguageCrawlTask extends CrawlTask {
    private Language language;

    public LanguageCrawlTask(Language language, Context parsingContext, PostTaskListener taskListener) {
        super(parsingContext, taskListener);
        this.language = language;
    }

    @Override
    protected void onPreExecute() {
        this.contextDialog = ProgressDialog.show(this.parsingContext, "", this.language.getLanguageName() + "의 레퍼런스 정보를 불러오는 중입니다...", false, false);
        this.contextDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        AlgomoaNetworkQueue.getInstance(parsingContext).sendHttpGetRequest(language, this);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    public void executeOnNetwork(String response) {
        ArrayList<LanguageRefer> refers = (ArrayList<LanguageRefer>) this.language.crawlContentFromHtml(response);
        for (LanguageRefer ref : refers) {
            // AlgomoaSQLHelper.getInstance(parsingContext).addProblem(problem);
            Log.e("Language Crawling", "Ref Name : " + ref.getReferenceName() + " , Lang : " + ref.getLanguage().toString() + " , Url : " + ref.getRequestURL());
        }

        this.taskListener.executeOnPostTask();
        this.contextDialog.dismiss();
    }

    @Override
    public void executeFailOnNetwork(String errorResponse) {
        ParsingTask redoingTask = new ParsingTask(parsingContext, this.taskListener);
        Toast.makeText(parsingContext, this.language.getLanguageName() + "의 레퍼런스 로드에 실패했습니다. 재시도합니다.", Toast.LENGTH_SHORT).show();

        redoingTask.execute();
        this.contextDialog.dismiss();
    }
}
