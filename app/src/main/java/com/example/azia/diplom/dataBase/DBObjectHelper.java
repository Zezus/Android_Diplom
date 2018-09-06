package com.example.azia.diplom.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBObjectHelper extends SQLiteOpenHelper {
    public static final String LISTS_TABLE = "Object";
    public static final String ID_COLUMN = "_id";
    public static final String FULL_OBJECT_COLUMN = "object";
    public static final String FULL_TEACHER_COLUMN = "teacher";

    public static final String CREATE_DATABASE_COMMAND = "CREATE TABLE " + LISTS_TABLE +
            " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " +
            FULL_OBJECT_COLUMN + " TEXT NOT NULL, " +
            FULL_TEACHER_COLUMN + " TEXT NOT NULL);";

    public static final int DATABASE_VERSION = 1;

    public DBObjectHelper(Context context) {
        super(context, "Object.db", null, DATABASE_VERSION);
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

    public void addInfo(String object, String teacher, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FULL_OBJECT_COLUMN, object);
        contentValues.put(FULL_TEACHER_COLUMN, teacher);
        db.insert(LISTS_TABLE, null, contentValues);
        Log.e("DATABESE ", "INSERTED");
    }

    public Cursor getInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] arr_Strings = {FULL_OBJECT_COLUMN, FULL_TEACHER_COLUMN, ID_COLUMN};
        cursor = db.query(LISTS_TABLE, arr_Strings, null, null, null, null, null);
        return cursor;

    }
}
