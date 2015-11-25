package kr.ac.cau.lumin.algomoa.Util.Language;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum LanguageList {
    Ruby("http://ruby-doc.org/core-2.2.3/"), Java("http://docs.oracle.com/javase/7/docs/api/");

    private String baseSearchURL;

    private LanguageList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }
}
