package com.example.norablakaj.lateinapp.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * DBHelper is used for managing the database and its tables.
 * All queries are done in this class and called where they are needed
 */
//TODO FOREIGN KEYS
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase dbConnection;

    private static final String SQL_CREATE_ENTRIES_DEKLINATIONSENDUNG =
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

    private static final String SQL_CREATE_ENTRIES_LEKTION =
            "CREATE TABLE "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "( "
                    + LektionDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + LektionDB.FeedEntry.COLUMN_TITEL
                    + " TEXT, "
                    + LektionDB.FeedEntry.COLUMN_THEMA
                    + " TEXT)";


    private static final String SQL_CREATE_ENTRIES_PERSONALENDUNG_PRÄSENS =
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

    private static final String SQL_CREATE_ENTRIES_SPRECHVOKAL_PRÄSENS =
            "CREATE TABLE "
                    + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME
                    + "( "
                    + Sprechvokal_PräsensDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
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

    private static final String SQL_CREATE_ENTRIES_SPRECHVOKAL_SUBSTANTIV =
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

    private static final String SQL_CREATE_ENTRIES_SUBSTANTIV =
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

    private static final String SQL_CREATE_ENTRIES_VERB =
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

    //creating a String for quick access to a deletion command for all tables
    private static final String SQL_DELETE_ENTRIES_DEKLINATIONSENDUNG =
            "DROP TABLES IF EXISTS "
                    + DeklinationsendungDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_LEKTION =
            "DROP TABLES IF EXISTS "
                    + LektionDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_PERSONALENDUNG_PRÄSENS =
            "DROP TABLES IF EXISTS "
                    + Personalendung_PräsensDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_SPRECHVOKAL_PRÄSENS =
            "DROP TABLES IF EXISTS "
                    + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_SPRECHVOKAL_SUBSTANTIV =
            "DROP TABLES IF EXISTS "
                    + Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_SUBSTANTIV =
            "DROP TABLES IF EXISTS "
                    + SubstantivDB.FeedEntry.TABLE_NAME;


    private static final String SQL_DELETE_ENTRIES_VERB =
            "DROP TABLES IF EXISTS "
                    + VerbDB.FeedEntry.TABLE_NAME;


    private static final String[] allColumnsDeklinationsendung = {
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

    private static final String[] allColumnsLektion = {
            LektionDB.FeedEntry._ID,
            LektionDB.FeedEntry.COLUMN_TITEL,
            LektionDB.FeedEntry.COLUMN_THEMA
    };

    private static final String[] allColumnsPersonalendung_Präsens = {
            Personalendung_PräsensDB.FeedEntry._ID,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL,
    };

    private static final String[] allColumnsSprechvokal_Präsens = {
            Sprechvokal_PräsensDB.FeedEntry._ID,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_SG,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_PL,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_PL,
            Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_PL

    };

    private static final String[] allColumnsSprechvokal_Substantiv = {
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

    private static final String[] allColumnsSubstantiv = {
            SubstantivDB.FeedEntry._ID,
            SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH,
            SubstantivDB.FeedEntry.COLUMN_WORTSTAMM,
            SubstantivDB.FeedEntry.COLUMN_GELERNT,
            SubstantivDB.FeedEntry.COLUMN_LEKTION_ID,
            SubstantivDB.FeedEntry.COLUMN_SPRECHVOKAL_ID,
            SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID
    };

    private static final String[] allColumnsVerb = {

            VerbDB.FeedEntry._ID,
            VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH,
            VerbDB.FeedEntry.COLUMN_WORTSTAMM,
            VerbDB.FeedEntry.COLUMN_KONJUGATION,
            VerbDB.FeedEntry.COLUMN_GELERNT,
            VerbDB.FeedEntry.COLUMN_LEKTION_ID,
            VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID,
            VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID
    };

    //Version of the database
    private static final int DATABASE_VERSION = 1;


    //Name of the database file
    private static final String DATABASE_NAME = "TestDb6.db";


    /**
     * Constructor
     *
     * @param context lets newly-created objects understand what has been going on
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating all 3 Databases
     *
     * @param db database where the tables are supposed to be put into
     */
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES_DEKLINATIONSENDUNG);
        db.execSQL(SQL_CREATE_ENTRIES_LEKTION);
        db.execSQL(SQL_CREATE_ENTRIES_PERSONALENDUNG_PRÄSENS);
        db.execSQL(SQL_CREATE_ENTRIES_SPRECHVOKAL_PRÄSENS);
        db.execSQL(SQL_CREATE_ENTRIES_SPRECHVOKAL_SUBSTANTIV);
        db.execSQL(SQL_CREATE_ENTRIES_SUBSTANTIV);
        db.execSQL(SQL_CREATE_ENTRIES_VERB);

    }

    /**
     * Deleting all tables and
     * recreating them in 'onCreate(db)'
     *
     * @param db         Database that should be upgraded
     * @param oldVersion old versionNr of the database
     * @param newVersion new versionNr of the database
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_DEKLINATIONSENDUNG);
        db.execSQL(SQL_DELETE_ENTRIES_LEKTION);
        db.execSQL(SQL_DELETE_ENTRIES_PERSONALENDUNG_PRÄSENS);
        db.execSQL(SQL_DELETE_ENTRIES_SPRECHVOKAL_PRÄSENS);
        db.execSQL(SQL_DELETE_ENTRIES_SPRECHVOKAL_SUBSTANTIV);
        db.execSQL(SQL_DELETE_ENTRIES_SUBSTANTIV);
        db.execSQL(SQL_DELETE_ENTRIES_VERB);
        onCreate(db);
    }

    //TODO: Combine all 3 methods into 1?
    public void addRowDeklinationsendung(String name,
                                         String nom_sg, String nom_pl,
                                         String gen_sg, String gen_pl,
                                         String dat_sg, String dat_pl,
                                         String akk_sg, String akk_pl,
                                         String abl_sg, String abl_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_NAME, name);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG, nom_sg);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL, nom_pl);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG, gen_sg);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL, gen_pl);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG, dat_sg);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL, dat_pl);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG, akk_sg);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL, akk_pl);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_ABL_SG, abl_sg);
        values.put(DeklinationsendungDB.FeedEntry.COLUMN_ABL_PL, abl_pl);

        dbConnection.insert(DeklinationsendungDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }

    public void addRowLektion(String titel, String thema) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(LektionDB.FeedEntry.COLUMN_TITEL, titel);
        values.put(LektionDB.FeedEntry.COLUMN_THEMA, thema);

        dbConnection.insert(LektionDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }

    public void addRowPersonalendung_Präsens(String erste_sg, String erste_pl,
                                             String zweite_sg, String zweite_pl,
                                             String dritte_sg, String dritte_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG, erste_sg);
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL, erste_pl);
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG, zweite_sg);
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL, zweite_pl);
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG, dritte_sg);
        values.put(Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL, dritte_pl);

        dbConnection.insert(Personalendung_PräsensDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();

    }

    public void addRowSprechvokal_Präsens(String erste_sg, String erste_pl,
                                          String zweite_sg, String zweite_pl,
                                          String dritte_sg, String dritte_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_SG, erste_sg);
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_1_PL, erste_pl);
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_SG, zweite_sg);
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_2_PL, zweite_pl);
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_SG, dritte_sg);
        values.put(Sprechvokal_PräsensDB.FeedEntry.COLUMN_3_PL, dritte_pl);

        dbConnection.insert(Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();

    }

    public void addRowSprechvokal_Substantiv(
            String nom_sg, String nom_pl,
            String gen_sg, String gen_pl,
            String dat_sg, String dat_pl,
            String akk_sg, String akk_pl,
            String abl_sg, String abl_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_SG, nom_sg);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_NOM_PL, nom_pl);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_SG, gen_sg);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_GEN_PL, gen_pl);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_SG, dat_sg);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_DAT_PL, dat_pl);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_SG, akk_sg);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_AKK_PL, akk_pl);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_SG, abl_sg);
        values.put(Sprechvokal_SubstantivDB.FeedEntry.COLUMN_ABL_PL, abl_pl);

        dbConnection.insert(Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }

    public void addRowSubstantiv(String nom_sg_deutsch, String wortstamm, boolean gelernt,
                                 int lektion_id, int sprechvokal_id, int deklinationsendung_id) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH, nom_sg_deutsch);
        values.put(SubstantivDB.FeedEntry.COLUMN_WORTSTAMM, wortstamm);
        values.put(SubstantivDB.FeedEntry.COLUMN_GELERNT, gelernt ? 1 : 0);
        values.put(SubstantivDB.FeedEntry.COLUMN_LEKTION_ID, lektion_id);
        values.put(SubstantivDB.FeedEntry.COLUMN_SPRECHVOKAL_ID, sprechvokal_id);
        values.put(SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID, deklinationsendung_id);

        dbConnection.insert(SubstantivDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }

    public void addRowVerb(String infinitiv_deutsch, String wortstamm, String konjugation, boolean gelernt,
                           int lektion, int personalendung_id, int sprechvokal_id) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH, infinitiv_deutsch);
        values.put(VerbDB.FeedEntry.COLUMN_WORTSTAMM, wortstamm);
        values.put(VerbDB.FeedEntry.COLUMN_KONJUGATION, konjugation);
        values.put(VerbDB.FeedEntry.COLUMN_GELERNT, gelernt ? 1 : 0);
        values.put(VerbDB.FeedEntry.COLUMN_LEKTION_ID, lektion);
        values.put(VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID, personalendung_id);
        values.put(VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID, sprechvokal_id);

        dbConnection.insert(VerbDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }




    public void addDeklinationsendungEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{
                        addRowDeklinationsendung(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                                tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }

            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addLektionEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{
                        addRowLektion(tokens[0], tokens[1]);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }
            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addPersonalendungEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{

                        addRowPersonalendung_Präsens(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }
            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addSprechvokalPräsensEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{

                        addRowSprechvokal_Präsens(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }
            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addSprechvokalSubstantivEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{

                        addRowSprechvokal_Substantiv(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                                tokens[6], tokens[7], tokens[8], tokens[9]);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }
            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addSubstantivEntriesFromFile(String path, Context context){

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            Cursor cursor;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    try{
                        int deklinationId;

                        cursor = dbConnection.rawQuery(
                                "SELECT " + DeklinationsendungDB.FeedEntry._ID + " FROM " + DeklinationsendungDB.FeedEntry.TABLE_NAME
                                        + " WHERE " + DeklinationsendungDB.FeedEntry.COLUMN_NAME + " = ?",
                                new String[]{tokens[5]}
                        );
                        cursor.moveToNext();
                        deklinationId = cursor.getInt(0);

                        //TODO: Sprechvokale einfügen (nicht '0')
                        addRowSubstantiv(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]), 0, deklinationId);

                        closeDb();
                        reopenDb();
                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }

            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addVerbEntriesFromFile(String path, Context context) {

        try{
            InputStream inputStream = context.getAssets().open(path);
            //InputStream inputStream = getClass().getResourceAsStream(path);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(1000000000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //count the total number of lines
            int lineAmount = 0;

            while(bufferedReader.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bufferedInputStream.reset();
            bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            //Skip the first line with column headings
            bufferedReader.readLine();

            reopenDb();

            //goes through every line and adds its content to the table
            String line;

            for(int i = 0; i < lineAmount - 1; i++){
                line = bufferedReader.readLine();

                if(line != null){
                    String[] tokens = line.split(";");

                    //TODO
                    try{

                        //TODO: read 'gelernt' from file
                        //TODO: Sprechvokale einfügen (nicht '0')
                        //TODO: Personalendungen benennen
                        addRowVerb(tokens[0], tokens[1], tokens[2], false, Integer.parseInt(tokens[4]), 0, 0);

                    }catch (NumberFormatException nfe){
                        nfe.printStackTrace();
                    }
                }
            }
            inputStream.close();
            bufferedInputStream.close();
            bufferedReader.close();
            closeDb();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void closeDb() {
        dbConnection.close();
    }

    public void reopenDb() {
        if (dbConnection != null && dbConnection.isOpen()) close();
        dbConnection = getWritableDatabase();
    }


    public int countTableEntries(String[] tables, int lektionNr){

        Cursor cursor = null;
        int count = 0;
        reopenDb();

        for(String table : tables){

            //getting the total number of entries which were completed and adding it to 'complete'
            cursor = dbConnection.rawQuery("SELECT COUNT(*) FROM " + table
                            + " WHERE Lektion_ID = ?",
                    new String[] {""+lektionNr});
            cursor.moveToNext();
            count += cursor.getInt(0);
        }
        cursor.close();
        closeDb();

        return count;
    }

    public int countTableEntries(String[] tables){

        Cursor cursor = null;
        int count = 0;
        reopenDb();

        for(String table : tables){

            //getting the total number of entries which were completed and adding it to 'complete'
            cursor = dbConnection.rawQuery("SELECT COUNT(*) FROM " + table,
                    new String[] {});
            cursor.moveToNext();
            count += cursor.getInt(0);
        }
        cursor.close();
        closeDb();

        return count;
    }

    public Cursor getCursorFromId(int id, String table){

        reopenDb();
        Cursor cursor;
        cursor = dbConnection.rawQuery("SELECT * FROM "+ table +" WHERE _ID = ?",
                new String[] {""+ id});

        return  cursor;
    }

    public String getDeklinierteVokabel(int vokabelID, String deklinationsendungsName){

        reopenDb();

        Cursor substantivCursor = dbConnection.rawQuery("SELECT * FROM "+ SubstantivDB.FeedEntry.TABLE_NAME +
                        " WHERE _ID = ?",
                new String[] {""+vokabelID});

        substantivCursor.moveToNext();
        String substantiv = substantivCursor.getString(substantivCursor.getColumnIndex("Wortstamm"));
        substantivCursor.close();

        Cursor endungsCursor = dbConnection.rawQuery(
                "SELECT "
                    + DeklinationsendungDB.FeedEntry.TABLE_NAME+".*" +
                " FROM " +
                    DeklinationsendungDB.FeedEntry.TABLE_NAME + ", " +
                    SubstantivDB.FeedEntry.TABLE_NAME +
                " WHERE " +
                        SubstantivDB.FeedEntry.TABLE_NAME+"."+SubstantivDB.FeedEntry._ID +
                            " = " +
                        "?" +
                    " AND " +
                        SubstantivDB.FeedEntry.TABLE_NAME+"."+SubstantivDB.FeedEntry.COLUMN_DEKLINATIONSENDUNG_ID +
                            " = " +
                        DeklinationsendungDB.FeedEntry.TABLE_NAME+"."+DeklinationsendungDB.FeedEntry._ID
                , new String[] {""+vokabelID}
        );

        endungsCursor.moveToNext();
        String endung = endungsCursor.getString(endungsCursor.getColumnIndex(deklinationsendungsName));
        endungsCursor.close();

        String deklinierteVokabel = substantiv + endung;

        closeDb();

        return deklinierteVokabel;
    }

    /*
    TODO: Ausprobieren / Fixen
     */
    public String getKonjugierteVokabel(int vokabelID, String personalendung){

        reopenDb();

        Cursor verbCursor = null;
        verbCursor = dbConnection.rawQuery("SELECT * FROM " + VerbDB.FeedEntry.TABLE_NAME +
                        " WHERE _ID = ?",
                new String[] {"" + vokabelID});

        verbCursor.moveToNext();
        String verb = verbCursor.getString(verbCursor.getColumnIndex("Wortstamm"));

        Cursor personalendungsCursor = null;
        personalendungsCursor = dbConnection.rawQuery("SELECT " + Personalendung_PräsensDB.FeedEntry.TABLE_NAME + ".? FROM " +
                Personalendung_PräsensDB.FeedEntry.TABLE_NAME,
                new String[] {""+ personalendung});

        personalendungsCursor.moveToNext();
        String endung = personalendungsCursor.getString(personalendungsCursor.getColumnIndex(personalendung));

        String konjugiertesVerb = verb + endung;

        closeDb();

        return konjugiertesVerb;
    }

    public String getInfinitiv (int verbID){

        reopenDb();

        Cursor verbCursor;
        verbCursor = dbConnection.rawQuery("SELECT Wortstamm FROM " + VerbDB.FeedEntry.TABLE_NAME
        + " WHERE " + VerbDB.FeedEntry._ID + " = ?", new String[] {""+ verbID});

        verbCursor.moveToNext();
        //TODO: getString(0) reicht?
        String verb = verbCursor.getString(verbCursor.getColumnIndex("Wortstamm"));
        verbCursor.close();

        String infinitiv = verb + "re";

        closeDb();

        return infinitiv;
    }

    /**
     * This method is for the class 'AndroidDatabaseManager'
     * Remove this before release
     * @param Query
     * @return
     */
    public ArrayList<Cursor> getData (String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

}