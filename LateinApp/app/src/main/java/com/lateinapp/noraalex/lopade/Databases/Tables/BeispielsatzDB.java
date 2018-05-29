package com.lateinapp.noraalex.lopade.Databases.Tables;

import android.provider.BaseColumns;

public class BeispielsatzDB {

    public BeispielsatzDB(){}

    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "BeispielsatzDB";

        //Table columns
        public static final String COLUMN_SUBJEKT = "Subjekt",
                COLUMN_PRAEDIKAT = "Prädikat",
                COLUMN_GENITIV = "Genitiv",
                COLUMN_DATIV = "Dativ",
                COLUMN_AKKUSATIV = "Akkusativ";

    }

}
