package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-21.
 */
public abstract class Problem implements Comparable, Transmittable, Crawlable {
    private SiteList siteList;
    protected String problemName;
    protected int problemNumber;

    public Problem(SiteList siteList, int problemNumber, String problemName) {
        this.siteList = siteList;
        this.problemNumber = problemNumber;
        this.problemName = problemName;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Problem) {
            Problem anotherProblem = (Problem) another;
            if (this.problemNumber == anotherProblem.problemNumber) {
                return 0;
            }

            return this.problemNumber < anotherProblem.problemNumber ? -1 : 1;
        } else {
            return 0;
        }
    }

    public int getProblemNumber() {
        return this.problemNumber;
    }

    public String getSiteList() {
        return this.siteList.toString();
    }

    public String getProblemName() {
        return this.problemName;
    }
}
