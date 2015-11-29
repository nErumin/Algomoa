package kr.ac.cau.lumin.algomoa.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.PostTaskListener;

/**
 * Created by Lumin on 2015-11-26.
 */
public abstract class NetworkCrawlTask extends AsyncTask<Void, Void, Void> implements NetworkListener {
    protected Context parsingContext;
    protected ProgressDialog contextDialog;
    protected PostTaskListener taskListener;

    protected NetworkCrawlTask(Context parsingContext, PostTaskListener taskListener) {
        this.parsingContext = parsingContext;
        this.taskListener = taskListener;
    }
}
