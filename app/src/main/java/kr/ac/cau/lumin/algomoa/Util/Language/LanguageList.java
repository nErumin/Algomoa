package kr.ac.cau.lumin.algomoa.Util.Language;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.cau.lumin.algomoa.R;

/**
 * Created by Lumin on 2015-11-21.
 */
public enum LanguageList {
    Ruby("http://ruby-doc.org/core-2.2.3/"), Java("http://docs.oracle.com/javase/8/docs/api/");

    private String baseSearchURL;

    private LanguageList(String baseSearchURL) {
        this.baseSearchURL = baseSearchURL;
    }

    public String getBaseSearchURL() {
        return this.baseSearchURL;
    }

    public Drawable getLanguageDrawable(Context context) {
        switch (this) {
            case Ruby:
            {
                return context.getResources().getDrawable(R.drawable.java_logo_ic);
            }
            case Java:
            {
                return context.getResources().getDrawable(R.drawable.java_logo_ic);
            }
        }

        return null;
    }

    public static ArrayList<LanguageList> fetchAllLanguageList() {
        return new ArrayList<>(Arrays.asList(LanguageList.class.getEnumConstants()));
    }

    public Drawable fetchDrawable(Context context) {
        switch (this) {
            case Ruby:
            {
                return context.getResources().getDrawable(R.drawable.ruby_ic);
            }
            case Java:
            {
                return context.getResources().getDrawable(R.drawable.java_logo_ic);
            }
        }

        return null;
    }
}
