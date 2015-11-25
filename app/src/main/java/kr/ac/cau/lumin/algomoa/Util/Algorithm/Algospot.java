package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lumin on 2015-11-26.
 */
public class Algospot extends AlgorithmSite {
    private static Algospot siteInstance;
    private static final int MAX_PROBLEMSET = 3;

    public static synchronized Algospot getInstance() {
        if (siteInstance == null) {
            siteInstance = new Algospot();
        }

        return siteInstance;
    }

    private Algospot() {
        super(SiteList.Algospot);
    }

    @Override
    public int getMaxProblemSet() {
        return MAX_PROBLEMSET;
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        Source htmlSource = new Source(htmlContent);
        List<Element> elementList = htmlSource.getAllElements(HTMLElementName.TD);

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Problem> problemList = new ArrayList<>();

        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            String attrValue = element.getAttributeValue("class");

            if (attrValue != null && attrValue.equals("id")) {
                TextExtractor extractor = element.getTextExtractor();
                nameList.add(extractor.toString());
            }
        }

        for (int i = 0; i < nameList.size(); i++) {
            problemList.add(new AlgospotProblem(super.maxProblemNum + 1, nameList.get(i)));
            super.maxProblemNum++;
        }

        return problemList;
    }

    @Override
    public String getRequestURL() {
        return SiteList.Algospot.getBaseSearchURL() + "judge/problem/list/";
    }
}
