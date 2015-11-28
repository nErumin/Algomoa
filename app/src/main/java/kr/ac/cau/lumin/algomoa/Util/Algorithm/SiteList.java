package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.cau.lumin.algomoa.R;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum SiteList {
    Codeforces("http://codeforces.com/"),
    Algospot("https://algospot.com/"),
    BaekjoonOnlineJudge("https://www.acmicpc.net/");

    private String baseSearchURL;

    private SiteList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }

    public static ArrayList<SiteList> fetchAllSiteList() {
        return new ArrayList<>(Arrays.asList(SiteList.class.getEnumConstants()));
    }

    public Drawable fetchDrawable(Context context) {
        switch (this) {
            case Codeforces:
            {
                return context.getResources().getDrawable(R.drawable.codeforce_ic);
            }
            case Algospot:
            {
                return context.getResources().getDrawable(R.drawable.algospot_ic);
            }
            case BaekjoonOnlineJudge:
            {
                return context.getResources().getDrawable(R.drawable.baekjoon_logo_ic);
            }
        }

        return null;
    }
}
