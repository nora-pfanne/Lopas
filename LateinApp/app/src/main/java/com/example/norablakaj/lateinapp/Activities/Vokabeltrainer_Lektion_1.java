package com.example.norablakaj.lateinapp.Activities;

import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Personalendung_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.Sprechvokal_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.VerbDB;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    TextView latein;

    String lateinVokabel = "Bitte Vokabel auswählen!";

    EditText schuelerInput;
    String schuelerInputString;
    Button bestaetigung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        latein = findViewById(R.id.lateinVokabel);

        //after a random vocabulary is chosen, it is showm in the TextView
        lateinVokabel = getRandomVocabulary(1);
        latein.setText(""+lateinVokabel);

        schuelerInput = findViewById(R.id.schuelerInput);
        schuelerInputString = schuelerInput.getText().toString();

        bestaetigung = findViewById(R.id.eingabeBestaetigungLektion1);
        bestaetigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //TODO: Move into DBHElper
    private String getRandomVocabulary(int lektionNr){

        lateinVokabel = "keine Vokabel ausgewählt!";

        String[] tables = {SubstantivDB.FeedEntry.TABLE_NAME, VerbDB.FeedEntry.TABLE_NAME};
        DBHelper dbHelper = new DBHelper(getApplicationContext());

        int prevLektionSubstantivCount = 0;
        int prevLektionVerbCount = 0;
        for (int i = 1; i < lektionNr; i++){
            prevLektionSubstantivCount += dbHelper.countTableEntries(new String[] {SubstantivDB.FeedEntry.TABLE_NAME}, i);
            prevLektionVerbCount += dbHelper.countTableEntries(new String[] {VerbDB.FeedEntry.TABLE_NAME}, i);
        }

        int entryAmountVerb = dbHelper.countTableEntries(new String[]{VerbDB.FeedEntry.TABLE_NAME}, lektionNr);
        int entryAmountSubstantiv = dbHelper.countTableEntries(new String[] {SubstantivDB.FeedEntry.TABLE_NAME}, lektionNr);
        int entryAmount = entryAmountSubstantiv + entryAmountVerb;

        Random rand = new Random();
        int randomNumber = rand.nextInt(entryAmount);

        if(randomNumber < entryAmountSubstantiv){

            String deklinationsendung = DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG;
            lateinVokabel = dbHelper.getDekliniertenSubstantiv(randomNumber + prevLektionSubstantivCount, deklinationsendung);

        } else if (randomNumber >= entryAmountSubstantiv){

            lateinVokabel = dbHelper.getKonjugiertesVerb(
                    randomNumber-entryAmountSubstantiv + prevLektionVerbCount,
                    "inf");

        }

        return lateinVokabel;
    }

    private void buttonClickedBestaetigung(){

    }

    private void pruefeSchuelerInput(){

    }


}

