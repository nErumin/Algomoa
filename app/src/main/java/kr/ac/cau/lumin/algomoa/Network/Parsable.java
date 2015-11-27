package kr.ac.cau.lumin.algomoa.Network;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Lumin on 2015-11-24.
 */
public interface Parsable extends Transmittable {
    public ArrayList parseJSONObject(String content);
    // TODO : Html Parse
}
