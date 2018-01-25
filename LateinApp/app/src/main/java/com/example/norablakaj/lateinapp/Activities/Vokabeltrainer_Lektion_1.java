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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        constraintLayout = findViewById(R.id.constraint_Layout);

        latein = findViewById(R.id.lateinLektion1);
        latein.setText("Hallo");
    }

    private Cursor getRandomVocabulary(){

        String[] tables = {SubstantivDB.FeedEntry.TABLE_NAME, VerbDB.FeedEntry.TABLE_NAME};
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        int entryAmount = dbHelper.countTableEntries(tables, 1);
        Random rand = new Random();

        int randomNumber = rand.nextInt(entryAmount);

        int entryAmountVerb = dbHelper.countTableEntries(new String[]{VerbDB.FeedEntry.TABLE_NAME}, 1);
        int entryAmountSubstantiv = dbHelper.countTableEntries(new String[] {SubstantivDB.FeedEntry.TABLE_NAME}, 1);

        if(randomNumber <= entryAmountSubstantiv){


        }
    }


}

