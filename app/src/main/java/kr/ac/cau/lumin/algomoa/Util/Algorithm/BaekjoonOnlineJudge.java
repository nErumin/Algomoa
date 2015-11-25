package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;

/**
 * Created by Lumin on 2015-11-25.
 */
public class BaekjoonOnlineJudge extends AlgorithmSite {
    private static BaekjoonOnlineJudge siteInstance;

    public synchronized static BaekjoonOnlineJudge getInstance() {
        if (siteInstance == null) {
            siteInstance = new BaekjoonOnlineJudge();
        }

        return siteInstance;
    }

    private BaekjoonOnlineJudge() {
        super(SiteList.BaekjoonOnlineJudge);
    }

    @Override
    public String[] crawlContentFromHtml(String htmlContent) {
        // TODO : Implementation

        return new String[0];
    }
}
