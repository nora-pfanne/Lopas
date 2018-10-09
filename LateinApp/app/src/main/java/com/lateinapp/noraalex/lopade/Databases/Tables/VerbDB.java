package com.lateinapp.noraalex.lopade.Databases.Tables;

import android.provider.BaseColumns;

public class VerbDB extends Vokabel{

    public VerbDB(int id, String lateinInf, String deutsch){
        super(id, lateinInf, deutsch);
    }

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Verb";
        //Table columns
        public static final String
                                   COLUMN_INFINITIV_DEUTSCH = "Infinitiv_Deutsch",
                                   COLUMN_WORTSTAMM = "Wortstamm",
                                   COLUMN_KONJUGATION = "Konjugation",
                                   COLUMN_GELERNT = "Gelernt",
                                   COLUMN_AMOUNT_INCORRECT = "Amount_Incorrect",
                                   COLUMN_LEKTION_ID = "Lektion_ID",
                                   COLUMN_PERSONALENDUNG_ID = "Personalendung_id",
                                   COLUMN_SPRECHVOKAL_ID = "Sprechvokal_id";
    }
}
