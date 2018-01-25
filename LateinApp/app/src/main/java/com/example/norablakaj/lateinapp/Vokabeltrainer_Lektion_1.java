package com.example.norablakaj.lateinapp;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        constraintLayout = findViewById(R.id.constraint_Layout);
    }

    private void getRandomVocabulary(ConstraintLayout constraintLayout, Cursor cursor){

        //Words are chosen randomly and displayed on the TextView

        /*ID of the word that is going to be shown next
        * pattern: Min + (int)(Math.random() * ((Max - Min) + 1))
        * as by right now there are only 10 words in the database
        */
        int rn = (int)(Math.random() * 11);

       // while(cursor.moveToPosition(i)){
//
  //          double id = cursor.getDouble(i);
    //        String latein = cursor.getString(i);
//
  //      }
    }
}

