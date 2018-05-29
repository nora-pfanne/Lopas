package com.lateinapp.noraalex.lopade.Databases;

import com.lateinapp.noraalex.lopade.Databases.Tables.AdverbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.BeispielsatzDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.LektionDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.PräpositionDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Sprechvokal_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Sprechvokal_SubstantivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SprichwortDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SubstantivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;

//TODO: SQL_DUMP -> Ref: Hancl: https://github.com/LinuCC/babesk/tree/master/dbv/data/revisions

/**
 * A dump for all the SQL-Statements and Arrays used in DBHelper.class
 */
//@SuppressWarnings("WeakerAccess")
public final class SQL_DUMP {

    //Strings used for the creation of all database tables
     static final String SQL_CREATE_ENTRIES_ADVERB =
            "CREATE TABLE "
                    + AdverbDB.FeedEntry.TABLE_NAME
                    + "( "
                    + AdverbDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + AdverbDB.FeedEntry.COLUMN_DEUTSCH
                    + " TEXT, "
                    + AdverbDB.FeedEntry.COLUMN_LATEIN
                    + " TEXT, "
                    + AdverbDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + AdverbDB.FeedEntry.COLUMN_LEKTION_ID
                    + " INTEGER, "

                    + " FOREIGN KEY ("
                    + AdverbDB.FeedEntry.COLUMN_LEKTION_ID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + ")"
                    + ")";

     static final String SQL_CREATE_ENTRIES_DEKLINATIONSENDUNG =
            "CREATE TABLE "
                    + DeklinationsendungDB.FeedEntry.TABLE_NAME
                    + " ("
                    + DeklinationsendungDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_NAME
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_ABL_SG
                    + " TEXT, "
                    + DeklinationsendungDB.FeedEntry.COLUMN_ABL_PL
                    + " TEXT)";

     static final String SQL_CREATE_ENTRIES_LEKTION =
            "CREATE TABLE "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "( "
                    + LektionDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + LektionDB.FeedEntry.COLUMN_LEKTION_NR
                    + " INTEGER, "
                    + LektionDB.FeedEntry.COLUMN_TITEL
                    + " TEXT, "
                    + LektionDB.FeedEntry.COLUMN_THEMA
                    + " TEXT)";

     static final String SQL_CREATE_ENTRIES_PERSONALENDUNG_PRÄSENS =
            "CREATE TABLE "
                    + Personalendung_PräsensDB.FeedEntry.TABLE_NAME
                    + "( "
                    + Personalendung_PräsensDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG
                    + " TEXT, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL
                    + " TEXT, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG
                    + " TEXT, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL
                    + " TEXT, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG
                    + " TEXT, "
                    + Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL
                    + " TEXT)";

     static final String SQL_CREATE_ENTRIES_PRAEPOSITION =
            "CREATE TABLE "
                    + PräpositionDB.FeedEntry.TABLE_NAME
                    + "( "
                    + PräpositionDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + PräpositionDB.FeedEntry.COLUMN_DEUTSCH
                    + " TEXT, "
                    + PräpositionDB.FeedEntry.COLUMN_LATEIN
                    + " TEXT, "
                    + PräpositionDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + PräpositionDB.FeedEntry.COLUMN_LEKTION_ID
                    + " INTEGER, "

                    + " FOREIGN KEY ("
                    + PräpositionDB.FeedEntry.COLUMN_LEKTION_ID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + ")"
                    + ")";

     static final String SQL_CREATE_ENTRIES_SPRECHVOKAL_PRÄSENS =
            "CREATE TABLE "
                    + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME
                    + "( "
                    + Sprechvokal_PräsensDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_TITLE
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_INFINITV
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_SG
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_PL
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_SG
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_PL
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_SG
                    + " TEXT, "
                    + Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_PL
                    + " TEXT)";

     static final String SQL_CREATE_ENTRIES_SPRECHVOKAL_SUBSTANTIV =
            "CREATE TABLE "
                    + Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME
                    + " ("
                    + Sprechvokal_SubstantivDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_SG
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_PL
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_SG
                    + " TEXT, "
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_PL
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_SG
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_PL
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_SG
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_PL
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_SG
                    + " TEXT, "
                    + Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_PL
                    + " TEXT)";

     static final String SQL_CREATE_ENTRIES_SUBSTANTIV =
            "CREATE TABLE "
                    + SubstantivDB.FeedEntry.TABLE_NAME
                    + "( "
                    + SubstantivDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH
                    + " TEXT, "
                    + SubstantivDB.FeedEntry.COLUMN_WORTSTAMM
                    + " TEXT, "
                    + SubstantivDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + SubstantivDB.FeedEntry.COLUMN_LEKTION_ID
                    + " INTEGER,"
                    + SubstantivDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
                    + " INTEGER, "
                    + SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID
                    + " INTEGER, "


                    + " FOREIGN KEY ("
                    + SubstantivDB.FeedEntry.COLUMN_LEKTION_ID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + "), "
                    + "FOREIGN KEY ("
                    + SubstantivDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
                    + ") REFERENCES "
                    + Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME
                    + "("
                    + Sprechvokal_SubstantivDB.FeedEntry._ID
                    + "), "
                    + "FOREIGN KEY ("
                    + SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID
                    + ") REFERENCES "
                    + DeklinationsendungDB.FeedEntry.TABLE_NAME
                    + "("
                    + DeklinationsendungDB.FeedEntry._ID
                    + ")"
                    + ")";

     static final String SQL_CREATE_ENTRIES_VERB =
            "CREATE TABLE "
                    + VerbDB.FeedEntry.TABLE_NAME
                    + "( "
                    + VerbDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_WORTSTAMM
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_KONJUGATION
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER,"
                    + VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID
                    + " INTEGER, "
                    + VerbDB.FeedEntry.COLUMN_LEKTION_ID
                    + " INTEGER, "
                    + VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
                    + " INTEGER, "


                    + " FOREIGN KEY ("
                    + VerbDB.FeedEntry.COLUMN_LEKTION_ID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + "), "
                    + "FOREIGN KEY ("
                    + VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID
                    + ") REFERENCES "
                    + Personalendung_PräsensDB.FeedEntry.TABLE_NAME
                    + "("
                    + Personalendung_PräsensDB.FeedEntry._ID
                    + "), "
                    + "FOREIGN KEY ("
                    + VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
                    + ") REFERENCES "
                    + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME
                    + "("
                    + Sprechvokal_PräsensDB.FeedEntry._ID
                    + ")"
                    + ")";

     static final String SQL_CREATE_ENTRIES_SPRICHWORT =
            "CREATE TABLE "
                    + SprichwortDB.FeedEntry.TABLE_NAME
                    + "( "
                    + SprichwortDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + SprichwortDB.FeedEntry.COLUMN_DEUTSCH
                    + " TEXT, "
                    + SprichwortDB.FeedEntry.COLUMN_LATEIN
                    + " TEXT, "
                    + SprichwortDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + SprichwortDB.FeedEntry.COLUMN_LEKTION_ID
                    + " INTEGER, "

                    + " FOREIGN KEY ("
                    + SprichwortDB.FeedEntry.COLUMN_LEKTION_ID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + ")"
                    + ")";

     static final String SQL_CREATE_ENTRIES_BEISPIELSATZ =
             "CREATE TABLE "
                     + BeispielsatzDB.FeedEntry.TABLE_NAME
                     + "( "
                     + SprichwortDB.FeedEntry._ID
                     + " INTEGER PRIMARY KEY, "

                     + BeispielsatzDB.FeedEntry.COLUMN_SUBJEKT
                     + " INTEGER, "
                     + BeispielsatzDB.FeedEntry.COLUMN_PRAEDIKAT
                     + " INTEGER, "
                     + BeispielsatzDB.FeedEntry.COLUMN_GENITIV
                     + " INTEGER, "
                     + BeispielsatzDB.FeedEntry.COLUMN_DATIV
                     + " INTEGER, "
                     + BeispielsatzDB.FeedEntry.COLUMN_AKKUSATIV
                     + " INTEGER, "


                     + " FOREIGN KEY ("
                     + BeispielsatzDB.FeedEntry.COLUMN_SUBJEKT
                     + ") REFERENCES "
                     + SubstantivDB.FeedEntry.TABLE_NAME
                     + "("
                     + SubstantivDB.FeedEntry._ID
                     + "),"

                     + " FOREIGN KEY ("
                     + BeispielsatzDB.FeedEntry.COLUMN_PRAEDIKAT
                     + ") REFERENCES "
                     + VerbDB.FeedEntry.TABLE_NAME
                     + "("
                     + VerbDB.FeedEntry._ID
                     + "),"

                     + " FOREIGN KEY ("
                     + BeispielsatzDB.FeedEntry.COLUMN_GENITIV
                     + ") REFERENCES "
                     + SubstantivDB.FeedEntry.TABLE_NAME
                     + "("
                     + SubstantivDB.FeedEntry._ID
                     + "),"

                     + " FOREIGN KEY ("
                     + BeispielsatzDB.FeedEntry.COLUMN_DATIV
                     + ") REFERENCES "
                     + SubstantivDB.FeedEntry.TABLE_NAME
                     + "("
                     + SubstantivDB.FeedEntry._ID
                     + "),"

                     + " FOREIGN KEY ("
                     + BeispielsatzDB.FeedEntry.COLUMN_AKKUSATIV
                     + ") REFERENCES "
                     + SubstantivDB.FeedEntry.TABLE_NAME
                     + "("
                     + SubstantivDB.FeedEntry._ID
                     + ")"


                     + ")";

    //creating a String for quick access to a deletion command for all tables
     static final String SQL_DELETE_ENTRIES_ADVERB =
            "DROP TABLES IF EXISTS "
                    + AdverbDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_DEKLINATIONSENDUNG =
            "DROP TABLES IF EXISTS "
                    + DeklinationsendungDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_LEKTION =
            "DROP TABLES IF EXISTS "
                    + LektionDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_PERSONALENDUNG_PRÄSENS =
            "DROP TABLES IF EXISTS "
                    + Personalendung_PräsensDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_PRAEPOSITION =
            "DROP TABLES IF EXISTS "
                    + PräpositionDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_SPRECHVOKAL_PRÄSENS =
            "DROP TABLES IF EXISTS "
                    + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_SPRECHVOKAL_SUBSTANTIV =
            "DROP TABLES IF EXISTS "
                    + Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_SPRICHWORT =
            "DROP TABLES IF EXISTS "
                    + SprichwortDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_SUBSTANTIV =
            "DROP TABLES IF EXISTS "
                    + SubstantivDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_VERB =
            "DROP TABLES IF EXISTS "
                    + VerbDB.FeedEntry.TABLE_NAME;

     static final String SQL_DELETE_ENTRIES_BEISPIELSATZ =
             "DROP TABLES IF EXISTS "
                    + BeispielsatzDB.FeedEntry.TABLE_NAME;

     static final String[] allTables = {
            AdverbDB.FeedEntry.TABLE_NAME,
            DeklinationsendungDB.FeedEntry.TABLE_NAME,
            LektionDB.FeedEntry.TABLE_NAME,
            Personalendung_PräsensDB.FeedEntry.TABLE_NAME,
            PräpositionDB.FeedEntry.TABLE_NAME,
            Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME,
            Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME,
            SprichwortDB.FeedEntry.TABLE_NAME,
            SubstantivDB.FeedEntry.TABLE_NAME,
            VerbDB.FeedEntry.TABLE_NAME,
            BeispielsatzDB.FeedEntry.TABLE_NAME
    };

     static final String[] allColumnsAdverb = {

            AdverbDB.FeedEntry._ID,
            AdverbDB.FeedEntry.COLUMN_DEUTSCH,
            AdverbDB.FeedEntry.COLUMN_LATEIN,
            AdverbDB.FeedEntry.COLUMN_GELERNT,
            AdverbDB.FeedEntry.COLUMN_LEKTION_ID
    };

     static final String[] allColumnsDeklinationsendung = {
            DeklinationsendungDB.FeedEntry._ID,
            DeklinationsendungDB.FeedEntry.COLUMN_NAME,
            DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_ABL_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_ABL_PL
    };

     static final String[] allColumnsLektion = {
            LektionDB.FeedEntry._ID,
             LektionDB.FeedEntry.COLUMN_LEKTION_NR,
            LektionDB.FeedEntry.COLUMN_TITEL,
            LektionDB.FeedEntry.COLUMN_THEMA
    };

     static final String[] allColumnsPersonalendung_Präsens = {
            Personalendung_PräsensDB.FeedEntry._ID,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL
    };

     static final String[] allColumnsPraeposition = {

            PräpositionDB.FeedEntry._ID,
            PräpositionDB.FeedEntry.COLUMN_DEUTSCH,
            PräpositionDB.FeedEntry.COLUMN_LATEIN,
            PräpositionDB.FeedEntry.COLUMN_GELERNT,
            PräpositionDB.FeedEntry.COLUMN_LEKTION_ID
    };

     static final String[] allColumnsSprechvokal_Präsens = {
            Sprechvokal_PräsensDB.FeedEntry._ID,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_TITLE,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_INFINITV,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_PL,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_PL,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_PL

    };

     static final String[] allColumnsSprechvokal_Substantiv = {
            Sprechvokal_SubstantivDB.FeedEntry._ID,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_SG,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_PL,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_SG,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_PL,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_SG,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_PL,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_SG,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_PL,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_SG,
            Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_PL

    };

     static final String[] allColumnsSprichwort = {

            SprichwortDB.FeedEntry._ID,
            SprichwortDB.FeedEntry.COLUMN_DEUTSCH,
            SprichwortDB.FeedEntry.COLUMN_LATEIN,
            SprichwortDB.FeedEntry.COLUMN_GELERNT,
            SprichwortDB.FeedEntry.COLUMN_LEKTION_ID
    };

     static final String[] allColumnsSubstantiv = {
            SubstantivDB.FeedEntry._ID,
            SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH,
            SubstantivDB.FeedEntry.COLUMN_WORTSTAMM,
            SubstantivDB.FeedEntry.COLUMN_GELERNT,
            SubstantivDB.FeedEntry.COLUMN_LEKTION_ID,
            SubstantivDB.FeedEntry.COLUMN_SPRECHVOKAL_ID,
            SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID
    };

     static final String[] allColumnsVerb = {

            VerbDB.FeedEntry._ID,
            VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH,
            VerbDB.FeedEntry.COLUMN_WORTSTAMM,
            VerbDB.FeedEntry.COLUMN_KONJUGATION,
            VerbDB.FeedEntry.COLUMN_GELERNT,
            VerbDB.FeedEntry.COLUMN_LEKTION_ID,
            VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID,
            VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
    };

     static final String[] allColumnsBeispielsatz = {
             BeispielsatzDB.FeedEntry._ID,
             BeispielsatzDB.FeedEntry.COLUMN_SUBJEKT,
             BeispielsatzDB.FeedEntry.COLUMN_GENITIV,
             BeispielsatzDB.FeedEntry.COLUMN_DATIV,
             BeispielsatzDB.FeedEntry.COLUMN_AKKUSATIV
     };
}
