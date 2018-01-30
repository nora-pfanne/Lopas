package com.example.norablakaj.lateinapp.Activities;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.VerbDB;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    TextView latein;
    String lateinVokabelText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        constraintLayout = findViewById(R.id.constraint_Layout);

        latein = findViewById(R.id.lateinVokabel);

        //after a random vocabulary is chosen, it is showm in the TextView
    }

    private Cursor getRandomVocabulary(){

        String[] tables = {SubstantivDB.FeedEntry.TABLE_NAME, VerbDB.FeedEntry.TABLE_NAME};
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        int entryAmount = dbHelper.countTableEntries(tables, 1);
        Random rand = new Random();

        int randomNumber = rand.nextInt(entryAmount);

        int entryAmountVerb = dbHelper.countTableEntries(new String[]{VerbDB.FeedEntry.TABLE_NAME}, 1);
        int entryAmountSubstantiv = dbHelper.countTableEntries(new String[] {SubstantivDB.FeedEntry.TABLE_NAME}, 1);

        Cursor vokabel = null;

        if(randomNumber <= entryAmountSubstantiv){

            vokabel = dbHelper.getCursorFromId(randomNumber, tables[0]);
            lateinVokabelText = vokabel.getString(vokabel.getColumnIndex("infinitiv_deutsch"));

        } else if (randomNumber > entryAmountSubstantiv){

            vokabel = dbHelper.getCursorFromId((randomNumber-entryAmountSubstantiv), tables[1]);
            lateinVokabelText = vokabel.getString(vokabel.getColumnIndex("nom_sg_deutsch"));
        }

        return vokabel;
    }


}

