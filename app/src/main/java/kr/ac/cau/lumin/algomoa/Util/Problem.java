package kr.ac.cau.lumin.algomoa.Util;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-21.
 */
public class Problem implements Comparable, Transmittable {
    private int problemNumber;
    private SiteName siteName;
    private String problemName;

    public Problem(SiteName siteName, int problemNumber, String problemName) {
        this.siteName = siteName;
        this.problemNumber = problemNumber;
        this.problemName = problemName;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Problem) {
            Problem anotherProblem = (Problem) another;

            if (this.problemNumber == anotherProblem.problemNumber) {
                return 0;
            } else {
                return (this.problemNumber > anotherProblem.problemNumber) ? 1 : -1;
            }
        } else {
            return 0;
        }
    }

    @Override
    public String getRequestURL() {
        StringBuilder requestUrlBuilder = new StringBuilder(this.siteName.getBaseSearchURL());

        if (this.siteName.needContainsNumber()) {
            requestUrlBuilder.append(this.getProblemNumber() + Transmittable.SPERATOR);
        }

        if (this.siteName.needContainsName()) {
            requestUrlBuilder.append(this.getProblemName() + Transmittable.SPERATOR);
        }

        return this.siteName.getBaseSearchURL();
    }

    public int getProblemNumber() {
        return this.problemNumber;
    }

    public String getSiteName() {
        return this.siteName.toString();
    }

    public String getProblemName() {
        return this.problemName;
    }
}
