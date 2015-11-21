package kr.ac.cau.lumin.algomoa.Util;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum SiteName {
    Codeforces("http://codeforces.com/problemset/problem/", true, true),
    Algospot("https://algospot.com/judge/problem/read/", true, false),
    BaekjoonOnlineJudge("https://www.acmicpc.net/problem/", false, true);

    private String baseSearchURL;
    private boolean needContainsNumber;
    private boolean needContainsName;

    private SiteName(String baseSearchURL, boolean needContainsName, boolean needContainsNumber) {
        this.baseSearchURL = baseSearchURL;
        this.needContainsName = needContainsName;
        this.needContainsNumber = needContainsNumber;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }

    public boolean needContainsNumber() {
        return this.needContainsNumber;
    }

    public boolean needContainsName() {
        return this.needContainsName;
    }
}
