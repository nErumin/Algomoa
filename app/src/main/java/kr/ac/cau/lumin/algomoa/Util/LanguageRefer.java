package kr.ac.cau.lumin.algomoa.Util;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by CAUCSE on 2015-11-23.
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
