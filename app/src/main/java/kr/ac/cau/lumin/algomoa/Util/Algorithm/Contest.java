package kr.ac.cau.lumin.algomoa.Util.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public String getStartTimeInString() {
        StringBuilder timeBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startTime);
        timeBuilder.append(calendar.get(Calendar.YEAR) + "년 ");
        timeBuilder.append(calendar.get(Calendar.MONTH) + "월 ");
        timeBuilder.append(calendar.get(Calendar.DATE) + "일 ");
        timeBuilder.append(calendar.get(Calendar.HOUR) + "시 ");
        timeBuilder.append(calendar.get(Calendar.MINUTE) + "분에 시작합니다.");

        return timeBuilder.toString();
    }
}
