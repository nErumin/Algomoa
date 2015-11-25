package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin 2015-11-24.
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
        Source htmlSource = new Source(htmlContent);
        List<Element> elementList = htmlSource.getAllElements(HTMLElementName.DIV);
        Hashtable<String, ArrayList<String>> infoTable = new Hashtable<>();
        infoTable.put("problem_description", new ArrayList<String>());
        infoTable.put("problem_input", new ArrayList<String>());
        infoTable.put("problem_output", new ArrayList<String>());
        infoTable.put("sample_input_1", new ArrayList<String>());
        infoTable.put("sample_output_1", new ArrayList<String>());

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("id");
            TextExtractor extractor = element.getTextExtractor();
            if (attrValue != null) {
                attrValue = attrValue.replaceAll("-", "_");
                if (infoTable.containsKey(attrValue)) {
                    infoTable.get(attrValue).add(extractor.toString());
                }
            }
        }

        return infoTable;
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