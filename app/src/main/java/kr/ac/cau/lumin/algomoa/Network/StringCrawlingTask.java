package kr.ac.cau.lumin.algomoa.Network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.SQLite.AlgomoaSQLHelper;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;

/**
 * Created by Lumin on 2015-11-28.
 */
public class StringCrawlingTask extends AsyncTask<String, Void, Void> {
    private Crawlable crawlableObject;
    private CrawlListener crawlListener;
    private Context context;

    public StringCrawlingTask(Context context, Crawlable crawlableObject, CrawlListener crawlListener) {
        this.crawlableObject = crawlableObject;
        this.crawlListener = crawlListener;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        ArrayList<LanguageRefer> crawlObjects = (ArrayList<LanguageRefer>) this.crawlableObject.crawlContentFromHtml(strings[0]);

        for (LanguageRefer ref : crawlObjects) {
            AlgomoaSQLHelper.getInstance(context).addReference(ref);
            Log.e("Language Crawling", "Ref Name : " + ref.getReferenceName() + " , Lang : " + ref.getLanguage().toString() + " , Url : " + ref.getRequestURL());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.crawlListener.executeOnCrawling(null);
    }
}
