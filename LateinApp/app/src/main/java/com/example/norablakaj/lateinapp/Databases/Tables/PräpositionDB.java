package com.example.norablakaj.lateinapp.Databases.Tables;

import android.provider.BaseColumns;

//TODO: Should this be private/package private?


public class PräpositionDB {

    private PräpositionDB(){}

    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "Praeposition";

        //Table columns
    public static final String COLUMN_DEUTSCH = "Deutsch",
                                COLUMN_LATEIN = "Latein",
                                COLUMN_GELERNT = "Gelernt",
                                COLUMN_LEKTION_ID = "Lektion_ID";
    }
}
