package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Information for the 'Lektion'-Table
 */

public class LektionDB {

    private LektionDB(){}

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Lektion";

        //Table columns
        public static final String COLUMN_THEMA = "Thema",
                                   COLUMN_BESCHREIBUNG = "Beschreibung";
    }
}
