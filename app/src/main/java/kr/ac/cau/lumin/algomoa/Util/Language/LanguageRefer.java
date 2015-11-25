package kr.ac.cau.lumin.algomoa.Util.Language;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;

/**
 * Created by Lumin on 2015-11-23.
 */
public class LanguageRefer implements Transmittable {
    private LanguageList language;
    private String referenceName;

    public LanguageRefer(LanguageList language, String referenceName)
    {
        this.language = language;
        this.referenceName = referenceName;
    }

    @Override
    public String getRequestURL() {
        return language.getBaseSearchURL() + referenceName.replaceAll(".", "/") + ".html";
    }
}
