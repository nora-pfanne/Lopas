package com.example.norablakaj.lateinapp.Databases.Tables;

import android.provider.BaseColumns;

//TODO: Should this be private/package private?


public class DeklinationsendungDB {

    private DeklinationsendungDB(){}

    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "deklinationsendung";

        //Table columns
        public static final String  COLUMN_NAME = "Name",
                COLUMN_NOM_SG = "Nom_Sg",
                COLUMN_NOM_PL = "Nom_Pl",
                COLUMN_GEN_SG = "Gen_Sg",
                COLUMN_GEN_PL = "Gen_Pl",
                COLUMN_DAT_SG = "Dat_Sg",
                COLUMN_DAT_PL = "Dat_Pl",
                COLUMN_AKK_SG = "Akk_Sg",
                COLUMN_AKK_PL = "Akk_PL",
                COLUMN_ABL_SG = "Abl_Sg",
                COLUMN_ABL_PL = "Abl_Pl";
    }
}