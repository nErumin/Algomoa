package kr.ac.cau.lumin.algomoa.Network;

import org.json.JSONException;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.APIList;

/**
 * Created by Lumin on 2015-11-24.
 */
public interface Parsable extends Transmittable {
    public void setAPIList(APIList api);
    public ArrayList parseJSONObject(String content);
    // TODO : Html Parse
}
