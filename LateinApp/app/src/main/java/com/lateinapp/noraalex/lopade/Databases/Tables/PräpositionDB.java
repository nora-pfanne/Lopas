package com.lateinapp.noraalex.lopade.Databases.Tables;

import android.provider.BaseColumns;

public class PräpositionDB extends Vokabel{

    public PräpositionDB(int id, String latein, String deutsch){
        super(id, latein, deutsch);
    }

    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "Praeposition";

        //Table columns
    public static final String COLUMN_DEUTSCH = "Deutsch",
                                COLUMN_LATEIN = "Latein",
                                COLUMN_GELERNT = "Gelernt",
                                COLUMN_AMOUNT_INCORRECT = "Amount_Incorrect",
                                COLUMN_LEKTION_ID = "Lektion_ID";
    }
}
