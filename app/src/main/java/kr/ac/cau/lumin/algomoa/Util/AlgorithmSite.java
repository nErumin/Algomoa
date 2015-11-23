package kr.ac.cau.lumin.algomoa.Util;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Lumin on 2015-11-21.
 */
public class AlgorithmSite {
    private ArrayList<Problem> containedProblems;
    private SiteName name;

    public AlgorithmSite(SiteName siteName) {
        this.name = siteName;
    }

    public void addProblem(Problem problem) {
        this.containedProblems.add(problem);
        Collections.sort(this.containedProblems);
    }

    public void deleteProblem(int problemNumber) throws Resources.NotFoundException {
        Problem targetProblem = this.findProblem(problemNumber);

        if (targetProblem == null) {
            throw new Resources.NotFoundException("Cannot find problem in this site that has this problem Number : " + problemNumber);
        }

        this.containedProblems.remove(targetProblem);
    }

    public Problem findProblem(int problemNumber) {
        Problem foundProblem = null;

        for (Problem problem : this.containedProblems) {
            if (problem.getProblemNumber() == problemNumber) {
                foundProblem = problem;
            }
        }

        return foundProblem;
    }

    public SiteName getSiteName() {
        return this.name;
    }

    public Problem[] getContainedProblems() {
        return (Problem[]) this.containedProblems.toArray();
    }
}