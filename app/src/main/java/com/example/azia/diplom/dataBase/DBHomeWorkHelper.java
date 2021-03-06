package com.example.azia.diplom.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHomeWorkHelper extends SQLiteOpenHelper {
    public static final String LISTS_TABLE = "HomeWork";
    public static final String ID_COLUMN = "_id";
    public static final String FULL_OBJECT_COLUMN = "object";
    public static final String FULL_TASK_COLUMN = "task";
    public static final String FULL_DATE_COLUMN = "date";
    public static final String FULL_TEACHER_COLUMN = "teacher";
    public static final String FULL_IMAGE_COLUMN = "image";
    public static final String FULL_DATESORT_COLUMN = "date_sort";

    public static final String CREATE_DATABASE_COMMAND = "CREATE TABLE " + LISTS_TABLE +
            " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " +
            FULL_OBJECT_COLUMN + " TEXT NOT NULL, " +
            FULL_TASK_COLUMN + " TEXT NOT NULL, " +
            FULL_DATE_COLUMN + " TEXT NOT NULL, " +
            FULL_TEACHER_COLUMN + " TEXT NOT NULL, " +
            FULL_IMAGE_COLUMN + " TEXT, " +
            FULL_DATESORT_COLUMN + " TEXT NOT NULL);";

    public static final int DATABASE_VERSION = 1;

    public DBHomeWorkHelper(Context context) {
        super(context, "HomeWork.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE_COMMAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteInfo(String id, SQLiteDatabase db) {
        db.delete(LISTS_TABLE, "_id=" + id, null);
        Log.e("DATABESE ", "DELETED ROW");
    }

    public void addInfo(String object, String task, String date, String teacher, String image, String date_sort, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FULL_OBJECT_COLUMN, object);
        contentValues.put(FULL_TASK_COLUMN, task);
        contentValues.put(FULL_DATE_COLUMN, date);
        contentValues.put(FULL_TEACHER_COLUMN, teacher);
        contentValues.put(FULL_IMAGE_COLUMN, image);
        contentValues.put(FULL_DATESORT_COLUMN, date_sort);
        db.insert(LISTS_TABLE, null, contentValues);
        Log.e("DATABESE ", "INSERTED");
    }

    public Cursor getInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] arr_Strings = {FULL_OBJECT_COLUMN, FULL_TASK_COLUMN, FULL_DATE_COLUMN, FULL_TEACHER_COLUMN, FULL_IMAGE_COLUMN, FULL_DATESORT_COLUMN, ID_COLUMN};
        cursor = db.query(LISTS_TABLE, arr_Strings, null, null, null, null, null);
        return cursor;

    }
}