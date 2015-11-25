package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.util.Log;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.io.InvalidObjectException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;

/**
 * Created by Lumin on 2015-11-25.
 */
public class BaekjoonOnlineJudge extends AlgorithmSite {
    private static BaekjoonOnlineJudge siteInstance;

    public synchronized static BaekjoonOnlineJudge getInstance() {
        if (siteInstance == null) {
            siteInstance = new BaekjoonOnlineJudge();
        }

        return siteInstance;
    }

    private BaekjoonOnlineJudge() {
        super(SiteList.BaekjoonOnlineJudge);
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        Source htmlSource = new Source(htmlContent);
        List<Element> numberElementList = htmlSource.getAllElements(HTMLElementName.TD);
        List<Element> nameElementList = htmlSource.getAllElements(HTMLElementName.A);

        ArrayList<Integer> problemNumList = new ArrayList<>();
        ArrayList<String> problemNameList = new ArrayList<>();
        ArrayList<BaekjoonProblem> problemList = new ArrayList<>();

        for (int i = 0; i < numberElementList.size(); i++) {
            Element numberElement = numberElementList.get(i);
            String numberAttrValue = numberElement.getAttributeValue("class");

            if (numberAttrValue != null && numberAttrValue.equals("list_problem_id")) {
                TextExtractor extractor = numberElement.getTextExtractor();
                problemNumList.add(Integer.parseInt(extractor.toString()));
            }
        }

        for (int i = 0; i < nameElementList.size(); i++) {
            Element nameElement = nameElementList.get(i);
            String nameAttrValue = nameElement.getAttributeValue("href");

            if (nameAttrValue != null && nameAttrValue.startsWith("/problem/")) {
                try {
                    Integer.parseInt(nameAttrValue.substring("/problem/".length(), nameAttrValue.length()));
                    TextExtractor extractor = nameElement.getTextExtractor();
                    problemNameList.add(extractor.toString());
                } catch (NumberFormatException e) {
                    Log.e("Jericho Exception", nameAttrValue);
                    continue;
                }
            }
        }

        for (int i = 0; i < problemNumList.size(); i++) {
            problemList.add(new BaekjoonProblem(problemNumList.get(i), problemNameList.get(i)));
        }

        return problemList;
    }
}