package com.example.norablakaj.lateinapp.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.SQL_DUMP;
import com.example.norablakaj.lateinapp.Databases.Tables.AdverbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.PräpositionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SprichwortDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;




public class Vokabeltrainer_Lektion_1 extends AppCompatActivity {

    TextView latein;
    TextView deutsch;
    EditText schuelerInput;
    Button bestaetigung;
    Button weiter;
    Button resetButton;

    DBHelper dbHelper;
    Vokabel currentVokabel;

    ProgressBar progressVokabeln;

    String[] allTables = {SubstantivDB.FeedEntry.TABLE_NAME,
            VerbDB.FeedEntry.TABLE_NAME,
            AdverbDB.FeedEntry.TABLE_NAME,
            PräpositionDB.FeedEntry.TABLE_NAME,
            SprichwortDB.FeedEntry.TABLE_NAME};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer__lektion_1);

        dbHelper = new DBHelper(getApplicationContext());

        latein = findViewById(R.id.lateinVokabel);
        deutsch = findViewById(R.id.deutschVokabel);
        schuelerInput = findViewById(R.id.schuelerInput);
        bestaetigung = findViewById(R.id.eingabeBestaetigungLektion1);
        weiter = findViewById(R.id.nextVocabulary);
        resetButton = findViewById(R.id.resetButton);

        deutsch.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressVokabeln = findViewById(R.id.progressBar);
        progressVokabeln.animate();
        progressVokabeln.setMax(100);
        progressVokabeln.setProgress((int)(dbHelper.getGelerntProzent(1) * 100));

        if (dbHelper.getGelerntProzent(1) == 1) {
            allLearned();
        } else {

            currentVokabel = dbHelper.getRandomVocabulary(1);
            latein.setText(currentVokabel.getLatein());

            Log.d("currentVok", currentVokabel.getDeutsch());
        }
    }

    public void buttonClicked(View view){

        if(view.getId() == R.id.nextVocabulary){

            currentVokabel = dbHelper.getRandomVocabulary(1);
            latein.setText(currentVokabel.getLatein());
            schuelerInput.setText("");

            progressVokabeln.setProgress((int)(dbHelper.getGelerntProzent(1) * 100));

            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            deutsch.setVisibility(View.GONE);

            Log.d("New vokabel is", currentVokabel.getDeutsch());

        }else if (view.getId() == R.id.eingabeBestaetigungLektion1){

            View v = this.getCurrentFocus();
            if (v != null){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            if(compareTranslation(schuelerInput.getText().toString(), currentVokabel.getDeutsch())){

                dbHelper.setGelernt(getVokabelTable(currentVokabel), currentVokabel.getId(), true);
            }

            deutsch.setText(currentVokabel.getDeutsch());

            bestaetigung.setVisibility(View.GONE);
            weiter.setVisibility(View.VISIBLE);
            deutsch.setVisibility(View.VISIBLE);
        }
    }

    private boolean compareTranslation(String userInput, String wantedTranslation){
        //TODO: Check if the user had all cases correct if he inputs multiple

        if (userInput.equals("") || userInput.equals(" ") || userInput.equals("  ")){
            return false;
        }

        String[] tokensTranslation = wantedTranslation.split(",");

        for (String tS : tokensTranslation) {

            tS = tS.replaceFirst("^ *", "");

            char lastChar = userInput.charAt(userInput.length() - 1);
            if (lastChar == ' '){
                userInput = userInput.substring(0, userInput.length() - 1);
            }

            if (userInput.equalsIgnoreCase(tS)) {
                return true;
            }
        }

        return false;
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

    private void allLearned(){

        latein.setVisibility(View.GONE);
        deutsch.setVisibility(View.GONE);
        schuelerInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        resetButton.setVisibility(View.VISIBLE);
    }
}



