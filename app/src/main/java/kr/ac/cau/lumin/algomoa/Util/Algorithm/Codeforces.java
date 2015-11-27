package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import kr.ac.cau.lumin.algomoa.Network.Crawlable;
import kr.ac.cau.lumin.algomoa.Network.Parsable;
import kr.ac.cau.lumin.algomoa.Network.Transmittable;

/**
 * Created by Lumin on 2015-11-24.
 */
public class Codeforces extends AlgorithmSite implements Transmittable, Parsable {
    private static Codeforces siteInstance;
    private APIList usingAPI;

    public static synchronized Codeforces getInstance() {
        if (siteInstance == null) {
            siteInstance = new Codeforces();
        }

        return siteInstance;
    }

    private Codeforces() {
        super(SiteList.Codeforces);
        this.usingAPI = APIList.CodeforcesProblem;
    }

    /**
     * 문제 리스트를 요청하는 API URL을 받아옵니다.
     * @return 리스트 요청 API URL입니다.
     */
    @Override
    public String getRequestURL() {
        Log.e("CodeforcesRequestURL", SiteList.Codeforces.getBaseSearchURL() + this.usingAPI);
        return SiteList.Codeforces.getBaseSearchURL() + this.usingAPI.getBaseSearchURL();
    }

    @Override
    public void setAPIList(APIList api) {
        this.usingAPI = api;
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
                Log.e("CodeForces Problem Parse", problemNumber + " " + problemIndex + " " + problemName);
            }
        } catch (JSONException e) {
            Log.e("Exception", e.getMessage());
        }

        return codeforceProblemList;
    }

    public ArrayList<Contest> parseContestJSONObject(String content) {
        ArrayList<Contest> codeforceContestList = new ArrayList<>();

        try {
            JSONArray contestSetArray = new JSONObject(content).getJSONArray("result");
            for (int i = 0; i < contestSetArray.length(); i++) {
                JSONObject contestJSONObject = contestSetArray.getJSONObject(i);

                if (contestJSONObject.getString("phase").equals("FINISHED")) {
                    break;
                }

                int contestID = contestJSONObject.getInt("id");
                String contestName = contestJSONObject.getString("name");
                Date contestStartTime = new Date(contestJSONObject.getLong("startTimeSeconds") * 1000L);
                codeforceContestList.add(new Contest(contestID, contestName, contestStartTime));
                Log.e("CodeForces Contest Parse", contestID + " " + contestName + " " + contestStartTime.toString());
            }
        } catch (JSONException e) {
            Log.e("Exception", e.getMessage());
        }

        return codeforceContestList;
    }

    @Override
    public String[] crawlContentFromHtml(String htmlContent) {
        return new String[0];
    }
}
