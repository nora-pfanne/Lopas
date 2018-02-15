package com.example.norablakaj.lateinapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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




public class Vokabeltrainer extends AppCompatActivity {

    TextView latein;
    TextView deutsch;
    EditText userInput;
    Button bestaetigung;
    Button weiter;
    Button resetButton;
    TextView überschrift;

    DBHelper dbHelper;
    Vokabel currentVokabel;

    ProgressBar progressBar;

    int lektion;

    Color color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        dbHelper = new DBHelper(getApplicationContext());

        überschrift = findViewById(R.id.VokabeltrainerÜberschrift);
        latein = findViewById(R.id.VokabeltrainerLatein);
        deutsch = findViewById(R.id.VokabeltrainerDeutsch);
        userInput = findViewById(R.id.VokabeltrainerUserInput);
        progressBar = findViewById(R.id.VokabeltrainerProgressBar);
        bestaetigung = findViewById(R.id.VokabeltrainerEingabeBestätigt);
        weiter = findViewById(R.id.VokabeltrainerNächsteVokabel);
        resetButton = findViewById(R.id.VokabeltrainerFortschrittLöschen);
        
        deutsch.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressBar.animate();
        progressBar.setMax(100);
        progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

        if (dbHelper.getGelerntProzent(lektion) == 1) {
            allLearned();
        } else {
            currentVokabel = dbHelper.getRandomVocabulary(lektion);
            latein.setText(currentVokabel.getLatein());

            Log.d("currentVok", currentVokabel.getDeutsch());
        }
    }

    public void buttonClicked(View view){

        if(view.getId() == R.id.VokabeltrainerNächsteVokabel){

            progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

            if (dbHelper.getGelerntProzent(lektion) == 1) {
                allLearned();
            } else {

                currentVokabel = dbHelper.getRandomVocabulary(lektion);
                latein.setText(currentVokabel.getLatein());
                userInput.setText("");

                Log.d("currentVok", currentVokabel.getDeutsch());

                bestaetigung.setVisibility(View.VISIBLE);
                weiter.setVisibility(View.GONE);
                deutsch.setVisibility(View.GONE);

                //TODO: get this color out of the /values/colors.xml file. currently: @colors/ghostWhite
                userInput.setBackgroundColor(Color.parseColor("#FAFAFF"));
                userInput.setFocusableInTouchMode(true);
            }


        }else if (view.getId() == R.id.VokabeltrainerEingabeBestätigt){

            View v = this.getCurrentFocus();
            if (v != null){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }

            userInput.setFocusable(false);

            if(compareTranslation(userInput.getText().toString(), currentVokabel.getDeutsch())){

                dbHelper.setGelernt(getVokabelTable(currentVokabel), currentVokabel.getId(), true);

                userInput.setBackgroundColor(Color.GREEN);

            }else {
                userInput.setBackgroundColor(Color.RED);
            }

            deutsch.setText(currentVokabel.getDeutsch());

            bestaetigung.setVisibility(View.GONE);
            weiter.setVisibility(View.VISIBLE);
            deutsch.setVisibility(View.VISIBLE);

        }else if (view.getId() == R.id.VokabeltrainerFortschrittLöschen){

            dbHelper.resetLektion(lektion);
            finish();
        }
    }

    private boolean compareTranslation(String userInput, String wantedTranslation){
        //TODO: Check if the user had all cases correct if he inputs multiple

        //TODO: Make the pronouns optional "Der Sklave" can be correct as "Sklave" aswell

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
        userInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        resetButton.setVisibility(View.VISIBLE);
    }
}



