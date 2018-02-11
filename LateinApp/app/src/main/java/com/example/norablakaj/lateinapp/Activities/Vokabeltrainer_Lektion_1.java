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
import com.example.norablakaj.lateinapp.Databases.Tables.AdverbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.PräpositionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SprichwortDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;

public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    TextView latein;
    TextView deutsch;

    Vokabel currentVokabel;

    EditText schuelerInput;
    DBHelper dbHelper;
    Button bestaetigung;
    Button weiter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        latein = findViewById(R.id.lateinVokabel);
        deutsch = findViewById(R.id.deutschVokabel);

        deutsch.setVisibility(View.GONE);

        //after a random vocabulary is chosen, it is showm in the TextView
        dbHelper = new DBHelper(getApplicationContext());
        currentVokabel = dbHelper.getRandomVocabulary(1);
        latein.setText(currentVokabel.getLatein());

        schuelerInput = findViewById(R.id.schuelerInput);

        bestaetigung = findViewById(R.id.eingabeBestaetigungLektion1);
        weiter = findViewById(R.id.nextVocabulary);

        weiter.setVisibility(View.GONE);
        Log.d("currentVok", currentVokabel.getDeutsch());
    }

    public void buttonClicked(View view){

        if(view.getId() == R.id.nextVocabulary){

            currentVokabel = dbHelper.getRandomVocabulary(1);
            latein.setText(currentVokabel.getLatein());
            schuelerInput.setText("");

            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            deutsch.setVisibility(View.GONE);

            Log.d("Gelernt in %", dbHelper.getGelerntProzent(1) + " wurden gelernt");

        }else if (view.getId() == R.id.eingabeBestaetigungLektion1){

            if(compareTranslation(schuelerInput.getText().toString(),
                    currentVokabel.getDeutsch())){
              dbHelper.setGelernt(getVokabelTable(currentVokabel), currentVokabel.getId(), true);
            }

            deutsch.setText(currentVokabel.getDeutsch());

            bestaetigung.setVisibility(View.GONE);
            weiter.setVisibility(View.VISIBLE);
            deutsch.setVisibility(View.VISIBLE);
        }
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

    private String getVokabelTable(Vokabel vokabel){

        if(vokabel instanceof SubstantivDB){

            return SubstantivDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof VerbDB){

            return VerbDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof SprichwortDB){

            return SprichwortDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof PräpositionDB){

            return PräpositionDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof AdverbDB){

            return AdverbDB.FeedEntry.TABLE_NAME;

        }else{

            Log.e("getVokabelTyp()","No VokabelTyp found");

            return "";
        }
    }
}

