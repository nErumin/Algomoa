package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Parsable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-24.
 */
public class Codeforces extends AlgorithmSite implements Transmittable, Parsable<Problem> {
    private static String CODEFORCES_API = "http://codeforces.com/api/";
    private static Codeforces siteInstance;

    public static synchronized Codeforces getInstance() {
        if (siteInstance == null) {
            siteInstance = new Codeforces();
        }

        return siteInstance;
    }

    private Codeforces() {
        super(SiteList.Codeforces);
    }

    @Override
    public String getRequestURL() {
        Log.e("Codeforces", CODEFORCES_API + APIList.CodeforcesProblem.getBaseSearchURL());
        return CODEFORCES_API + APIList.CodeforcesProblem.getBaseSearchURL();
    }

    @Override
    public ArrayList<Problem> parseJSONObject(String content) {
        ArrayList<Problem> codeforceProblemList = new ArrayList<>();

        try {
            JSONArray problemSetArray = new JSONObject(content).getJSONObject("result").getJSONArray("problems");
            for (int i = 0; i < problemSetArray.length(); i++) {
                JSONObject problemJSONObject = problemSetArray.getJSONObject(i);
                int problemNumber = problemJSONObject.getInt("contestId");
                String problemIndex = problemJSONObject.getString("index");
                String problemName = problemJSONObject.getString("name");
                codeforceProblemList.add(new CodeforcesProblem(problemNumber, problemIndex, problemName));
                Log.e("CodeForces Parse", problemNumber + " " + problemIndex + " " + problemName);
            }
        } catch (JSONException e) {
            Log.e("Exception", e.getMessage());
        }

        return codeforceProblemList;
    }

    @Override
    public String[] crawlContentFromHtml(String htmlContent) {
        return new String[0];
    }
}
