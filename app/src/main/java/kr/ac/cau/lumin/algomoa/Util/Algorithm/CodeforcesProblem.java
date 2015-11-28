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
        Hashtable<String, String> infoTable = new Hashtable<>();

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("class");
            TextExtractor extractor = element.getTextExtractor();

            if (attrValue != null) {
                switch (attrValue) {
                    case "problem-statement":
                        infoTable.put("problem_description", extractor.toString());
                        break;
                    case "input-specification":
                        infoTable.put("problem_input", extractor.toString());
                        break;
                    case "output-specification":
                        infoTable.put("problem_output", extractor.toString());
                        break;
                    case "input":
                        if (!infoTable.containsKey("sample_input_1")) {
                            infoTable.put("sample_input_1", extractor.toString());
                        }
                        break;
                    case "output":
                        if (!infoTable.containsKey("sample_output_1")) {
                            infoTable.put("sample_output_1", extractor.toString());
                        }
                        break;
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