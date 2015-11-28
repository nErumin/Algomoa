package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lumin on 2015-11-26.
 */
public class AlgospotProblem extends Problem {
    private static String BASIC_PROBLEM_URL = "judge/problem/read/";

    public AlgospotProblem(int problemNumber, String problemName) {
        super(SiteList.Algospot, problemNumber, problemName);
    }

    @Override
    public String getRequestURL() {
        return SiteList.Algospot.getBaseSearchURL() + BASIC_PROBLEM_URL + super.problemName;
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        Source htmlSource = new Source(htmlContent);
        List<Element> elementList = htmlSource.getAllElements(HTMLElementName.SECTION);
        Hashtable<String, String> infoTable = new Hashtable<>();

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("class");
            TextExtractor extractor = element.getTextExtractor();

            if (attrValue != null) {

                switch (attrValue) {
                    case "problem_statement":
                        infoTable.put("problem_description", extractor.toString());
                        break;
                    case "problem_input":
                        infoTable.put("problem_input", extractor.toString());
                        break;
                    case "problem_output":
                        infoTable.put("problem_output", extractor.toString());
                        break;
                    case "problem_sample_input":
                        infoTable.put("sample_input_1", extractor.toString());
                        break;
                    case "problem_sample_output":
                        infoTable.put("sample_output_1", extractor.toString());
                        break;
                }
            }

        }

        return infoTable;
    }
}
