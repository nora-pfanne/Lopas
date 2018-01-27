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
        public static final String
                                   COLUMN_INFINITIV_DEUTSCH = "Infinitiv_Deutsch",
                                   COLUMN_WORTSTAMM = "Wortstamm",
                                   COLUMN_KONJUGATION = "Konjugation",
                                   COLUMN_GELERNT = "Gelernt",
                                   COLUMN_LEKTION_ID = "Lektion_ID",
                                   COLUMN_PERSONALENDUNG_ID = "Personalendung_id",
                                   COLUMN_SPRECHVOKAL_ID = "Sprechvokal_id";
    }
}
