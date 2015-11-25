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
        Hashtable<String, ArrayList<String>> infoTable = new Hashtable<>();
        infoTable.put("problem_description", new ArrayList<String>());
        infoTable.put("problem_input", new ArrayList<String>());
        infoTable.put("problem_output", new ArrayList<String>());
        infoTable.put("sample_input_1", new ArrayList<String>());
        infoTable.put("sample_output_1", new ArrayList<String>());

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("class");
            TextExtractor extractor = element.getTextExtractor();

            if (attrValue != null) {

                switch (attrValue) {
                    case "problem_statement":
                        infoTable.get("problem_description").add(extractor.toString());
                        break;
                    case "problem_input":

                        infoTable.get("problem_input").add(extractor.toString());
                        break;
                    case "problem_output":

                        infoTable.get("problem_output").add(extractor.toString());
                        break;
                    case "problem_sample_input":

                        infoTable.get("sample_input_1").add(extractor.toString());
                        break;
                    case "problem_sample_output":

                        infoTable.get("sample_output_1").add(extractor.toString());
                        break;
                }
            }

        }

        return infoTable;
    }
}
