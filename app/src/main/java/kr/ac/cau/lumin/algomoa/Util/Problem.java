package kr.ac.cau.lumin.algomoa.Util;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-21.
 */
public abstract class Problem implements Comparable, Transmittable {
    private SiteList siteList;
    protected String problemName;
    protected String problemNumber;

    public Problem(SiteList siteList, String problemNumber, String problemName) {
        this.siteList = siteList;
        this.problemNumber = problemNumber;
        this.problemName = problemName;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Problem) {
            Problem anotherProblem = (Problem) another;
            return this.problemNumber.compareTo(anotherProblem.getProblemNumber());
        } else {
            return 0;
        }
    }

    public String getProblemNumber() {
        return this.problemNumber;
    }

    public String getSiteList() {
        return this.siteList.toString();
    }

    public String getProblemName() {
        return this.problemName;
    }
}
