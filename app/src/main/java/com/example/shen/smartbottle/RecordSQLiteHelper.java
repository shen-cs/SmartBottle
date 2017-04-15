package com.example.shen.smartbottle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shen Chang-Shao on 2016/9/2.
 *
 */
public class RecordSQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "records";
    public static final String DATABASE_NAME = "records.db";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_CONSUMPTION = "consumption";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
             + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             + COLUMN_YEAR + " INTEGER NOT NULL, " + COLUMN_MONTH + " INTEGER NOT NULL, "
             + COLUMN_DAY + " INTEGER NOT NULL, " + COLUMN_TIME + " TEXT NOT NULL, "
             + COLUMN_CONSUMPTION + " FLOAT NOT NULL);";

    public RecordSQLiteHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
