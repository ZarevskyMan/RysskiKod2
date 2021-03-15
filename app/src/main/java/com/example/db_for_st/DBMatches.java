package com.example.db_for_st;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBMatches {

    private static final String DATABASE_NAME = "BazaPodProekt";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Eksponati";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_YEAR = "Year";
    private static final String COLUMN_YEARINSCHOOL = "YearInSchool";
    private static final String COLUMN_HISTORY = "History";
    private static final String COLUMN_GROUPS = "Groups";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_NAME = 1;
    private static final int NUM_COLUMN_YEAR = 2;
    private static final int NUM_COLUMN_YEARINSCHOOL = 3;
    private static final int NUM_COLUMN_HISTORY = 4;
    private static final int NUM_COLUMN_GROUPS = 5;

    private SQLiteDatabase mDataBase;

    public DBMatches(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String name, int year, int yearinschool, String history) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_YEARINSCHOOL, yearinschool);
        cv.put(COLUMN_HISTORY,history);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Matches md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_NAME, md.getTeamhouse());
        cv.put(COLUMN_YEAR, md.getTeamguest());
        cv.put(COLUMN_YEARINSCHOOL, md.getGoalshouse());
        cv.put(COLUMN_HISTORY,md.getGoalsguest());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Matches select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String TeamHome = mCursor.getString(NUM_COLUMN_NAME);
       int TeamGuest = mCursor.getInt(NUM_COLUMN_YEAR);
        int GoalsHome = mCursor.getInt(NUM_COLUMN_YEARINSCHOOL);
        String GoalsGuest=mCursor.getString(NUM_COLUMN_HISTORY);
        return new Matches(id, TeamHome, TeamGuest, GoalsHome,GoalsGuest);
    }

    public ArrayList<Matches> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Matches> arr = new ArrayList<Matches>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String TeamHome = mCursor.getString(NUM_COLUMN_NAME);
                int TeamGuest = mCursor.getInt(NUM_COLUMN_YEAR);
                int GoalsHome = mCursor.getInt(NUM_COLUMN_YEARINSCHOOL);
                String GoalsGuest=mCursor.getString(NUM_COLUMN_HISTORY);
                arr.add(new Matches(id, TeamHome, TeamGuest, GoalsHome,GoalsGuest));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME+ " STRING, " +
                    COLUMN_YEAR + " INT, " +
                    COLUMN_YEARINSCHOOL + " INT,"+
                    COLUMN_HISTORY+" STRING);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}