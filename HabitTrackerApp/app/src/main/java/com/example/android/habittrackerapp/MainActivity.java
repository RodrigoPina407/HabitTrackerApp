package com.example.android.habittrackerapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.habittrackerapp.data.TrackerContract.TrackerEntry;
import com.example.android.habittrackerapp.data.TrackerDbHelper;

public class MainActivity extends AppCompatActivity {

    private TrackerDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new TrackerDbHelper(this);
    }

    private void displayDatabaseInfo() {

        TextView displayView = (TextView) findViewById(R.id.db_text);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = TrackerDbHelper.readAllHabits(db);

        try {

            displayView.setText("Total number of days " + cursor.getCount() + "\n\n");
            displayView.append(TrackerEntry.COLUMN_DAY + " - " +
                    TrackerEntry.COLUMN_ACTIVITY_NAME + " - " +
                    TrackerEntry.COLUMN_ACTIVITY_TIME + "\n");

            // Figure out the index of each column
            int dayColumnIndex = cursor.getColumnIndex(TrackerEntry.COLUMN_DAY);
            int nameColumnIndex = cursor.getColumnIndex(TrackerEntry.COLUMN_ACTIVITY_NAME);
            int timeColumnIndex = cursor.getColumnIndex(TrackerEntry.COLUMN_ACTIVITY_TIME);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int currentDay = cursor.getInt(dayColumnIndex);
                String currentActivityName = cursor.getString(nameColumnIndex);
                int currentActivityTime = cursor.getInt(timeColumnIndex);

                displayView.append(("\n" + currentDay + " - " +
                        currentActivityName + " - " +
                        currentActivityTime));
            }
        } finally {

            cursor.close();
        }
    }

    /* Display the database information when the app starts*/
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_delete_all_entries:

                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.delete(TrackerEntry.TABLE_NAME, null, null);
                displayDatabaseInfo();

                return true;
        }

        /* switch statement for the options in the menu*/
        return super.onOptionsItemSelected(item);
    }
}



