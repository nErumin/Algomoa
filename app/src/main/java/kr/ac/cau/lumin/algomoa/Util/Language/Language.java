package kr.ac.cau.lumin.algomoa.Util.Language;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by CAUCSE on 2015-11-26.
 */
public abstract class Language implements Crawlable, Transmittable {
    public abstract String getLanguageName();
}
