package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Created by Nora Blakaj on 06.02.2018.
 */

public class AdverbDB {

    private AdverbDB(){}

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Adverb";

        //Table columns
        public static final String COLUMN_DEUTSCH = "Deutsch",
                                    COLUMN_LATEIN = "Latein",
                                    COLUMN_GELERNT = "Gelernt",
                                    COLUMN_LEKTION_ID = "Lektion_ID";
    }
}
