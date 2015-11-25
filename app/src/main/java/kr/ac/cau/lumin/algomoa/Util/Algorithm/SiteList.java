package kr.ac.cau.lumin.algomoa.Util.Algorithm;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum SiteList {
    Codeforces("http://codeforces.com/"),
    Algospot("https://algospot.com/"),
    BaekjoonOnlineJudge("https://www.acmicpc.net/");

    private String baseSearchURL;

    private SiteList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }
}
