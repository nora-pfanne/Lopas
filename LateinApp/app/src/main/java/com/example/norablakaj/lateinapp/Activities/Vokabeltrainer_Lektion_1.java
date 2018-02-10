package com.example.norablakaj.lateinapp.Activities;

import android.os.CpuUsageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;

public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    TextView latein;

    Vokabel currentVokabel;

    EditText schuelerInput;
    String schuelerInputString;
    Button bestaetigung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        latein = findViewById(R.id.lateinVokabel);

        //after a random vocabulary is chosen, it is showm in the TextView
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        currentVokabel = dbHelper.getRandomVocabulary(2);
        latein.setText(currentVokabel.getLatein());
        dbHelper.close();

        schuelerInput = findViewById(R.id.schuelerInput);
        schuelerInputString = schuelerInput.getText().toString();

        bestaetigung = findViewById(R.id.eingabeBestaetigungLektion1);
        bestaetigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void buttonClickedBestaetigung(){

    }

    private void pruefeSchuelerInput(){

    }

    private boolean compareTranslation(String userInput, String wantedTranslation){

        String[] tokensTranslation = wantedTranslation.split(",");
        String[] tokensUser = userInput.split(",");


        for (String uS : tokensUser) {

            boolean found = false;

            uS = uS.replaceFirst("^ *","");

            for (String tS : tokensTranslation) {

                tS = tS.replaceFirst("^ *", "");

                if (uS.equalsIgnoreCase(tS)) {
                    found = true;
                }

                if(!found){
                    return false;
                }
            }

        }

        return true;
    }
}

