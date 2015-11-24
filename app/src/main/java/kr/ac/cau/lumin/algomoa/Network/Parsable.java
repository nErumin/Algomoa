package kr.ac.cau.lumin.algomoa.Network;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lumin on 2015-11-24.
 */
public interface Parsable<T> {
    public ArrayList<T> parseJSONObject(String content);
    // TODO : Html Parse
}
