package kr.ac.cau.lumin.algomoa.Util.Algorithm;

/**
 * Created by Lumin on 2015-11-25.
 */
public enum APIList {
    CodeforcesProblem("problemset.problems"), CodeforcesContest("");

    private String baseSearchURL;

    APIList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }
}
