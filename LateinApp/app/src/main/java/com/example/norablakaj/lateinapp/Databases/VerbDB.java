package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Information for the 'Verb'-Table
 */

public class VerbDB {

    public VerbDB(){}

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Verb";
        //Table columns
        public static final String COLUMN_LATEIN = "Latein",
                                   COLUMN_DEUTSCH = "Deutsch",
                                   COLUMN_HINWEIS = "Hinweis",
                                   COLUMN_VERBFORM = "FirstPersSg",
                                   COLUMN_KONJUGATION = "Konjugation",
                                   COLUMN_GELERNT = "Gelernt";
        //TODO: Foreign Key: Lektion
    }
}
