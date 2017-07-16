package com.example.android.habittrackerapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.android.habittrackerapp.data.TrackerContract.TrackerEntry;


public class TrackerDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tracker.db";

    private static final int DATABASE_VERSION = 1;

    public TrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the tracker table
        String SQL_CREATE_TRACKER_TABLE = "CREATE TABLE " + TrackerEntry.TABLE_NAME + " ("
                + TrackerEntry.COLUMN_DAY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TrackerEntry.COLUMN_ACTIVITY_NAME + " TEXT NOT NULL, "
                + TrackerEntry.COLUMN_ACTIVITY_TIME + " INTEGER NOT NULL DEFAULT 0 );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TRACKER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static Cursor readAllHabits(SQLiteDatabase db) {

        String[] projection = {
                TrackerEntry.COLUMN_DAY,
                TrackerEntry.COLUMN_ACTIVITY_NAME,
                TrackerEntry.COLUMN_ACTIVITY_TIME};

        Cursor cursor = db.query(
                TrackerEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return cursor;

    }


    public static void insertHabit(String nameString, int time, Context context) {

        // Create database helper
        TrackerDbHelper mDbHelper = new TrackerDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrackerEntry.COLUMN_ACTIVITY_NAME, nameString);
        values.put(TrackerEntry.COLUMN_ACTIVITY_TIME, time);

        long newRowId = db.insert(TrackerEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(context, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(context, "Habit saved in day: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
