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

public class DBHelper extends SQLiteOpenHelper {

    //Creating a String for quick access to a creation command for all tables
    private static final String SQL_CREATE_ENTRIES_LEKTION =
            "CREATE TABLE "
            + LektionDB.FeedEntry.TABLE_NAME
            + " ("
            + LektionDB.FeedEntry._ID
            + " INTEGER PRIMARY KEY, "
            + LektionDB.FeedEntry.COLUMN_THEMA
            + " TEXT, "
            + LektionDB.FeedEntry.COLUMN_BESCHREIBUNG
            + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_VERB =
            "CREATE TABLE "
                    + VerbDB.FeedEntry.TABLE_NAME
                    + " ("
                    + VerbDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + VerbDB.FeedEntry.COLUMN_LATEIN
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_DEUTSCH
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_HINWEIS
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_VERBFORM
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_KONJUGATION
                    + " TEXT, "
                    + VerbDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + VerbDB.FeedEntry.COLUMN_LEKTIONID
                    + " INTEGER, FOREIGN KEY("
                    + VerbDB.FeedEntry.COLUMN_LEKTIONID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + "))";



    private static final String SQL_CREATE_ENTRIES_NOMEN =
            "CREATE TABLE "
                    + NomenDB.FeedEntry.TABLE_NAME
                    + " ("
                    + NomenDB.FeedEntry._ID
                    + " INTEGER PRIMARY KEY, "
                    + NomenDB.FeedEntry.COLUMN_LATEIN
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_DEUTSCH
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_HINWEIS
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_GENITIV
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_GENUS
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_DEKLINATION
                    + " TEXT, "
                    + NomenDB.FeedEntry.COLUMN_GELERNT
                    + " INTEGER, "
                    + NomenDB.FeedEntry.COLUMN_LEKTIONID
                    + " INTEGER, FOREIGN KEY("
                    + NomenDB.FeedEntry.COLUMN_LEKTIONID
                    + ") REFERENCES "
                    + LektionDB.FeedEntry.TABLE_NAME
                    + "("
                    + LektionDB.FeedEntry._ID
                    + "))";

    //creating a String for quick access to a deletion command for all tables
    private static final String SQL_DELETE_ENTRIES_LEKTION =
            "DROP TABLES IF EXISTS "
                    + LektionDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_VERB =
            "DROP TABLES IF EXISTS "
                    + VerbDB.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_NOMEN =
            "DROP TABLES IF EXISTS "
                    + NomenDB.FeedEntry.TABLE_NAME;


    //Lists all columns of the tables in a String[]
    private static final String[] allColumnsLektion = {
            LektionDB.FeedEntry._ID,
            LektionDB.FeedEntry.COLUMN_THEMA,
            LektionDB.FeedEntry.COLUMN_BESCHREIBUNG
    };

    private static final String[] allColumnsVerb = {
            VerbDB.FeedEntry._ID,
            VerbDB.FeedEntry.COLUMN_LATEIN,
            VerbDB.FeedEntry.COLUMN_DEUTSCH,
            VerbDB.FeedEntry.COLUMN_HINWEIS,
            VerbDB.FeedEntry.COLUMN_VERBFORM,
            VerbDB.FeedEntry.COLUMN_KONJUGATION,
            VerbDB.FeedEntry.COLUMN_GELERNT,
            VerbDB.FeedEntry.COLUMN_LEKTIONID
    };

    private static final String[] allColumnsNomen = {
            NomenDB.FeedEntry._ID,
            NomenDB.FeedEntry.COLUMN_LATEIN,
            NomenDB.FeedEntry.COLUMN_DEUTSCH,
            NomenDB.FeedEntry.COLUMN_HINWEIS,
            NomenDB.FeedEntry.COLUMN_GENITIV,
            NomenDB.FeedEntry.COLUMN_GENUS,
            NomenDB.FeedEntry.COLUMN_DEKLINATION,
            NomenDB.FeedEntry.COLUMN_GELERNT,
            NomenDB.FeedEntry.COLUMN_LEKTIONID
    };

    //Version of the database
    private static final int DATABASE_VERSION = 1;


    //Name of the database file
    private static final String DATABASE_NAME= "TestDb.db";

    /**
     * Constructor
     * @param context lets newly-created objects understand what has been going on
     */
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating all 3 Databases
     * TODO: Maybe initialize them here too?
     * @param db database where the tables are supposed to be put into
     */
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES_LEKTION);
        db.execSQL(SQL_CREATE_ENTRIES_NOMEN);
        db.execSQL(SQL_CREATE_ENTRIES_VERB);
    }

    /**
     * Deleting all tables and
     * recreating them in 'onCreate(db)'
     * @param db Database that should be upgraded
     * @param oldVersion old versionNr of the database
     * @param newVersion new versionNr of the database
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES_LEKTION);
        db.execSQL(SQL_DELETE_ENTRIES_NOMEN);
        db.execSQL(SQL_DELETE_ENTRIES_VERB);
        onCreate(db);
    }

    //TODO: Combine all 3 methods into 1?
    /**
     * Adds a row with the given parameters into the 'Lektion' table
     * @param thema content for the 'Thema' column
     * @param beschreibung content for the 'Beschreibung' column
     */
    public void addRowLektion(String thema, String beschreibung){
        //retrieving the database that contains the wanted table
        SQLiteDatabase db = getWritableDatabase();

        //adds the row to the table
        ContentValues values = new ContentValues();
        values.put(LektionDB.FeedEntry.COLUMN_THEMA, thema);
        values.put(LektionDB.FeedEntry.COLUMN_BESCHREIBUNG, beschreibung);
        db.insert(LektionDB.FeedEntry.TABLE_NAME, null, values);

        //closes the database connection
        db.close();
    }

    /**
     * Adds a row with the given parameters into the 'Verb' table
     * @param latein content for the 'Latein' column
     * @param deutsch content for the 'Deutsch' column
     * @param hinweis content for the 'Hinweis' column
     * @param verbform content for the 'Verbform' column
     * @param konjugation content for the 'Konjugation' column
     * @param gelernt content for the 'Gelernt?' column
     * @param lektion foreign key referencing a lektion where the noun in contained
     */
    public void addRowVerb(String latein, String deutsch, String hinweis, String verbform, String konjugation, boolean gelernt, int lektion){
        //retrieving the database that contains the wanted table
        SQLiteDatabase db = getWritableDatabase();

        //adds the row to the table
        ContentValues values = new ContentValues();
        values.put(VerbDB.FeedEntry.COLUMN_LATEIN, latein);
        values.put(VerbDB.FeedEntry.COLUMN_DEUTSCH, deutsch);
        values.put(VerbDB.FeedEntry.COLUMN_HINWEIS, hinweis);
        values.put(VerbDB.FeedEntry.COLUMN_VERBFORM, verbform);
        values.put(VerbDB.FeedEntry.COLUMN_KONJUGATION, konjugation);
        values.put(VerbDB.FeedEntry.COLUMN_GELERNT, gelernt ? 1 : 0);
        values.put(VerbDB.FeedEntry.COLUMN_LEKTIONID, lektion);
        db.insert(VerbDB.FeedEntry.TABLE_NAME, null, values);

        //closes the database connection
        db.close();
    }

    /**
     * Adds a row with the given parameters into the 'Nomen' table
     * @param latein content for the 'Latein' column
     * @param deutsch content for the 'Deutsch' column
     * @param hinweis content for the 'Hinweis' column
     * @param genitiv content for the 'Genitiv' column
     * @param genus content for the 'Genus' column
     * @param deklination content for the 'Deklination' column
     * @param gelernt content for the 'Gelernt?' column
     * @param lektion foreign key referencing a lektion where the noun in contained
     */
    public void addRowNomen(String latein, String deutsch, String hinweis, String genitiv, String genus, String deklination, boolean gelernt, int lektion) {
        //retrieving the database that contains the wanted table
        SQLiteDatabase db = getWritableDatabase();

        //adds the row to the table
        ContentValues values = new ContentValues();
        values.put(NomenDB.FeedEntry.COLUMN_LATEIN, latein);
        values.put(NomenDB.FeedEntry.COLUMN_DEUTSCH, deutsch);
        values.put(NomenDB.FeedEntry.COLUMN_HINWEIS, hinweis);
        values.put(NomenDB.FeedEntry.COLUMN_GENITIV, genitiv);
        values.put(NomenDB.FeedEntry.COLUMN_GENUS, genus);
        values.put(NomenDB.FeedEntry.COLUMN_DEKLINATION, deklination);
        values.put(NomenDB.FeedEntry.COLUMN_GELERNT, gelernt ? 1 : 0);
        values.put(NomenDB.FeedEntry.COLUMN_LEKTIONID, lektion);
        db.insert(NomenDB.FeedEntry.TABLE_NAME, null, values);

        //closes the database connection
        db.close();
    }

    /**
     * Add a entry to the 'Verb' table for every row in the file
     * the columns are split with '@param split'
     * @param s path to the file to read
     * @param split element where the columns are split
     */
    public void addFileDataToVerb(String s, String split){
        //TODO: Add support for special characters -> ā ē ī
        try {
            //reading the image from the path 's'
            InputStream in = getClass().getResourceAsStream(s);
            //add buffer for mark/reset support
            InputStream bIn = new BufferedInputStream(in);
            //marks the beginning to resets to this point later
            bIn.mark(100000000);

            BufferedReader br = new BufferedReader( new InputStreamReader(bIn));

            //count the total number of lines
            int lineAmount = 0;
            while(br.readLine() != null){
                lineAmount++;
            }

            //reset to the beginning
            bIn.reset();
            br = new BufferedReader(new InputStreamReader((bIn)));

            //Skip the first line with the column headings
            br.readLine();

            SQLiteDatabase db = getReadableDatabase();

            //goes through every line and add its content to the table
            String line;
            for (int i = 0; i < lineAmount - 1; i++){
                line = br.readLine();
                if (line != null){
                    String[] tokens = line.split(split);
                    try {
                        addRowVerb(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Boolean.parseBoolean(tokens[5]), Integer.parseInt(tokens[6]));

                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }
            }
            //closing all connections
            db.close();
            br.close();
            bIn.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add a entry to the 'Verb' table for every row in the file
     * the columns are split with '@param split'
     * @param s path to the file to read
     * @param split element where the columns are split
     */
    public void addFileDataToNomen(String s, String split){
        //TODO: Add support for special characters -> ā ē ī
        try {
            //reading the image from the path 's'
            InputStream in = getClass().getResourceAsStream(s);
            //add buffer for mark/reset support
            InputStream bIn = new BufferedInputStream(in);
            //marks the beginning to resets to this point later
            bIn.mark(100000000);

            BufferedReader br = new BufferedReader( new InputStreamReader(bIn));

            //count the total number of lines
            int lineAmount = 0;
            while(br.readLine() != null){
                lineAmount++;
            }

            //reset reader to the beginning
            bIn.reset();
            br = new BufferedReader(new InputStreamReader((bIn)));

            //Skip the first line with the column headings
            br.readLine();

            SQLiteDatabase db = getReadableDatabase();

            //goes through every line and add its content to the table
            String line;
            for (int i = 0; i < lineAmount - 1; i++){
                line = br.readLine();
                if (line != null){
                    String[] tokens = line.split(split);
                    try {

                        addRowNomen(
                                tokens[0],
                                tokens[1],
                                tokens[2],
                                tokens[3],
                                tokens[4],
                                tokens[5],
                                Boolean.parseBoolean(tokens[6]),
                                Integer.parseInt(tokens[7]));
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }
            }
            //closing all connections
            db.close();
            br.close();
            bIn.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //TODO: Combine all 3 methods into 1
    /**
     * TODO: Combine all 3 'getAllItems[..]()' methods into 1
     * @return a Cursor of all elements of the table 'Lektion'
     */
    public Cursor getAllEntriesLektion() {
        //Get db connection
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                LektionDB.FeedEntry.TABLE_NAME,
                allColumnsLektion,
                null,
                null,
                null,
                null,
                LektionDB.FeedEntry._ID + " DESC");

        //return the amount of elements that were returned
        return cursor;
    }

    /**
     * TODO: Combine all 3 'getAllItems[..]()' methods into 1
     * @return a Cursor of all elements of the table 'Nomen'
     */
    public Cursor getAllEntriesNomen() {
        //Get db connection
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                NomenDB.FeedEntry.TABLE_NAME,
                allColumnsNomen,
                null,
                null,
                null,
                null,
                NomenDB.FeedEntry._ID + " DESC");

        db.close();
        //return the amount of elements that were returned
        return cursor;
    }

    /**
     * TODO: Combine all 3 'getAllItems[..]()' methods into 1
     * @return a Cursor of all elements of the table 'Verb'
     */
    public Cursor getAllEntriesVerb() {
        //Get db connection
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(
                VerbDB.FeedEntry.TABLE_NAME,
                allColumnsVerb,
                null,
                null,
                null,
                null,
                VerbDB.FeedEntry._ID + " DESC");

        db.close();
        //return the amount of elements that were returned
        return cursor;
    }

    /**
     * @param table name of the table where the amount of rows is wanted
     * @return amount of rows in the table given as parameter
     */
    public int getEntryAmount(String table){

        //Get db connection
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + table, null);

        cursor.moveToNext();
        long count = cursor.getLong(0);

        db.close();
        cursor.close();

        return (int)count;
    }


    /**
     * Returns a single column of a requested row with the id
     * @param table the table the result should be from
     * @param id the id of the entry with the wanted result
     * @param column the name of the wanted column
     * @return
     */
    public Object returnColumnFromID(String table, int id, String column){

        SQLiteDatabase db = getReadableDatabase();

        //getting the result of the query
        Cursor cursor = db.query(
                table,
                new String[] {column},
                " = ?",
                new String[] {""+id},
                null,
                null,
                null
        );

        //reading the result of the query
        cursor.moveToNext();
        Object result;
        try {
            result = cursor.getFloat(0);
        }catch (Exception e){
            result = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return result;
    }


    /**
     * This method is for the class 'AndroidDatabaseManager'
     * Remove this before release
     * @param Query
     * @return
     */
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
