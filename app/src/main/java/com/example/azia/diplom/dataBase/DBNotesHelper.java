package com.example.azia.diplom.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBNotesHelper extends SQLiteOpenHelper {
    public static final String LISTS_TABLE = "Notes";
    public static final String ID_COLUMN = "_id";
    public static final String FULL_TITLE_COLUMN = "title";
    public static final String FULL_NOTES_COLUMN = "note";

    public static final String CREATE_DATABASE_COMMAND = "CREATE TABLE " + LISTS_TABLE +
            " (" + ID_COLUMN + " INTEGER PRIMARY KEY, " +
            FULL_NOTES_COLUMN + " TEXT NOT NULL, " +
            FULL_TITLE_COLUMN + " TEXT NOT NULL);";

    public static final int DATABASE_VERSION = 1;

    public DBNotesHelper(Context context) {
        super(context, "Notes.db", null, DATABASE_VERSION);
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

    public void addInfo(String note, String title, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FULL_NOTES_COLUMN, note);
        contentValues.put(FULL_TITLE_COLUMN, title);
        db.insert(LISTS_TABLE, null, contentValues);
        Log.e("DATABESE ", "INSERTED");
    }

    public Cursor getInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] arr_Strings = {FULL_NOTES_COLUMN, FULL_TITLE_COLUMN, ID_COLUMN};
        cursor = db.query(LISTS_TABLE, arr_Strings, null, null, null, null, null);
        return cursor;

    }
}
