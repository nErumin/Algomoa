package kr.ac.cau.lumin.algomoa.Util;

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

    public void addNewFavoriteSite(AlgorithmSite newSite) {
        this.favoriteAlgorithmList.add(newSite);
    }

    public void changeFavortiteSite(Collection<AlgorithmSite> siteCollection) {
        this.favoriteAlgorithmList = new ArrayList<>(siteCollection);
    }

    public ArrayList<AlgorithmSite> copyFavoriteAlgorithmSite() {
        return new ArrayList<>(this.favoriteAlgorithmList);
    }
    
    public ArrayList<LanguageList> copyFavoriteLanguage() {
        return new ArrayList<>(this.favoriteLanguageList);
    }
}