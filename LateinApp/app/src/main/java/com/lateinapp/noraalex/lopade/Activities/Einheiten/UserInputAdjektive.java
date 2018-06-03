package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.Home;
import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

public class UserInputAdjektive extends LateinAppActivity {

    private SharedPreferences sharedPref;

    private TextView request,
            solution,
            titel;
    private EditText userInput;
    private ProgressBar progressBar;
    //TODO: Remove button elevation to make it align with 'userInput'-EditText
    private Button bestaetigung,
            weiter,
            reset,
            zurück;

    private String deutscherText;

    private int backgroundColor;
    private int maxProgress = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_user_input);

        setup();

        newVocabulary();
    }

    private void setup(){

        Intent intent = getIntent();

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        request = findViewById(R.id.textUserInputLatein);
        solution = findViewById(R.id.textUserInputDeutsch);
        userInput = findViewById(R.id.textUserInputUserInput);
        progressBar = findViewById(R.id.progressBarUserInput);
        bestaetigung = findViewById(R.id.buttonUserInputEingabeBestätigt);
        weiter = findViewById(R.id.buttonUserInputNächsteVokabel);
        reset = findViewById(R.id.buttonUserInputFortschrittLöschen);
        zurück = findViewById(R.id.buttonUserInputZurück);
        titel = findViewById(R.id.textUserInputÜberschrift);

        userInput.setHint("Dekliniertes Adjektiv");
        titel.setText("Adjektive");

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);


        progressBar.setMax(maxProgress);
    }

    private void newVocabulary(){

        int progress = sharedPref.getInt("UserInputAdjektive", 0);

        if (progress < maxProgress) {

            progressBar.setProgress(progress);

            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }

            //Resetting the userInput.
            userInput.setText("");
            userInput.setBackgroundColor(backgroundColor);
            userInput.setFocusableInTouchMode(true);

            deutscherText = getRandomAdjektiv()[0];
            request.setText(deutscherText);

            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            solution.setVisibility(View.GONE);

        } else {

            progressBar.setProgress(maxProgress);

            //Hiding the keyboard.
            try {
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(userInput.getWindowToken(), 0);
            }catch (NullPointerException npe){
                npe.printStackTrace();
            }

            allLearned();
        }


    }


    private String[] getRandomAdjektiv(){

        int randomInt = (int)(Math.random() * ((5) + 1));

        String deutsch = "deutscher Fülltext";
        String latein = "leer";

        switch(randomInt) {

            case 0:
                deutsch = "großer Gott";
                latein = "magnus deus";

            case 1:
                deutsch = "kleines Mädchen";
                latein = "parva puella";

            case 2:
                deutsch = "arme Muse";
                latein = "misera musa";

            case 3:
                deutsch = "erstaunliches Land";
                latein = "mira terra";

            case 4:
                deutsch = "guter Weg";
                latein = "bona via";
        }

        String[] randomText = {deutsch, latein};

        return randomText;
    }

    private void checkInput(){
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
        if(compareString(userInput.getText().toString(), getRandomAdjektiv()[1])){
            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);

            SharedPreferences.Editor editor = sharedPref.edit();

            //Increasing the counter by 1
            editor.putInt("UserInputAdjektive",
                    sharedPref.getInt("UserInputAdjektive", 0) + 1);
            editor.apply();
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);

            SharedPreferences.Editor editor = sharedPref.edit();
            //Decreasing the counter by 1
            editor.putInt("UserInputAdjektive",
                    sharedPref.getInt("UserInputAdjektive", 0) - 1);
            editor.apply();
        }
        userInput.setBackgroundColor(color);

        //Showing the correct translation
        solution.setText(getRandomAdjektiv()[1]);

        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.VISIBLE);
        solution.setVisibility(View.VISIBLE);
    }

    /**
     * Compares the userInput with a wanted input and returns if the comparison was successful.
     * @param userInput String to be compared with the wanted input
     * @param wantedString the original string to be compared with
     * @return Was the comparison successful?
     */
    private boolean compareString(String userInput, String wantedString){

        // Returns false for empty input
        if (userInput.equals("")){
            return false;
        }

        //Deleting all whitespaces at the start of the input
        if (userInput.length() > 1) {
            while (userInput.charAt(0) == ' ') {
                userInput = userInput.substring(1, userInput.length());
                if (userInput.length() == 1) break;
            }
        }
        //Deleting all whitespaces at the end of the input
        if (userInput.length() > 1) {
            while (userInput.charAt(userInput.length() - 1) == ' ') {
                userInput = userInput.substring(0, userInput.length() - 1);
                if (userInput.length() == 1) break;
            }
        }

        if (userInput.equalsIgnoreCase(wantedString)) return true;
        else return false;

    }

    /**
     * Executed when all vocabularies are learned.
     */
    private void allLearned(){

        request.setVisibility(View.GONE);
        solution.setVisibility(View.GONE);
        userInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);
        reset.setVisibility(View.VISIBLE);
        zurück.setVisibility(View.VISIBLE);
    }

    /**
     * Handling the button-presses
     * @param view the view of the pressed button
     */
    public void userInputButtonClicked(View view){

        switch (view.getId()){

            //Checking if all vocabularies have been learned already and getting a new one
            case (R.id.buttonUserInputNächsteVokabel):

                newVocabulary();
                break;

            //Checking if the user input was correct
            case (R.id.buttonUserInputEingabeBestätigt):

                checkInput();
                break;

            //Setting the 'learned' state of all vocabularies of the current lektion to false
            case (R.id.buttonUserInputFortschrittLöschen):
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("UserInputAdjektive", 0);
                editor.apply();
                finish();
                break;

            //Returning to the previous activity
            case (R.id.buttonUserInputZurück):
                finish();
                break;
        }
    }
}
