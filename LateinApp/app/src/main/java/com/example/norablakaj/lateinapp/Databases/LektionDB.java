package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Created by Nora Blakaj on 11.01.2018.
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
