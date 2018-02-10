package com.example.norablakaj.lateinapp.Databases.Tables;

import android.provider.BaseColumns;


//TODO: Should this be private/package private?
//TODO: SQL_DUMP -> Ref: Hancl: https://github.com/LinuCC/babesk/tree/master/dbv/data/revisions

public class AdverbDB extends Vokabel{

    public AdverbDB(int id, String latein, String deutsch){
        super(id, latein, deutsch);
    }

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
