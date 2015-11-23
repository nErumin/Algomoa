package kr.ac.cau.lumin.algomoa.Util;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Lumin on 2015-11-21.
 */
public class User {
    private ArrayList<LanguageList> favoriteLanguageList;
    private ArrayList<AlgorithmSite> favoriteAlgorithmList;

    public User()
    {
        this.favoriteAlgorithmList = new ArrayList<>();
        this.favoriteLanguageList = new ArrayList<>();
    }

    public User(Collection<AlgorithmSite> siteCollection, Collection<LanguageList> languageCollection) {
        this.favoriteAlgorithmList = new ArrayList<>(siteCollection);
        this.favoriteLanguageList = new ArrayList<>(languageCollection);
    }

    public void addNewFavoriteSite(AlgorithmSite newSite) {
        this.favoriteAlgorithmList.add(newSite);
    }

    public void deleteFavoriteSite(SiteName deleteSiteName) throws InvalidObjectException {
        for (int i = 0; i < this.favoriteAlgorithmList.size(); i++) {
            AlgorithmSite favoriteSite = this.favoriteAlgorithmList.get(i);
            if (favoriteSite.getSiteName().toString().equals(deleteSiteName.toString())) {
                this.favoriteAlgorithmList.remove(i);
                break;
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
                break;
            }
        }

        throw new InvalidObjectException("Cannot find that language in the list.");
    }

    public void changeFavoriteSite(Collection<AlgorithmSite> siteCollection) {
        this.favoriteAlgorithmList = new ArrayList<>(siteCollection);
    }

    public void changeFavoriteLanguage(Collection<LanguageList> languageCollection) {
        this.favoriteLanguageList = new ArrayList<>(languageCollection);
    }

    public ArrayList<AlgorithmSite> copyFavoriteAlgorithmSite() {
        return new ArrayList<>(this.favoriteAlgorithmList);
    }

    public ArrayList<LanguageList> copyFavoriteLanguage() {
        return new ArrayList<>(this.favoriteLanguageList);
    }
}