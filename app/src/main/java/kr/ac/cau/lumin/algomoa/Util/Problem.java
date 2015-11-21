package kr.ac.cau.lumin.algomoa.Util;

import java.io.InvalidObjectException;

/**
 * Created by Lumin on 2015-11-21.
 */
public class Problem implements Comparable {
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
