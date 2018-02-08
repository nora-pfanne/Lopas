package com.example.norablakaj.lateinapp.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.norablakaj.lateinapp.Databases.Tables.AdverbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.LektionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Personalendung_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.Tables.PräpositionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Sprechvokal_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Sprechvokal_SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SprichwortDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static com.example.norablakaj.lateinapp.Databases.SQL_DUMP.*;

/**
 * DBHelper is used for managing the database and its tables.
 * All queries are done in this class and called where they are needed
 */
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    //Version of the database
    private static final int DATABASE_VERSION = 1;

    //Name of the database file
    private static final String DATABASE_NAME = "Database.db";

    /**
     * Constructor
     * Used
     * @param context lets newly-created objects understand what has been going on
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        /*
        TODO: Maybe add check for the amount of entries against the amount of entries in the file:
            if (table_1.countEntries() < file.entryAmount){
              addInitialEntry();
            }
        */
        if(countTableEntries(allTables) == 0) {
            addRowSprechvokal_Substantiv("", "", "", "", "", "", "", "", "", "");
            addRowSprechvokal_Präsens("", "", "", "", "", "");

            addEntriesFromFile("deklinationsendung.csv", DeklinationsendungDB.FeedEntry.TABLE_NAME ,context);
            addEntriesFromFile("lektion.csv", LektionDB.FeedEntry.TABLE_NAME, context);
            addEntriesFromFile("personalendung_präsens.csv", Personalendung_PräsensDB.FeedEntry.TABLE_NAME, context);
            //addEntriesFromFile("", Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME, context);
            addEntriesFromFile("substantiv.csv", SubstantivDB.FeedEntry.TABLE_NAME, context);
            addEntriesFromFile("verb.csv", VerbDB.FeedEntry.TABLE_NAME, context);
            addEntriesFromFile("adverb.csv", AdverbDB.FeedEntry.TABLE_NAME, context);
            //addEntriesFromFile("", SprichwortDB.FeedEntry.TABLE_NAME, context);
            //addEntriesFromFile("", PräpositionDB.FeedEntry.TABLE_NAME, context);
        }

    }

    /**
     * Creating all tables
     *
     * @param db database where the tables are supposed to be put into
     */
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(SQL_CREATE_ENTRIES_ADVERB);
        db.execSQL(SQL_CREATE_ENTRIES_DEKLINATIONSENDUNG);
        db.execSQL(SQL_CREATE_ENTRIES_LEKTION);
        db.execSQL(SQL_CREATE_ENTRIES_PERSONALENDUNG_PRÄSENS);
        db.execSQL(SQL_CREATE_ENTRIES_PRAEPOSITION);
        db.execSQL(SQL_CREATE_ENTRIES_SPRECHVOKAL_PRÄSENS);
        db.execSQL(SQL_CREATE_ENTRIES_SPRECHVOKAL_SUBSTANTIV);
        db.execSQL(SQL_CREATE_ENTRIES_SPRICHWORT);
        db.execSQL(SQL_CREATE_ENTRIES_SUBSTANTIV);
        db.execSQL(SQL_CREATE_ENTRIES_VERB);
        
    }

    /**
     * Deleting all tables and
     * recreating them through 'onCreate(db)'
     *
     * @param db         Database that should be upgraded
     * @param oldVersion old versionNr of the database
     * @param newVersion new versionNr of the database
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES_ADVERB);
        db.execSQL(SQL_DELETE_ENTRIES_DEKLINATIONSENDUNG);
        db.execSQL(SQL_DELETE_ENTRIES_LEKTION);
        db.execSQL(SQL_DELETE_ENTRIES_PERSONALENDUNG_PRÄSENS);
        db.execSQL(SQL_DELETE_ENTRIES_PRAEPOSITION);
        db.execSQL(SQL_DELETE_ENTRIES_SPRECHVOKAL_PRÄSENS);
        db.execSQL(SQL_DELETE_ENTRIES_SPRECHVOKAL_SUBSTANTIV);
        db.execSQL(SQL_DELETE_ENTRIES_SPRICHWORT);
        db.execSQL(SQL_DELETE_ENTRIES_SUBSTANTIV);
        db.execSQL(SQL_DELETE_ENTRIES_VERB);
        onCreate(db);
    }

    /**
     * Adds a entry to the 'Adverb' table in the database with given parameters
     * @param deutsch content of the column 'deutsch' in the database entry
     * @param latein content of the column 'latein' in the database entry
     * @param gelernt content of the column 'gelernt' in the database entry
     * @param lektion_id foreign key: the corresponding entry from the 'Lektion' table
     */
    private void addRowAdverb(String deutsch, String latein, boolean gelernt,
                             int lektion_id){

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsAdverb[1], deutsch);
        values.put(allColumnsAdverb[2], latein);
        values.put(allColumnsAdverb[3], gelernt ? 1 : 0);
        values.put(allColumnsAdverb[4], lektion_id);

        database.insert(AdverbDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Deklinationsendung' table in the database with given parameters
     * @param name content of the column 'name' in the database entry
     * @param nom_sg content of the column 'nom_sg' in the database entry
     * @param nom_pl content of the column 'nom_pl' in the database entry
     * @param gen_sg content of the column 'gen_sg' in the database entry
     * @param gen_pl content of the column 'gen_pl' in the database entry
     * @param dat_sg content of the column 'dat_sg' in the database entry
     * @param dat_pl content of the column 'dat_pl' in the database entry
     * @param akk_sg content of the column 'akk_sg' in the database entry
     * @param akk_pl content of the column 'akk_pl' in the database entry
     * @param abl_sg content of the column 'abl_sg' in the database entry
     * @param abl_pl content of the column 'abl_pl' in the database entry
     */
    private void addRowDeklinationsendung(String name,
                                         String nom_sg, String nom_pl,
                                         String gen_sg, String gen_pl,
                                         String dat_sg, String dat_pl,
                                         String akk_sg, String akk_pl,
                                         String abl_sg, String abl_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsDeklinationsendung[1], name);
        values.put(allColumnsDeklinationsendung[2], nom_sg);
        values.put(allColumnsDeklinationsendung[3], nom_pl);
        values.put(allColumnsDeklinationsendung[4], gen_sg);
        values.put(allColumnsDeklinationsendung[5], gen_pl);
        values.put(allColumnsDeklinationsendung[6], dat_sg);
        values.put(allColumnsDeklinationsendung[7], dat_pl);
        values.put(allColumnsDeklinationsendung[8], akk_sg);
        values.put(allColumnsDeklinationsendung[9], akk_pl);
        values.put(allColumnsDeklinationsendung[10], abl_sg);
        values.put(allColumnsDeklinationsendung[11], abl_pl);

        database.insert(DeklinationsendungDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Lektion' table in the database with given parameters
     * @param titel content of the column 'titel' in the database entry
     * @param thema content of the column 'thema' in the database entry
     */
    private void addRowLektion(String titel, String thema) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsLektion[1], titel);
        values.put(allColumnsLektion[2], thema);

        database.insert(LektionDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Personalendung_Präsens' table in the database with given parameters
     * @param erste_sg content of the column 'erste_sg' in the database entry
     * @param zweite_sg content of the column 'zweite_sg' in the database entry
     * @param dritte_sg content of the column 'dritte_sg' in the database entry
     * @param erste_pl content of the column 'erste_pl' in the database entry
     * @param zweite_pl content of the column 'zweite_pl' in the database entry
     * @param dritte_pl content of the column 'dritte_pl' in the database entry
     */
    private void addRowPersonalendung_Präsens(String erste_sg, String zweite_sg,
                                             String dritte_sg, String erste_pl,
                                             String zweite_pl, String dritte_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsPersonalendung_Präsens[1], erste_sg);
        values.put(allColumnsPersonalendung_Präsens[2], erste_pl);
        values.put(allColumnsPersonalendung_Präsens[3], zweite_sg);
        values.put(allColumnsPersonalendung_Präsens[4], zweite_pl);
        values.put(allColumnsPersonalendung_Präsens[5], dritte_sg);
        values.put(allColumnsPersonalendung_Präsens[6], dritte_pl);

        database.insert(Personalendung_PräsensDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();

    }
    /**
     * Adds a entry to the 'Präposition' table in the database with given parameters
     * @param deutsch content of the column 'deutsch' in the database entry
     * @param latein content of the column 'latein' in the database entry
     * @param gelernt content of the column 'gelernt' in the database entry
     * @param lektion_id foreign key: the corresponding entry from the 'Lektion' table
     */
    private void addRowPräposition(String deutsch, String latein, boolean gelernt,
                                   int lektion_id){

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsPraeposition[1], deutsch);
        values.put(allColumnsPraeposition[1], latein);
        values.put(allColumnsPraeposition[1], gelernt ? 1 : 0);
        values.put(allColumnsPraeposition[1], lektion_id);

        database.insert(PräpositionDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Sprechvokal_Präsens' table in the database with given parameters
     * @param erste_sg content of the column 'erste_sg' in the database entry
     * @param zweite_sg content of the column 'zweite_sg' in the database entry
     * @param dritte_sg content of the column 'dritte_sg' in the database entry
     * @param erste_pl content of the column 'erste_pl' in the database entry
     * @param zweite_pl content of the column 'zweite_pl' in the database entry
     * @param dritte_pl content of the column 'dritte_pl' in the database entry
     */
    private void addRowSprechvokal_Präsens(String erste_sg, String zweite_sg,
                                          String dritte_sg, String erste_pl,
                                          String zweite_pl, String dritte_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsSprechvokal_Präsens[1], erste_sg);
        values.put(allColumnsSprechvokal_Präsens[2], erste_pl);
        values.put(allColumnsSprechvokal_Präsens[3], zweite_sg);
        values.put(allColumnsSprechvokal_Präsens[4], zweite_pl);
        values.put(allColumnsSprechvokal_Präsens[5], dritte_sg);
        values.put(allColumnsSprechvokal_Präsens[6], dritte_pl);

        database.insert(Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();

    }
    /**
     * Adds a entry to the 'Sprechvokal_Substantiv' table in the database with given parameters
     * @param nom_sg content of the column '' in the database entry
     * @param nom_pl content of the column '' in the database entry
     * @param gen_sg content of the column '' in the database entry
     * @param gen_pl content of the column '' in the database entry
     * @param dat_sg content of the column '' in the database entry
     * @param dat_pl content of the column '' in the database entry
     * @param akk_sg content of the column '' in the database entry
     * @param akk_pl content of the column '' in the database entry
     * @param abl_sg content of the column '' in the database entry
     * @param abl_pl content of the column '' in the database entry
     */
    private void addRowSprechvokal_Substantiv(
            String nom_sg, String nom_pl,
            String gen_sg, String gen_pl,
            String dat_sg, String dat_pl,
            String akk_sg, String akk_pl,
            String abl_sg, String abl_pl) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsSprechvokal_Substantiv[1], nom_sg);
        values.put(allColumnsSprechvokal_Substantiv[2], nom_pl);
        values.put(allColumnsSprechvokal_Substantiv[3], gen_sg);
        values.put(allColumnsSprechvokal_Substantiv[4], gen_pl);
        values.put(allColumnsSprechvokal_Substantiv[5], dat_sg);
        values.put(allColumnsSprechvokal_Substantiv[6], dat_pl);
        values.put(allColumnsSprechvokal_Substantiv[7], akk_sg);
        values.put(allColumnsSprechvokal_Substantiv[8], akk_pl);
        values.put(allColumnsSprechvokal_Substantiv[9], abl_sg);
        values.put(allColumnsSprechvokal_Substantiv[10], abl_pl);

        database.insert(Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Sprichwort' table in the database with given parameters
     * @param deutsch content of the column 'deutsch' in the database entry
     * @param latein content of the column 'latein' in the database entry
     * @param gelernt content of the column 'gelernt' in the database entry
     * @param lektion_id foreign key: the corresponding entry from the 'Lektion' table
     */
    private void addRowSprichwort(String deutsch, String latein, boolean gelernt,
                                 int lektion_id){

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsSprichwort[1], deutsch);
        values.put(allColumnsSprichwort[2], latein);
        values.put(allColumnsSprichwort[3], gelernt ? 1 : 0);
        values.put(allColumnsSprichwort[4], lektion_id);

        database.insert(SprichwortDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Substantiv' table in the database with given parameters
     * @param nom_sg_deutsch content of the column '' in the database entry
     * @param wortstamm content of the column '' in the database entry
     * @param gelernt content of the column '' in the database entry
     * @param lektion_id foreign key: the corresponding entry from the 'Lektion' table
     * @param sprechvokal_id foreign key: the corresponding entry from the 'Sprechvokal_Substantiv' table
     * @param deklinationsendung_id foreign key: the corresponding entry from the 'Deklinationsendung' table
     */
    private void addRowSubstantiv(String nom_sg_deutsch, String wortstamm, boolean gelernt,
                                 int lektion_id, int sprechvokal_id, int deklinationsendung_id) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsSubstantiv[1], nom_sg_deutsch);
        values.put(allColumnsSubstantiv[2], wortstamm);
        values.put(allColumnsSubstantiv[3], gelernt ? 1 : 0);
        values.put(allColumnsSubstantiv[4], lektion_id);
        values.put(allColumnsSubstantiv[5], sprechvokal_id);
        values.put(allColumnsSubstantiv[6], deklinationsendung_id);

        database.insert(SubstantivDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }
    /**
     * Adds a entry to the 'Verb' table in the database with given parameters
     * @param infinitiv_deutsch content of the column 'infinitiv_deutsch' in the database entry
     * @param wortstamm content of the column 'wortstamm' in the database entry
     * @param konjugation content of the column 'konjugation' in the database entry
     * @param gelernt content of the column 'gelernt' in the database entry
     * @param lektion_id foreign key: the corresponding entry from the 'Lektion' table
     * @param personalendung_id foreign key: the corresponding entry from the 'Personalendung_Präsens' table
     * @param sprechvokal_id foreign key: the corresponding entry from the 'Sprechvokal_Substantiv' table
     */
    private void addRowVerb(String infinitiv_deutsch, String wortstamm, String konjugation, boolean gelernt,
                           int lektion_id, int personalendung_id, int sprechvokal_id) {

        reopenDb();

        ContentValues values = new ContentValues();
        values.put(allColumnsVerb[1], infinitiv_deutsch);
        values.put(allColumnsVerb[2], wortstamm);
        values.put(allColumnsVerb[3], konjugation);
        values.put(allColumnsVerb[4], gelernt ? 1 : 0);
        values.put(allColumnsVerb[5], lektion_id);
        values.put(allColumnsVerb[6], personalendung_id);
        values.put(allColumnsVerb[7], sprechvokal_id);

        database.insert(VerbDB.FeedEntry.TABLE_NAME, null, values);

        closeDb();
    }

    /**
     * Entries are added to a specified table from a file under a given path. (probably a .csv file)
     * 1 row represents a entry
     * the ';' separate the different column of each entry
     * @param path Path of the file relative to the 'assets' folder of the project
     * @param table Name of the table where the entries are to be added to
     * @param context Application context
     */
    private void addEntriesFromFile(String path, String table, Context context){
        try{
            InputStream inputStream = context.getAssets().open(path);
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

                    try {
                        switch (table) {

                            case AdverbDB.FeedEntry.TABLE_NAME:
                                //TODO: Why is this needed here
                                tokens[3] = tokens[3].replaceAll("[^\\d.]", "");

                                //TODO: read 'gelernt' from file
                                addRowAdverb(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]));

                                break;

                            case DeklinationsendungDB.FeedEntry.TABLE_NAME:
                                addRowDeklinationsendung(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5],
                                        tokens[6], tokens[7], tokens[8], tokens[9], tokens[10]);
                                break;

                            case LektionDB.FeedEntry.TABLE_NAME:
                                addRowLektion(tokens[0], tokens[1]);
                                break;

                            case Personalendung_PräsensDB.FeedEntry.TABLE_NAME:
                                addRowPersonalendung_Präsens(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                                break;

                            case PräpositionDB.FeedEntry.TABLE_NAME:
                                //TODO: Why is this needed here: removes the �
                                tokens[3] = tokens[3].replaceAll("[^\\d.]", "");

                                //TODO: read 'gelernt' from file
                                addRowPräposition(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]));

                                break;

                            case Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME:
                                //TODO: Why is this needed here: removes the �
                                tokens[3] = tokens[3].replaceAll("[^\\d.]", "");

                                //TODO: read 'gelernt' from file
                                addRowPräposition(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]));

                                break;

                            case Sprechvokal_SubstantivDB.FeedEntry.TABLE_NAME:
                                break;

                            case SprichwortDB.FeedEntry.TABLE_NAME:
                                //TODO: Why is this needed here
                                tokens[3] = tokens[3].replaceAll("[^\\d.]", "");

                                //TODO: read 'gelernt' from file
                                addRowSprichwort(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]));

                                break;

                            case SubstantivDB.FeedEntry.TABLE_NAME:
                                int deklinationId;

                                String query = "SELECT " + DeklinationsendungDB.FeedEntry._ID +
                                        " FROM " + DeklinationsendungDB.FeedEntry.TABLE_NAME +
                                        " WHERE " + DeklinationsendungDB.FeedEntry.COLUMN_NAME + " = ?";
                                Cursor cursor = database.rawQuery(query,
                                        new String[]{tokens[5]}
                                );
                                cursor.moveToNext();
                                deklinationId = cursor.getInt(0);
                                cursor.close();

                                //TODO: Sprechvokale einfügen (nicht '0')
                                addRowSubstantiv(tokens[0], tokens[1], false, Integer.parseInt(tokens[3]), 0, deklinationId);

                                closeDb();
                                reopenDb();
                                break;

                            case VerbDB.FeedEntry.TABLE_NAME:
                                //TODO: read 'gelernt' from file
                                //TODO: Sprechvokale einfügen (nicht '1')
                                //TODO: Personalendungen benennen
                                addRowVerb(tokens[0],
                                        tokens[1],
                                        tokens[2],
                                        false,
                                        Integer.parseInt(tokens[4]),
                                        1,
                                        1);
                                break;

                            default:
                                Log.e(DBHelper.class.getName(),
                                        "A table was not found while trying to add a entry from a file:\n" +
                                                "Method: DBHelper.class -> addEntriesFromFile(String path, String table, String context\n" +
                                                "Table: " + table);
                                break;
                        }
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

    /**
     * Closes the connection to the database if it is open
     */
    private void closeDb() {
        database.close();
    }

    /**
     * Reopens the connection to the database if it isn't open already
     */
    private void reopenDb() {
        if (database != null && database.isOpen()) close();
        database = getWritableDatabase();
    }

    /**
     * Counts the entries of all tables in the tables Array with a specific 'Lektion_id'
     * ONLY WORKS IF EVERY TABLE IN THE ARRAY HAS A FOREIGN KEY NAMED 'Lektion_id'
     * @param tables Array of all tables, where the entries are to be counted
     * @param lektionNr the id of the 'lektion', where the entries are to be counted
     * @return the amount of entries in the tables of the array; returns -1 if the any table doesn't have 'Lektion_id' as foreign key
     */
    public int countTableEntries(String[] tables, int lektionNr){

        //TODO: Currently only works for tables containing the column 'Lektion_ID' -> maybe make it general?
        //TODO: Maybe with try/catch?
        Cursor cursor = null;
        int count = 0;
        reopenDb();

        for(String table : tables){

            try {
                //getting the total number of entries which were completed and adding it to 'complete'
                cursor = database.rawQuery("SELECT COUNT(*) FROM " + table
                                + " WHERE Lektion_ID = ?",
                        new String[] {""+lektionNr});
                cursor.moveToNext();
                count += cursor.getInt(0);
            }catch (Exception e){
                e.printStackTrace();
                return -1;
            }

        }
        if (cursor != null) cursor.close();
        closeDb();

        return count;
    }

    /**
     * Counts the entries of all tables in the tables Array
     * @param tables Array of all tables, where the entries are to be counted
     * @return the amount of entries in the tables of the array
     */
    public int countTableEntries(String[] tables){

        Cursor cursor = null;
        int count = 0;
        reopenDb();

        for(String table : tables){

            //getting the total number of entries which were completed and adding it to 'complete'
            cursor = database.rawQuery("SELECT COUNT(*) FROM " + table,
                    new String[] {});
            cursor.moveToNext();
            count += cursor.getInt(0);
        }

        if (cursor != null) cursor.close();
        closeDb();

        return count;
    }

    public int countTableEntries(String table, int lektionNr){
        //TODO: Currently only works for tables containing the column 'Lektion_ID' -> maybe make it general?
        //TODO: Maybe with try/catch?
        Cursor cursor;
        int count = 0;
        reopenDb();

        try {
            //getting the total number of entries which were completed and adding it to 'complete'
            cursor = database.rawQuery("SELECT COUNT(*) FROM " + table
                            + " WHERE Lektion_ID = ?",
                    new String[] {""+lektionNr});
            cursor.moveToNext();
            count += cursor.getInt(0);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }


        cursor.close();
        closeDb();

        return count;
    }

    private String getLateinFromId(int id, String table){
        reopenDb();

        Cursor cursor = database.rawQuery("SELECT Latein" +
                                                " FROM " + table +
                                                " WHERE _ID = " + id
        , new String[]{});

        cursor.moveToNext();

        String latein = cursor.getString(0);

        cursor.close();
        closeDb();

        return latein;
    }

    /**
     * Returns a noun in the right declination
     * @param vokabelID ID of the entry in the table 'Substantiv'
     * @param deklinationsendungsName Column of the table 'Deklinationsendung' where the right declination-end-part of the needed word can be found
     * @return the final word in the right declination
     */
    public String getDekliniertenSubstantiv(int vokabelID, String deklinationsendungsName){

        reopenDb();

        String query = "SELECT " + SubstantivDB.FeedEntry.COLUMN_WORTSTAMM +
                       " FROM "+ SubstantivDB.FeedEntry.TABLE_NAME +
                       " WHERE _ID = ?";
        Cursor substantivCursor = database.rawQuery(query,
                                                        new String[] {""+vokabelID});
        substantivCursor.moveToNext();
        String substantiv = substantivCursor.getString(0);
        substantivCursor.close();

        query = "SELECT "
                + DeklinationsendungDB.FeedEntry.TABLE_NAME+"."+deklinationsendungsName +
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
                DeklinationsendungDB.FeedEntry.TABLE_NAME+"."+DeklinationsendungDB.FeedEntry._ID;
        //TODO: Is ""+deklinationsname nöitg -> konvertierung String-String ist redundant
        Cursor endungCursor = database.rawQuery(query,
                                                    new String[] {""+vokabelID}
        );
        endungCursor.moveToNext();
        String endung = endungCursor.getString(0);
        endungCursor.close();

        closeDb();

        return (substantiv + endung);

    }

    public String getKonjugiertesVerb(int vokabelID, String personalendung){

        reopenDb();

        String query = "SELECT * FROM " + VerbDB.FeedEntry.TABLE_NAME + " WHERE _ID = ?";
        Cursor verbCursor = database.rawQuery(query,
                                                  new String[] {"" + vokabelID});
        verbCursor.moveToNext();
        String verb = verbCursor.getString(verbCursor.getColumnIndex("Wortstamm"));
        verbCursor.close();

        //TODO: Sprechvokal fehlt noch, da es noch keine Vokabeln mit Sprechvokalen in den bisherigen Lektionen gibt

        String endung;
        if (personalendung.equals("inf") || personalendung.equals("infinitiv") ||
            personalendung.equals("Inf") || personalendung.equals("Infinitiv")){
            endung = "re";
        }else{

            query = "SELECT "
                    + Personalendung_PräsensDB.FeedEntry.TABLE_NAME+".?" +
                    " FROM " +
                    Personalendung_PräsensDB.FeedEntry.TABLE_NAME + ", " +
                    VerbDB.FeedEntry.TABLE_NAME +
                    " WHERE " +
                    VerbDB.FeedEntry.TABLE_NAME+"."+VerbDB.FeedEntry._ID +
                    " = " +
                    "?" +
                    " AND " +
                    VerbDB.FeedEntry.TABLE_NAME+"."+VerbDB.FeedEntry.COLUMN_PERSONALENDUNG_ID +
                    " = " +
                    Personalendung_PräsensDB.FeedEntry.TABLE_NAME+"."+Personalendung_PräsensDB.FeedEntry._ID;
            Cursor personalendungCursor = database.rawQuery(query ,
                                                                new String[] {""+personalendung, ""+vokabelID}
            );
            personalendungCursor.moveToNext();
            endung = personalendungCursor.getString(0);
            personalendungCursor.close();
        }

        closeDb();

        return (verb + endung);
    }

    public String getRandomVocabulary(int lektionNr, Context context){

        String lateinVokabel = "keine Vokabel ausgewählt!";

        int prevLektionSubstantivCount = 0;
        int prevLektionVerbCount = 0;
        int prevLektionPräpositionCount = 0;
        int prevLektionSprichwortCount = 0;
        int prevLektionAdverbCount = 0;
        for (int i = 1; i < lektionNr; i++){
            prevLektionSubstantivCount += countTableEntries(SubstantivDB.FeedEntry.TABLE_NAME, i);
            prevLektionVerbCount += countTableEntries(VerbDB.FeedEntry.TABLE_NAME, i);
            prevLektionPräpositionCount += countTableEntries(PräpositionDB.FeedEntry.TABLE_NAME, i);
            prevLektionSprichwortCount += countTableEntries(SprichwortDB.FeedEntry.TABLE_NAME, i);
            prevLektionAdverbCount += countTableEntries(AdverbDB.FeedEntry.TABLE_NAME, i);
        }

        int entryAmountVerb = countTableEntries(VerbDB.FeedEntry.TABLE_NAME, lektionNr);
        int entryAmountSubstantiv = countTableEntries(SubstantivDB.FeedEntry.TABLE_NAME, lektionNr);
        int entryAmountPräposition = countTableEntries(PräpositionDB.FeedEntry.TABLE_NAME, lektionNr);
        int entryAmountSprichwort = countTableEntries(SprichwortDB.FeedEntry.TABLE_NAME, lektionNr);
        int entryAmountAdverb = countTableEntries(AdverbDB.FeedEntry.TABLE_NAME, lektionNr);

        int entryAmountTotal = entryAmountSubstantiv + entryAmountVerb + entryAmountPräposition + entryAmountSprichwort + entryAmountAdverb;

        Random rand = new Random();
        int randomNumber = rand.nextInt(entryAmountTotal);

        if(randomNumber < entryAmountSubstantiv){

            //increments randomNumber by 1 because _ID in the tables starts with '1' not '0'
            randomNumber++;

            String deklinationsendung = DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG;
            lateinVokabel = getDekliniertenSubstantiv(randomNumber + prevLektionSubstantivCount, deklinationsendung);

        } else if (randomNumber-entryAmountSubstantiv < entryAmountVerb){

            //increments randomNumber by 1 because _ID in the tables starts with '1' not '0'
            randomNumber++;

            lateinVokabel = getKonjugiertesVerb(
                    randomNumber-entryAmountSubstantiv + prevLektionVerbCount,
                    "inf");

        }else if (randomNumber-entryAmountSubstantiv-entryAmountVerb < entryAmountPräposition){

            //increments randomNumber by 1 because _ID in the tables starts with '1' not '0'
            randomNumber++;
            lateinVokabel = getLateinFromId(randomNumber-entryAmountSubstantiv-entryAmountVerb-entryAmountPräposition + prevLektionPräpositionCount,
                            PräpositionDB.FeedEntry.TABLE_NAME);
        }else if (randomNumber-entryAmountSubstantiv-entryAmountVerb-entryAmountPräposition < entryAmountSprichwort){

            //increments randomNumber by 1 because _ID in the tables starts with '1' not '0'
            randomNumber++;
            lateinVokabel = getLateinFromId(randomNumber-entryAmountSubstantiv-entryAmountVerb-entryAmountPräposition + prevLektionSprichwortCount,
                            SprichwortDB.FeedEntry.TABLE_NAME);

        }else if(randomNumber-entryAmountSubstantiv-entryAmountVerb-entryAmountPräposition-entryAmountSprichwort < entryAmountAdverb){

            //increments randomNumber by 1 because _ID in the tables starts with '1' not '0'
            randomNumber++;
            lateinVokabel = getLateinFromId(randomNumber-entryAmountSubstantiv-entryAmountVerb-entryAmountPräposition-entryAmountSprichwort + prevLektionAdverbCount,
                            AdverbDB.FeedEntry.TABLE_NAME);

        }else{
            Log.e(DBHelper.class.getName(), "entry_id given by the randomNumber is out of bounds -> bigger than the amount of all entries combined");
        }

        return lateinVokabel;
    }

    /**
     * This method is for the class 'AndroidDatabaseManager'
     * Remove this before release
     * @param Query
     * @return
     */
    @SuppressWarnings("all")
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