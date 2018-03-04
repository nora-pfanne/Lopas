package com.lateinapp.noraalex.lopade.Databases.Tables;

import android.provider.BaseColumns;

public class SubstantivDB extends Vokabel{

    public SubstantivDB(int id, String lateinNomSg, String deutsch){
        super(id, lateinNomSg, deutsch);
    }

    public static class FeedEntry implements BaseColumns{

        //Table name
        public static final String TABLE_NAME = "Substantiv";

        //Table columns
        public static final String COLUMN_NOM_SG_DEUTSCH = "Nom_Sg_Deutsch",
                                   COLUMN_WORTSTAMM = "Wortstamm",
                                   COLUMN_GELERNT = "Gelernt",
                                   COLUMN_LEKTION_ID = "Lektion_ID",
                                   COLUMN_SPRECHVOKAL_ID = "Sprechvokal_ID",
                                   COLUMN_DEKLINATIONSENDUNG_ID = "Deklinationsendung_ID";
    }
}
