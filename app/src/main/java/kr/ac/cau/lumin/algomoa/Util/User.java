package kr.ac.cau.lumin.algomoa.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.AlgorithmSite;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;

/**
 * Created by Lumin on 2015-11-21.
 */
public class User {
    private static User user;
    private ArrayList<LanguageList> favoriteLanguageList;
    private ArrayList<SiteList> favoriteAlgorithmList;

    public synchronized static User getInstance() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    private User()
    {
        this.favoriteAlgorithmList = new ArrayList<>();
        this.favoriteLanguageList = new ArrayList<>();
    }

    public void addNewFavoriteSite(SiteList newSite) {
        this.favoriteAlgorithmList.add(newSite);
    }

    public void deleteFavoriteSite(SiteList deleteSiteList) throws InvalidObjectException {
        for (int i = 0; i < this.favoriteAlgorithmList.size(); i++) {
            SiteList favoriteSite = this.favoriteAlgorithmList.get(i);
            if (favoriteSite.toString().equals(deleteSiteList.toString())) {
                this.favoriteAlgorithmList.remove(i);
                return;
            }
        }

        throw new InvalidObjectException("Cannot find that site name in list.");
    }

    public void addNewFavoriteLanguage(LanguageList newLanguage) {
        this.favoriteLanguageList.add(newLanguage);
    }

    public void deleteFavoriteLanguage(LanguageList deleteLanguage) throws InvalidObjectException {
        for (int i = 0; i < this.favoriteLanguageList.size(); i++) {
            LanguageList favoriteLanguage = this.favoriteLanguageList.get(i);
            if (favoriteLanguage.toString().equals(deleteLanguage.toString())) {
                this.favoriteLanguageList.remove(i);
                return;
            }
        }

        throw new InvalidObjectException("Cannot find that language in the list.");
    }

    public void changeFavoriteSite(Collection<SiteList> siteCollection) {
        this.favoriteAlgorithmList = new ArrayList<>(siteCollection);
    }

    public void changeFavoriteLanguage(Collection<LanguageList> languageCollection) {
        this.favoriteLanguageList = new ArrayList<>(languageCollection);
    }

    public ArrayList<SiteList> copyFavoriteAlgorithmSite() {
        return new ArrayList<>(this.favoriteAlgorithmList);
    }

    public ArrayList<LanguageList> copyFavoriteLanguage() {
        return new ArrayList<>(this.favoriteLanguageList);
    }

    public void setUserFavoriteInfomation(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        for (LanguageList language : LanguageList.fetchAllLanguageList()) {
            boolean result = prefs.getBoolean(language.toString(), false);
            if (result) {
                User.getInstance().addNewFavoriteLanguage(language);
            }
        }

        for (SiteList site : SiteList.fetchAllSiteList()) {
            boolean result = prefs.getBoolean(site.toString(), false);
            if (result) {
                User.getInstance().addNewFavoriteSite(site);
            }
        }
    }
}