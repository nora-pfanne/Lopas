package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Information for the 'Nomen'-Table
 */

public class NomenDB {

    public NomenDB(){}


    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "Nomen";
        //Table columns
        public static final String COLUMN_LATEIN = "Latein",
                                   COLUMN_DEUTSCH = "Deutsch",
                                   COLUMN_HINWEIS = "Hinweis",
                                   COLUMN_GENITIV = "GenSg",
                                   COLUMN_GENUS = "Genus",
                                   COLUMN_DEKLINATION = "Deklination",
                                   COLUMN_GELERNT = "Gelernt";
    }
}
