package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Created by Nora Blakaj on 11.01.2018.
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
