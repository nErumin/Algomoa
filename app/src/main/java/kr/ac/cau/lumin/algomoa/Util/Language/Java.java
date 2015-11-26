package kr.ac.cau.lumin.algomoa.Util.Language;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lumin on 2015-11-26.
 */
public class Java extends Language {
    private static Java language;

    public static synchronized Java getInstance() {
        if (language == null) {
            language = new Java();
        }

        return language;
    }

    private Java() {

    }

    @Override
    public String getLanguageName() {
        return LanguageList.Java.toString();
    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        ArrayList<LanguageRefer> javaReferences = new ArrayList<>();
        Source source = new Source(htmlContent);
        List<Element> elements = source.getAllElements(HTMLElementName.LI);

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            List<Element> classElems = element.getAllElements(HTMLElementName.A);

            for (int j = 0; j < classElems.size(); j++) {
                String hrefAttrValue = classElems.get(j).getAttributeValue("href");
                TextExtractor extractor = classElems.get(j).getTextExtractor();
                javaReferences.add(new LanguageRefer(LanguageList.Java, extractor.toString(), hrefAttrValue));
            }
        }

        return javaReferences;
    }

    @Override
    public String getRequestURL() {
        return LanguageList.Java.getBaseSearchURL() + "allclasses-frame.html";
    }
}
