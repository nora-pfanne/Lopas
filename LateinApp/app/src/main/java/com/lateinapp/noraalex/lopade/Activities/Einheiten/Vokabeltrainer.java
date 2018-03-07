package com.lateinapp.noraalex.lopade.Activities.Einheiten;

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

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Activities.Home;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.AdverbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.PräpositionDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SprichwortDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SubstantivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

public class Vokabeltrainer extends LateinAppActivity {

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

    private TextView latein,
         deutsch,
         titel;
    private EditText userInput;
    private ProgressBar progressBar;
    //TODO: Remove button elevation to make it align with 'userInput'-EditText
    private Button bestaetigung,
        weiter,
        reset,
        zurück;

    private Vokabel currentVokabel;

    private int lektion;
    private int backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vokabeltrainer);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        sharedPref = getSharedPreferences("SharedPreferences", 0);
        dbHelper = new DBHelper(getApplicationContext());

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        latein = findViewById(R.id.textVokabeltrainerLatein);
        deutsch = findViewById(R.id.textVokabeltrainerDeutsch);
        userInput = findViewById(R.id.textVokabeltrainerUserInput);
        progressBar = findViewById(R.id.progressBarVokabeltrainer);
        bestaetigung = findViewById(R.id.buttonVokabeltrainerEingabeBestätigt);
        weiter = findViewById(R.id.buttonVokabeltrainerNächsteVokabel);
        reset = findViewById(R.id.buttonVokabeltrainerFortschrittLöschen);
        zurück = findViewById(R.id.buttonVokabeltrainerZurück);
        titel = findViewById(R.id.textVokabeltrainerÜberschrift);

        userInput.setHint("Übersetzung");
        titel.setText("Vokabeltrainer");

        deutsch.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);
        
        progressBar.setMax(100);
        progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

        //Checks if all vocabularies have been learned already
        if (dbHelper.getGelerntProzent(lektion) == 1) {
            //Hiding the keyboard.
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }

            allLearned();

        } else {
            //Getting a new vocabulary.
            currentVokabel = dbHelper.getRandomVocabulary(lektion);
            String lateinText = currentVokabel.getLatein();
            //#DEVELOPER
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) lateinText += "\n" + currentVokabel.getDeutsch();
            latein.setText(lateinText);
        }
    }

    /**
     * Handling the button-presses
     * @param view the view of the pressed button
     */
    public void vokabeltrainerButtonClicked(View view){

        switch (view.getId()){

            //Checking if all vocabularies have been learned already and getting a new one
            case (R.id.buttonVokabeltrainerNächsteVokabel):

                progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

                //Checks if all vocabularies have been learned already
                if (dbHelper.getGelerntProzent(lektion) == 1) {
                    allLearned();
                } else {

                    userInput.setText("");
                    userInput.setBackgroundColor(backgroundColor);
                    userInput.setFocusableInTouchMode(true);

                    //Showing the keyboard
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }catch (NullPointerException npe){
                        npe.printStackTrace();
                    }

                    //Getting a new vocabulary.
                    currentVokabel = dbHelper.getRandomVocabulary(lektion);
                    String lateinText = currentVokabel.getLatein();
                    //#DEVELOPER
                    if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) lateinText += "\n" + currentVokabel.getDeutsch();
                    latein.setText(lateinText);

                    bestaetigung.setVisibility(View.VISIBLE);
                    weiter.setVisibility(View.GONE);
                    deutsch.setVisibility(View.GONE);
                }
                break;

            //Checking if the user input was correct
            case (R.id.buttonVokabeltrainerEingabeBestätigt):

                userInput.setFocusable(false);

                //Hiding the keyboard
                //TODO: Why do we need to use the RootView instead of sth like: this.getCurrentFocus();
                try {
                    View v = getWindow().getDecorView().getRootView();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }


                //Checking the userInput against the translation
                int color;
                if(compareTranslation(userInput.getText().toString(), currentVokabel.getDeutsch())){

                    //Checking the vocabulary as learned
                    dbHelper.setGelernt(getVokabelTable(currentVokabel), currentVokabel.getId(), true);

                    color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);

                }else {
                    color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
                }
                userInput.setBackgroundColor(color);

                //Showing the correct translation
                deutsch.setText(currentVokabel.getDeutsch());

                bestaetigung.setVisibility(View.GONE);
                weiter.setVisibility(View.VISIBLE);
                deutsch.setVisibility(View.VISIBLE);
                break;

            //Setting the 'learned' state of all vocabularies of the current lektion to false
            case (R.id.buttonVokabeltrainerFortschrittLöschen):
                dbHelper.resetLektion(lektion);
                finish();
                break;

            //Returning to the previous activity
            case (R.id.buttonVokabeltrainerZurück):
                finish();
                break;
        }
    }

    /**
     * Compares the userInput with a wanted translation and returns if the comparison was successful.
     * @param userInput String to be compared with the translation
     * @param wantedTranslation the original translation to be compared with
     * @return Was the comparison successful
     */
    private boolean compareTranslation(String userInput, String wantedTranslation){

        String[] userTokens = userInput.split(",", -1);
        String[] translationTokens = wantedTranslation.split(",", -1);

        //Checking if every userToken[]-element matches with a translation
        for (String user : userTokens){

            // Returns false for empty tokens
            if (user.equals("")){
                return false;
            }

            //Deleting all whitespaces at the start of the token
            if (user.length() > 1) {
                while (user.charAt(0) == ' ') {
                    user = user.substring(1, user.length());
                    if (user.length() == 1) break;
                }
            }
            //Deleting all whitespaces at the end of the token
            if (user.length() > 1) {
                while (user.charAt(user.length() - 1) == ' ') {
                    user = user.substring(0, user.length() - 1);
                    if (user.length() == 1) break;
                }
            }

            boolean found = false;

            for (String translation : translationTokens){

                //Deleting all whitespaces at the start of the token
                while (translation.charAt(0) == ' '){
                    if (translation.length() == 1) break;
                    translation = translation.substring(1, translation.length());
                }

                //Deleting all whitespaces at the end of the token
                while (translation.charAt(translation.length()-1) == ' '){
                    if (translation.length() == 1) break;
                    translation = translation.substring(0, translation.length()-1);
                }
                //Checking with pronouns
                if (user.equalsIgnoreCase(translation)){
                    found = true;
                }
                //Checking without pronouns
                if (translation.contains("der") || translation.contains("die") || translation.contains("das") ||
                    translation.contains("Der") || translation.contains("Die") || translation.contains("Das")){
                    if (user.equalsIgnoreCase(translation.substring(4))){
                        found = true;
                    }
                }
                //Checking without 'Sich'/'sich'
                if (translation.contains("sich") || translation.contains("Sich")){
                    if (user.equalsIgnoreCase(translation.substring(5))){
                        found = true;
                    }
                }

            }

            if (!found){
                return false;
            }

        }

        //Everything was correct if we got here without returning false.
        return true;
    }

    /**
     * Determines what subclass of 'Vokabel' the given object is.
     * @param vokabel the 'Vokabel' where the type is to be determined
     * @return type of the 'Vokabel'-instance
     */
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

    /**
     * Executed when all vocabularies are learned.
     */
    private void allLearned(){

        latein.setVisibility(View.GONE);
        deutsch.setVisibility(View.GONE);
        userInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);
        reset.setVisibility(View.VISIBLE);
        zurück.setVisibility(View.VISIBLE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Vokabeltrainer"+lektion, true);
        editor.apply();
    }
}



