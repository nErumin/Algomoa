package kr.ac.cau.lumin.algomoa.Util.Language;

import kr.ac.cau.lumin.algomoa.Network.Transmittable;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;

/**
 * Created by Lumin on 2015-11-23.
 */
public class LanguageRefer implements Transmittable {
    private LanguageList language;
    private String referenceName;
    private String url;


    public LanguageRefer(LanguageList language, String referenceName, String url)
    {
        this.language = language;
        this.referenceName = referenceName;
        this.url = url;
    }

    @Override
    public String getRequestURL() {
        return language.getBaseSearchURL() + url;
    }

    public LanguageList getLanguage() {
        return this.language;
    }

    public String getReferenceName() {
        return this.referenceName;
    }
}
