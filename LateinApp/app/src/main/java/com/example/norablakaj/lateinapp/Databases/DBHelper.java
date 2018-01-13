package com.example.norablakaj.lateinapp.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nora Blakaj on 11.01.2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    //Lektion DB Stuff
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
    //provides quick access to a "delete all" command
    private static final String SQL_DELETE_ENTRIES_LEKTION =
            "DROP TABLES IF EXISTS "
            + LektionDB.FeedEntry.TABLE_NAME;
    //Lists all columns in a String[]
    private static final String[] allColumnsLektion = {
            LektionDB.FeedEntry._ID,
            LektionDB.FeedEntry.COLUMN_THEMA,
            LektionDB.FeedEntry.COLUMN_BESCHREIBUNG
    };

    //Verb DB Stuff
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
            + " INTEGER)";
    //provides quick access to a "delete all" command
    private static final String SQL_DELETE_ENTRIES_VERB =
            "DROP TABLES IF EXISTS "
            + VerbDB.FeedEntry.TABLE_NAME;
    //Lists all columns in a String[]
    private static final String[] allColumnsVerb = {
            VerbDB.FeedEntry._ID,
            VerbDB.FeedEntry.COLUMN_LATEIN,
            VerbDB.FeedEntry.COLUMN_DEUTSCH,
            VerbDB.FeedEntry.COLUMN_HINWEIS,
            VerbDB.FeedEntry.COLUMN_VERBFORM,
            VerbDB.FeedEntry.COLUMN_KONJUGATION,
            VerbDB.FeedEntry.COLUMN_GELERNT
    };

    //Nomen DB Stuff
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
            + " INTEGER)";
    //provides quick access to a "delete all" command
    private static final String SQL_DELETE_ENTRIES_NOMEN =
            "DROP TABLES IF EXISTS "
            + NomenDB.FeedEntry.TABLE_NAME;
    //Lists all columns in a String[]
    private static final String[] allColumnsNomen = {
            NomenDB.FeedEntry._ID,
            NomenDB.FeedEntry.COLUMN_LATEIN,
            NomenDB.FeedEntry.COLUMN_DEUTSCH,
            NomenDB.FeedEntry.COLUMN_HINWEIS,
            NomenDB.FeedEntry.COLUMN_GENITIV,
            NomenDB.FeedEntry.COLUMN_GENUS,
            NomenDB.FeedEntry.COLUMN_DEKLINATION,
            NomenDB.FeedEntry.COLUMN_GELERNT
    };

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME= "TestDb.db";

    //private static final String DATABASE_NAME = "contactsManager";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES_LEKTION);
        db.execSQL(SQL_CREATE_ENTRIES_NOMEN);
        db.execSQL(SQL_CREATE_ENTRIES_VERB);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES_LEKTION);
        db.execSQL(SQL_DELETE_ENTRIES_NOMEN);
        db.execSQL(SQL_DELETE_ENTRIES_VERB);
        onCreate(db);
    }

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
