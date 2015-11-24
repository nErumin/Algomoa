package kr.ac.cau.lumin.algomoa.Util;

import android.content.res.Resources;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Lumin on 2015-11-21.
 */
public class AlgorithmSite {
    private ArrayList<Problem> containedProblems;
    private SiteList name;

    public AlgorithmSite(SiteList siteList) {
        this.name = siteList;
    }

    public void addProblem(Problem problem) throws InvalidObjectException {
        if (this.name.toString() != problem.getSiteList().toString()) {
            throw new InvalidObjectException("Different Name.");
        }

        this.containedProblems.add(problem);
        Collections.sort(this.containedProblems);
    }

    public void deleteProblem(String problemNumber) throws Resources.NotFoundException {
        Problem targetProblem = this.findProblem(problemNumber);

        if (targetProblem == null) {
            throw new Resources.NotFoundException("Cannot find problem in this site that has this problem Number : " + problemNumber);
        }

        this.containedProblems.remove(targetProblem);
    }

    public Problem findProblem(String problemNumber) {
        Problem foundProblem = null;

        for (Problem problem : this.containedProblems) {
            if (problem.getProblemNumber().equals(problemNumber)) {
                foundProblem = problem;
            }
        }

        return foundProblem;
    }

    public SiteList getSiteName() {
        return this.name;
    }

    public Problem[] getContainedProblems() {
        return (Problem[]) this.containedProblems.toArray();
    }
}