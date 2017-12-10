package com.praneethcorporation.notes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 9/30/2017.
 */

public class NoteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Notebook.db";
    public static final int DATABASE_VERSION = 2;

    String SQL_ENTRIES = "CREATE TABLE " + NoteContract.NoteEntry.TABLE_NAME + "(" + NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NoteContract.NoteEntry.COLUMN_TITLE + " TEXT NOT NULL," + NoteContract.NoteEntry.COLUMN_CONTENT + " TEXT NOT NULL,"+ NoteContract.NoteEntry.COLUMN_TIME+" TEXT NOT NULL);";
    String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME;

    public NoteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
