package com.example.norablakaj.lateinapp.Activities.Einheiten;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Activities.DevActivity;
import com.example.norablakaj.lateinapp.Activities.Home;
import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.Tables.AdverbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.PräpositionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SprichwortDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;

public class Vokabeltrainer extends DevActivity {

    TextView latein;
    TextView deutsch;
    EditText userInput;
    
    //TODO: Remove button elevation to make it align with 'userInput'-EditText
    Button bestaetigung;
    Button weiter;
    Button resetButton;
    Button zurückButton;

    TextView überschrift;

    DBHelper dbHelper;
    Vokabel currentVokabel;

    ProgressBar progressBar;

    int lektion;

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
        zurückButton = findViewById(R.id.VokabeltrainerZurück);

        deutsch.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressBar.animate();
        progressBar.setMax(100);
        progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

        if (dbHelper.getGelerntProzent(lektion) == 1) {
            InputMethodManager imm = (InputMethodManager)getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);

            allLearned();

        } else {

            currentVokabel = dbHelper.getRandomVocabulary(lektion);


            Log.d("currentVok", "Latein: \t" + currentVokabel.getLatein() + "\n"
                    +"Deutsch: \t" + currentVokabel.getDeutsch()+"\n");

            latein.setText(currentVokabel.getLatein());
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()){
                latein.setText(currentVokabel.getLatein() + "\n" + currentVokabel.getDeutsch());
            }
        }
    }

    public void buttonClicked(View view){

        if(view.getId() == R.id.VokabeltrainerNächsteVokabel){

            progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

            if (dbHelper.getGelerntProzent(lektion) == 1) {
                allLearned();
            } else {

                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                currentVokabel = dbHelper.getRandomVocabulary(lektion);
                latein.setText(currentVokabel.getLatein());
                userInput.setText("");

                Log.d("currentVok", "Latein: \t" + currentVokabel.getLatein() + "\n"
                        +"Deutsch: \t" + currentVokabel.getDeutsch()+"\n");

                bestaetigung.setVisibility(View.VISIBLE);
                weiter.setVisibility(View.GONE);
                deutsch.setVisibility(View.GONE);

                int color = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
                userInput.setBackgroundColor(color);
                userInput.setFocusableInTouchMode(true);

                if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()){
                    latein.setText(currentVokabel.getLatein() + "\n" + currentVokabel.getDeutsch());
                }
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

                int color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
                userInput.setBackgroundColor(color);

            }else {
                int color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
                userInput.setBackgroundColor(color);
            }

            deutsch.setText(currentVokabel.getDeutsch());

            bestaetigung.setVisibility(View.GONE);
            weiter.setVisibility(View.VISIBLE);
            deutsch.setVisibility(View.VISIBLE);

        }else if (view.getId() == R.id.VokabeltrainerFortschrittLöschen){

            dbHelper.resetLektion(lektion);
            finish();
        }else if (view.getId() == R.id.VokabeltrainerZurück){
            finish();
        }
    }

    private boolean compareTranslation(String userInput, String wantedTranslation){

        if (userInput.equals("") || userInput.equals(" ") || userInput.equals("  ")){
            return false;
        }

        String[] userTokens = userInput.split(",");
        String[] tokensTranslation = wantedTranslation.split(",");

        boolean found;

        for (String user : userTokens){

            //Deleting the first char of the user if it is a space
            user = user.replaceFirst("^ *", "");

            //Deleting the last char of the user if it is a space
            char lastChar = user.charAt(user.length() - 1);
            if (lastChar == ' ') {
                user = user.substring(0, user.length() - 1);
            }

            found = false;

            for (String translation : tokensTranslation){

                //Deleting the first char of the translation if it is a space
                translation = translation.replaceFirst("^ *", "");

                //Deleting the last char of the translation if it is a space
                lastChar = translation.charAt(translation.length() - 1);
                if (lastChar == ' ') {
                    translation = translation.substring(0, translation.length() - 1);
                }

                //Checking with pronouns
                if (user.equalsIgnoreCase(translation)){
                    found = true;
                }
                //Checking without pronouns
                if (translation.contains("der") || translation.contains("die") || translation.contains("das") ||
                    translation.contains("Der") || translation.contains("Die") || translation.contains("Das")){
                    translation = translation.substring(4);

                    if (user.equalsIgnoreCase(translation)){
                        found = true;
                    }
                }

                if (translation.contains("sich") || translation.contains("Sich")){

                    translation = translation.substring(5);

                    if (user.equalsIgnoreCase(translation)){
                        found = true;
                    }
                }

            }

            if (!found){
                return false;
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

    private void allLearned(){

        latein.setVisibility(View.GONE);
        deutsch.setVisibility(View.GONE);
        userInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        resetButton.setVisibility(View.VISIBLE);
        zurückButton.setVisibility(View.VISIBLE);

        SharedPreferences sharedPref = getSharedPreferences("SharedPreferences", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Vokabeltrainer"+lektion, true);
        editor.commit();
    }
}



