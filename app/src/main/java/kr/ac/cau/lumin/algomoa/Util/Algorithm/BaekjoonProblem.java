package kr.ac.cau.lumin.algomoa.Util.Algorithm;

/**
 * Created by Lumin on 2015-11-25.
 */
public class BaekjoonProblem extends Problem {
    public BaekjoonProblem(int problemNumber, String problemName) {
        super(SiteList.BaekjoonOnlineJudge, problemNumber, problemName);
    }

    @Override
    public String getRequestURL() {
        return SiteList.BaekjoonOnlineJudge.getBaseSearchURL() + super.problemNumber;
    }
}
