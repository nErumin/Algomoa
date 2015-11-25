package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.util.Log;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-21.
 */
public abstract class AlgorithmSite implements Crawlable, Transmittable {
    private ArrayList<Problem> containedProblems;
    private SiteList name;

    public AlgorithmSite(SiteList siteList) {
        this.name = siteList;
        this.containedProblems = new ArrayList<>();
    }

    public void addProblem(Problem problem) throws InvalidObjectException {
        if (this.name.toString() != problem.getSiteList().toString()) {
            throw new InvalidObjectException("Different Name.");
        }

        this.containedProblems.add(problem);
        Collections.sort(this.containedProblems);
    }

    public void addProblem(Collection<Problem> problems) throws InvalidObjectException {
        this.containedProblems.addAll(problems);
        Collections.sort(this.containedProblems);
    }

    public void deleteProblem(int problemNumber) throws InvalidObjectException {
        Problem targetProblem = this.findProblem(problemNumber);

        if (targetProblem == null) {
            throw new InvalidObjectException("Cannot find problem in this site that has this problem Number : " + problemNumber);
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

    public SiteList getSiteName() {
        return this.name;
    }

    public Problem[] getContainedProblems() {
        return this.containedProblems.toArray(new Problem[this.containedProblems.size()]);
    }
}