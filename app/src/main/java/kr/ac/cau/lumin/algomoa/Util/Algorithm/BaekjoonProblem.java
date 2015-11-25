package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lumin on 2015-11-25.
 */
public class BaekjoonProblem extends Problem {
    private static String BASIC_PROBLEM_URL = "problem/";

    public BaekjoonProblem(int problemNumber, String problemName) {
        super(SiteList.BaekjoonOnlineJudge, problemNumber, problemName);
    }

    @Override
    public String getRequestURL() {
        return SiteList.BaekjoonOnlineJudge.getBaseSearchURL() + BASIC_PROBLEM_URL + super.problemNumber;
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
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
}
