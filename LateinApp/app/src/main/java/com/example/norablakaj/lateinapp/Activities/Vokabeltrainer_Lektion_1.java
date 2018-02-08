package com.example.norablakaj.lateinapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;

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
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        lateinVokabel = dbHelper.getRandomVocabulary(1, getApplicationContext());
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



    private void buttonClickedBestaetigung(){

    }

    private void pruefeSchuelerInput(){

    }


}

