package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by CAUCSE on 2015-11-24.
 */
public class CodeforcesProblem extends Problem {
    private String problemIndex;
    private static String BASIC_PROBLEM_URL = "problemset/problem/";

    public CodeforcesProblem(int problemNumber, String problemIndex, String problemName) {
        super(SiteList.Codeforces, problemNumber, problemName);
        this.problemIndex = problemIndex;
    }

    @Override
    public String getRequestURL() {
        StringBuilder requestUrlBuilder = new StringBuilder(SiteList.Codeforces.getBaseSearchURL() + BASIC_PROBLEM_URL);
        requestUrlBuilder.append(super.problemNumber + Transmittable.SPERATOR);
        requestUrlBuilder.append(this.problemIndex + Transmittable.SPERATOR);

        return requestUrlBuilder.toString();
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        // TODO : Crawling
        return null;
    }

    @Override
    public int compareTo(Object another) {
        CodeforcesProblem anotherProblem = (CodeforcesProblem) another;
        if (anotherProblem.problemNumber != this.problemNumber) {
            return super.compareTo(another);
        } else {
            return this.problemIndex.compareTo(anotherProblem.problemIndex);
        }
    }

    public String getProblemIndex() {
        return problemIndex;
    }
}
