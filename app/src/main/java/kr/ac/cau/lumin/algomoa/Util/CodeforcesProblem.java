package kr.ac.cau.lumin.algomoa.Util;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by CAUCSE on 2015-11-24.
 */
public class CodeforcesProblem extends Problem {
    private String problemIndex;

    public CodeforcesProblem(String problemNumber, String problemName) {
        super(SiteList.Codeforces, problemNumber, problemName);
        this.splitProblemNumber();
    }

    private void splitProblemNumber() {
        for (int i = 0; i < super.problemNumber.length(); i++) {
            if (super.problemNumber.charAt(i) > '9') {
                this.problemIndex = super.problemNumber.substring(i, super.problemNumber.length());
                super.problemNumber = super.problemNumber.substring(0, i);
                break;
            }
        }
    }

    @Override
    public String getRequestURL() {
        StringBuilder requestUrlBuilder = new StringBuilder(SiteList.Codeforces.getBaseSearchURL());
        requestUrlBuilder.append(super.problemNumber + Transmittable.SPERATOR);
        requestUrlBuilder.append(this.problemIndex + Transmittable.SPERATOR);

        return requestUrlBuilder.toString();
    }

    @Override
    public String getProblemName() {
        return super.problemName + this.problemIndex;
    }
}
