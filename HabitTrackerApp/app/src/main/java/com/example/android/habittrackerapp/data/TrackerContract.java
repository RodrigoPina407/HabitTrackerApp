package com.example.android.habittrackerapp.data;

import android.provider.BaseColumns;


public final class TrackerContract {


    private TrackerContract() {
        // this constructor is private so the class cannot be initialised
    }


    public static final class TrackerEntry implements BaseColumns {


        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_DAY = "day";

        public static final String COLUMN_ACTIVITY_NAME = "activity";

        public static final String COLUMN_ACTIVITY_TIME = "time";

    }

}
