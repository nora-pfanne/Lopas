package com.example.norablakaj.lateinapp.Databases;

import android.provider.BaseColumns;

/**
 * Created by Nora Blakaj on 23.01.2018.
 */

public class DeklinationsendungDB {

    private DeklinationsendungDB(){}

    public static class FeedEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "Deklinationsendung";

        //Table columns
        public static final String  COLUMN_NAME = "Name",
                                    COLUMN_NOM_SG = "Nom_Sg",
                                    COLUMN_NOM_PL = "Nom_Pl",
                                    COLUMN_GEN_SG = "Gen_Sg",
                                    COLUMN_GEN_PL = "Gen_Pl",
                                    COLUMN_DAT_SG = "Dat_Sg",
                                    COLUMN_DAT = "Nom_Sg",
                                    COLUMN_NOM_SG = "Nom_Sg",
                                    COLUMN_NOM_SG = "Nom_Sg",
                                    COLUMN_NOM_SG = "Nom_Sg",
                                    COLUMN_NOM_SG = "Nom_Sg",
    }
}
