package com.example.android.habittrackerapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.habittrackerapp.data.TrackerDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameEditText = (EditText) findViewById(R.id.name);
        mTimeEditText = (EditText) findViewById(R.id.time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* inflate the options menu*/
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Read from input fields
                String nameString = mNameEditText.getText().toString().trim();
                String timeString = mTimeEditText.getText().toString().trim();
                int time = Integer.parseInt(timeString);

                TrackerDbHelper.insertHabit(nameString, time, this);
                // Exit activity
                finish();
                return true;

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        /*switch statement for the menu options*/
        return super.onOptionsItemSelected(item);
    }
}
