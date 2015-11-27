package kr.ac.cau.lumin.algomoa.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import kr.ac.cau.lumin.algomoa.Util.Algorithm.AlgospotProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.BaekjoonProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.CodeforcesProblem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.Problem;
import kr.ac.cau.lumin.algomoa.Util.Algorithm.SiteList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageList;
import kr.ac.cau.lumin.algomoa.Util.Language.LanguageRefer;

/**
 * Created by Lumin on 2015-11-23.
 */
public class AlgomoaSQLHelper extends SQLiteOpenHelper {
    private static AlgomoaSQLHelper sqlHelper;
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "AlgomoaDataBase";

    private static final String TABLE_PROBLEM = "Problem";
    private static final String TABLE_REFERENCE = "Reference";

    private static final String COLUMN_PROBLEM_SITE = "site_name";
    private static final String COLUMN_PROBLEM_CODE = "prob_code";
    private static final String COLUMN_PROBLEM_NAME = "prob_name";
    private static final String COLUMN_PROBLEM_URL = "url";

    private static final String COLUMN_REFERENCE_LANG_NAME = "lang_name";
    private static final String COLUMN_REFERENCE_REF_NAME = "class_name";
    private static final String COLUMN_REFERENCE_URL = "url";

    private static final String CREATE_PROBLEM_TABLE = "CREATE TABLE " + TABLE_PROBLEM + " ( " +
            COLUMN_PROBLEM_SITE + " varchar(10), " +
            COLUMN_PROBLEM_CODE + " varchar(20), " +
            COLUMN_PROBLEM_NAME + " varchar(20), " +
            COLUMN_PROBLEM_URL + " varchar(100), " +
            "PRIMARY KEY (" + COLUMN_PROBLEM_SITE + ", " + COLUMN_PROBLEM_CODE + " ) " + " )";

    private static final String CREATE_REFERENCE_TABLE = "CREATE TABLE " + TABLE_REFERENCE + " ( " +
            COLUMN_REFERENCE_LANG_NAME + " varchar(10), " +
            COLUMN_REFERENCE_REF_NAME + " varchar(50), " +
            COLUMN_REFERENCE_URL + " varchar(100), " +
            "PRIMARY KEY (" + COLUMN_REFERENCE_LANG_NAME + ", " + COLUMN_REFERENCE_REF_NAME + " ) " + " ) ";


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
        sqLiteDatabase.execSQL(CREATE_REFERENCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PROBLEM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REFERENCE);

        // TODO : Check
        onCreate(sqLiteDatabase);
    }

    public String getReferenceURL(LanguageList language, String refName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_REFERENCE_URL + " FROM " + TABLE_REFERENCE +
                " WHERE " + COLUMN_REFERENCE_LANG_NAME + " = '" + language.toString() + "' AND " + COLUMN_REFERENCE_REF_NAME + " = '" + refName + "'";
        Log.e("Database", "RefURL / RefURL Select Query : " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_URL));
    }

    public String getProblemURL(SiteList site, String problemCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT " + COLUMN_PROBLEM_URL + " FROM " + TABLE_PROBLEM +
                " WHERE " + COLUMN_PROBLEM_SITE + " = '" + site.toString() + "' AND " + COLUMN_PROBLEM_CODE + " = '" + problemCode + "'";
        Log.e("Database", "ProbURL / ProbURL Select Query : " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getString(cursor.getColumnIndex(COLUMN_PROBLEM_URL));
    }

    public long addReference(LanguageRefer refer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("Database", "AddRef / Lang : " + refer.getLanguage().toString() + " , Name : " + refer.getReferenceName() + " , Url : " + refer.getRequestURL());
        values.put(COLUMN_REFERENCE_LANG_NAME, refer.getLanguage().toString());
        values.put(COLUMN_REFERENCE_REF_NAME, refer.getReferenceName());
        values.put(COLUMN_REFERENCE_URL, refer.getRequestURL());

        long rowID = db.insert(TABLE_REFERENCE, null, values);

        Log.e("Database", "AddRef / Row ID : " + rowID);
        return rowID;
    }

    public long addProblem(Problem problem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e("Database", "AddProb / Number : " + problem.getProblemNumber() + " , Name : " + problem.getProblemName() + " , Url : " + problem.getRequestURL());
        String problemCode = Integer.toString(problem.getProblemNumber());

        if (problem instanceof CodeforcesProblem) {
            problemCode += ((CodeforcesProblem) problem).getProblemIndex();
        }

        values.put(COLUMN_PROBLEM_SITE, problem.getSiteList().toString());
        values.put(COLUMN_PROBLEM_CODE, problemCode);
        values.put(COLUMN_PROBLEM_NAME, problem.getProblemName());
        values.put(COLUMN_PROBLEM_URL, problem.getRequestURL());

        long rowID = db.insert(TABLE_PROBLEM, null, values);

        Log.e("Database", "AddProb / Row ID : " + rowID);
        return rowID;
    }

    public ArrayList<LanguageRefer> getAllReferences(LanguageList language) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<LanguageRefer> refers = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_REFERENCE + " WHERE " + COLUMN_REFERENCE_LANG_NAME + " = '" + language.toString() +"'";
        Log.e("Database", "AllRefer / Ref Select Query : " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String url = cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_URL));
                String className = cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_REF_NAME));

                refers.add(new LanguageRefer(language, className, url));
            } while (cursor.moveToNext());
        }

        return refers;
    }

    public ArrayList<Problem> getAllProblems(SiteList site) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Problem> problems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PROBLEM + " WHERE '" + COLUMN_PROBLEM_SITE + "' = '" + site.toString() + "'";
        Log.e("Database", "AllProb / Prob Select Query : " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String problemName = cursor.getString(cursor.getColumnIndex(COLUMN_PROBLEM_NAME));
                String problemCode = cursor.getString(cursor.getColumnIndex(COLUMN_PROBLEM_CODE));

                switch (site) {
                    case Codeforces:
                    {
                        int problemNumber = Integer.parseInt(problemCode.substring(0, problemCode.length() - 1));
                        String problemIndex = problemCode.substring(problemCode.length() - 1, problemCode.length());
                        problems.add(new CodeforcesProblem(problemNumber, problemIndex, problemName));
                        break;
                    }
                    case BaekjoonOnlineJudge:
                    {
                        int problemNumber = Integer.parseInt(problemCode);
                        problems.add(new BaekjoonProblem(problemNumber, problemName));
                        break;
                    }
                    case Algospot:
                    {
                        int problemNumber = Integer.parseInt(problemCode);
                        problems.add(new AlgospotProblem(problemNumber, problemName));
                        break;
                    }
                }
            } while (cursor.moveToNext());
        }

        return problems;
    }
}
