package com.example.azia.diplom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by AZIA on 12.07.2018.
 */

public class DBScheduleHelper extends SQLiteOpenHelper {

    public static final String LISTS_TABLE = "Schedule";
    public static final String ID_COLUMN = "_id";
    public static final String FULL_OBJECT_COLUMN = "object";
    public static final String FULL_ROOM_COLUMN = "room";
    public static final String FULL_TIMESTART_COLUMN = "time_start";
    public static final String FULL_TIMEEND_COLUMN = "time_end";
    public static final String FULL_DAY_COLUMN = "day";

    public static final String CREATE_DATABASE_COMMAND = "CREATE TABLE " + LISTS_TABLE +
            " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " +
            FULL_OBJECT_COLUMN + " TEXT NOT NULL, " +
            FULL_ROOM_COLUMN + " TEXT NOT NULL, " +
            FULL_TIMESTART_COLUMN + " TEXT NOT NULL, " +
            FULL_TIMEEND_COLUMN + " TEXT NOT NULL, " +
            FULL_DAY_COLUMN + " TEXT NOT NULL);";

    public static final int DATABASE_VERSION = 1;

    public DBScheduleHelper(Context context) {
        super(context, "Schedule.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addInfo(String object, String room, String time_start, String time_end, String day, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FULL_OBJECT_COLUMN, object);
        contentValues.put(FULL_ROOM_COLUMN, room);
        contentValues.put(FULL_TIMESTART_COLUMN, time_start);
        contentValues.put(FULL_TIMEEND_COLUMN, time_end);
        contentValues.put(FULL_DAY_COLUMN, day);
        db.insert(LISTS_TABLE, null, contentValues);
        Log.e("DATABESE ", "INSERTED");
    }

    public Cursor getInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] arr_Strings = {FULL_OBJECT_COLUMN, FULL_ROOM_COLUMN, FULL_TIMESTART_COLUMN, FULL_TIMEEND_COLUMN, FULL_DAY_COLUMN};
        cursor = db.query(LISTS_TABLE, arr_Strings, null, null, null, null, null);
        return cursor;

    }
}
