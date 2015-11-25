package kr.ac.cau.lumin.algomoa.Util.Language;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;

import java.util.ArrayList;
import java.util.List;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-26.
 */
public class Ruby implements Transmittable, Crawlable{
    private static Ruby languageInst;

    public synchronized static Ruby getInstance() {
        if (languageInst == null) {
            languageInst = new Ruby();
        }

        return languageInst;
    }

    private Ruby() {

    }

    @Override
    public Object crawlContentFromHtml(String htmlContent) {
        ArrayList<LanguageRefer> rubyReferences = new ArrayList<>();
        Source source = new Source(htmlContent);
        List<Element> elements = source.getAllElements(HTMLElementName.DIV);

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            String attrValue = element.getAttributeValue("id");

            if (attrValue != null && attrValue.equals("class-index")) {
                List<Element> classElems = element.getAllElements(HTMLElementName.A);

                for (int j = 0; j < classElems.size(); j++) {
                    TextExtractor extractor = classElems.get(i).getTextExtractor();
                    rubyReferences.add(new LanguageRefer(LanguageList.Ruby, extractor.toString()));
                }
            }
        }

        return rubyReferences;
    }

    @Override
    public String getRequestURL() {
        return LanguageList.Ruby.getBaseSearchURL();
    }
}
