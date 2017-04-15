package com.example.shen.smartbottle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Shen Chang-Shao on 2016/9/2.
 *  Interface bound to MainActivity to interact with the user to control the database
 */
public class RecordDataSource {
    private SQLiteDatabase db;
    private RecordSQLiteHelper dbHelper;
    private String[] allColumns = {
            RecordSQLiteHelper.COLUMN_ID, RecordSQLiteHelper.COLUMN_YEAR,
            RecordSQLiteHelper.COLUMN_MONTH, RecordSQLiteHelper.COLUMN_DAY,
            RecordSQLiteHelper.COLUMN_TIME, RecordSQLiteHelper.COLUMN_CONSUMPTION
    };
    public RecordDataSource(Context context) {
        dbHelper = new RecordSQLiteHelper(context);
    }
    public void open() {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {
        dbHelper.close();
    }
    public Record addRecord(int year, int month, int day, String time, float consumption) {
        ContentValues values = new ContentValues();
        values.put(RecordSQLiteHelper.COLUMN_YEAR, year);
        values.put(RecordSQLiteHelper.COLUMN_MONTH, month);
        values.put(RecordSQLiteHelper.COLUMN_DAY, day);
        values.put(RecordSQLiteHelper.COLUMN_TIME, time );
        values.put(RecordSQLiteHelper.COLUMN_CONSUMPTION, consumption);
        long insertId =  db.insert(RecordSQLiteHelper.TABLE_NAME, null, values);
        return new Record(insertId, year, month, day, time, consumption);
    }

    public void deleteRecord(Record record) {
        long id = record.getId();
        db.delete(RecordSQLiteHelper.TABLE_NAME, RecordSQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    // given a date , returns a HashMap of full date  to consumption
    public ArrayList<Record> selectByDate(int month, int day) {
        ArrayList<Record> records = new ArrayList<>();
        Cursor c = db.query(RecordSQLiteHelper.TABLE_NAME, allColumns, RecordSQLiteHelper.COLUMN_MONTH + " = " + month
        + " AND " + RecordSQLiteHelper.COLUMN_DAY + " = " + day, null, null, null, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            records.add(new Record(c.getLong(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getString(4), c.getFloat(5)));
            c.moveToNext();
        }
        c.close();
        return records;
    }

    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> records = new ArrayList<>();
        Cursor c = db.query(RecordSQLiteHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        c.moveToFirst();
        while(!c.isAfterLast()) {
            records.add(new Record(c.getLong(0), c.getInt(1), c.getInt(2), c.getInt(3), c.getString(4), c.getFloat(5)));
            c.moveToNext();
        }
        c.close();
        return records;
    }
}
