package com.example.norablakaj.lateinapp.Databases.Tables;

import android.provider.BaseColumns;

//TODO: Should this be private/package private?

public class Personalendung_PräsensDB {

    private Personalendung_PräsensDB(){}

    public static class FeedEntry implements BaseColumns {


        // Table name
        public static final String TABLE_NAME = "Personalendung_Praesens";

        // Table columns
        public static final String  COLUMN_1_SG = "Erste_Sg",
                                    COLUMN_2_SG = "Zweite_Sg",
                                    COLUMN_3_SG = "Dritte_Sg",
                                    COLUMN_1_PL = "Erste_Pl",
                                    COLUMN_2_PL = "Zweite_Pl",
                                    COLUMN_3_PL = "Dritte_Pl";
    }
}
