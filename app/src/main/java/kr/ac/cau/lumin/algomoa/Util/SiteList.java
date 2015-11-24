package kr.ac.cau.lumin.algomoa.Util;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum SiteList {
    Codeforces("http://codeforces.com/problemset/problem/"),
    Algospot("https://algospot.com/judge/problem/read/"),
    BaekjoonOnlineJudge("https://www.acmicpc.net/problem/");

    private String baseSearchURL;

    private SiteList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }
}
