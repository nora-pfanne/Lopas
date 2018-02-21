package com.example.norablakaj.lateinapp.Databases.Tables;

import android.provider.BaseColumns;

public class LektionDB {

    private LektionDB(){}

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Lektion";

        //Table columns
        public static final String
                COLUMN_LEKTION_NR = "LektionNr",
                COLUMN_TITEL = "Titel",
                COLUMN_THEMA = "Thema";
    }
}
