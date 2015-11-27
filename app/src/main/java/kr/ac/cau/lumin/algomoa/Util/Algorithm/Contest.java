package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import java.util.Date;

/**
 * Created by Lumin on 2015-11-27.
 */
public class Contest {
    private int id;
    private String name;
    private Date startTime;

    public Contest(int contestID, String contestName, Date contestStartTime) {
        this.id = contestID;
        this.name = contestName;
        this.startTime = contestStartTime;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getStartTime() {
        return this.startTime;
    }
}
