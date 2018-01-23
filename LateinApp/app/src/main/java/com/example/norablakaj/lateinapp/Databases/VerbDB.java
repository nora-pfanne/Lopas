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
        public static final String COLUMN_WORTSTAMM = "Wortstamm",
                                   COLUMN_INFINITIV_DEUTSCH = "Infinitiv_Deutsch",
                                   COLUMN_KONJUGATION = "Konjugation",
                                   COLUMN_GELERNT = "Gelernt",
                                   COLUMN_LEKTIONID = "Lektion_ID";
    }
}
