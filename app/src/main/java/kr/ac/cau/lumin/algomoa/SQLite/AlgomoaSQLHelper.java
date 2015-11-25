package kr.ac.cau.lumin.algomoa.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Hashtable;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;

/**
 * Created by Lumin on 2015-11-23.
 */
public class AlgomoaSQLHelper extends SQLiteOpenHelper {
    private static AlgomoaSQLHelper sqlHelper;
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "AlgomoaDataBase";

    private static final String CREATE_PROBLEM_TABLE = "";

    private static final String TABLE_PROBLEM = "Problem";
    private static final String COLUMN_PROBLEM_SITE = "site_name";
    private static final String COLUMN_PROBLEM_CODE = "prob_code";
    private static final String COLUMN_PROBLEM_NAME = "prob_name";
    private static final String COLUMN_PROBLEM_URL = "url";

    public static synchronized AlgomoaSQLHelper getInstance(Context context) {
        if (sqlHelper == null) {
            sqlHelper = new AlgomoaSQLHelper(context);
        }

        return sqlHelper;
    }

    public AlgomoaSQLHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, DATABASE_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PROBLEM_TABLE);
        //sqLiteDatabase.execSQL(TableEnum.CREATE_REFERENCE_TABLE.getMappingValue());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Problem");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Reference");

        // TODO : Check
        onCreate(sqLiteDatabase);
    }

    public String getProblemURL(SiteList site, String problemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_PROBLEM_URL + " FROM " + TABLE_PROBLEM +
                " WHERE " + COLUMN_PROBLEM_NAME + " = " + site.toString() + " AND " + COLUMN_PROBLEM_CODE + " = " + problemCode;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getString(cursor.getColumnIndex(COLUMN_PROBLEM_URL));
    }

    public long addProblem(Problem problem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String problemCode = Integer.toString(problem.getProblemNumber());

        if (problem instanceof CodeforcesProblem) {
            problemCode += ((CodeforcesProblem) problem).getProblemIndex();
        }

        values.put(COLUMN_PROBLEM_SITE, problem.getSiteList().toString());
        values.put(COLUMN_PROBLEM_CODE, problemCode);
        values.put(COLUMN_PROBLEM_NAME, problem.getProblemName());
        values.put(COLUMN_PROBLEM_URL, problem.getRequestURL());

        long rowID = db.insert(TABLE_PROBLEM, null, values);
        return rowID;
    }
}
