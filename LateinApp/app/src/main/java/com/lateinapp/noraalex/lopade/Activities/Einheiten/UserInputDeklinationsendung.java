package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.EinheitenUebersicht;
import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

/**
 * Created by Alexander on 07.03.2018.
 */

//TODO: Input bestätigen mit Enter
//TODO: Make compatible with multiple tenses
public class UserInputDeklinationsendung extends LateinAppActivity {

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

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

    private Vokabel currentVokabel;
    private String currentDeclination;

    private int[] weights;
    private String[] faelle = {
            DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL,
            DeklinationsendungDB.FeedEntry.COLUMN_ABL_SG,
            DeklinationsendungDB.FeedEntry.COLUMN_ABL_PL
    };

    private String extraFromEinheitenUebersicht;
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
        extraFromEinheitenUebersicht = intent.getStringExtra("ExtraInputDeklinationsendung");

        sharedPref = getSharedPreferences("SharedPreferences", 0);
        dbHelper = new DBHelper(getApplicationContext());

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

        userInput.setHint("Deklinierter Substantiv");
        titel.setText("Deklinationstrainer");

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        weightSubjects(extraFromEinheitenUebersicht);

        progressBar.setMax(maxProgress);
    }

    private void newVocabulary(){

        int progress = sharedPref.getInt("UserInputDeklinationsendung"+extraFromEinheitenUebersicht, 0);

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

            //Getting a new vocabulary.
            //FIXME: Don't return a random number but one according to the progress (nom->1 /...)
            //random number from 1 to 5 to choose, where the vocabulary comes from
            //Blueprint for randNum: int randomNum = rand.nextInt((max - min) + 1) + min;
            int rand = new Random().nextInt((5 - 1) + 1) + 1;
            currentVokabel = dbHelper.getRandomSubstantiv(rand);
            currentDeclination = getRandomDeklination();

            //FIXME Remove nom_sg
            while (currentDeclination.equals(faelle[0])){
                currentDeclination = getRandomDeklination();
            }
            String lateinText = dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG);

            String personalendungUser = currentDeclination.replace("_", " ");
            personalendungUser = personalendungUser.replace("Sg", "Sg.");
            personalendungUser = personalendungUser.replace("Pl", "Pl.");
            personalendungUser = personalendungUser.replace("Nom", "Nom.");
            personalendungUser = personalendungUser.replace("Gen", "Gen.");
            personalendungUser = personalendungUser.replace("Dat", "Dat.");
            personalendungUser = personalendungUser.replace("Akk", "Akk.");
            personalendungUser = personalendungUser.replace("Abl", "Abl.");
            lateinText += "\n" + personalendungUser;

            //#DEVELOPER
            if (EinheitenUebersicht.DEVELOPER && EinheitenUebersicht.DEV_CHEAT_MODE){
                lateinText += "\n" + dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), currentDeclination);
            }
            request.setText(lateinText);

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

    /**
     * Sets weights for all entries of 'faelle' depending on the current value of lektion
     * Copied from GrammatikUserInputDeklinationsendung.class
     */
    private void weightSubjects(String extra){

        int weightNomSg;
        int weightNomPl;
        int weightGenSg;
        int weightGenPl;
        int weightDatSg;
        int weightDatPl;
        int weightAkkSg;
        int weightAkkPl;
        int weightAblSg;
        int weightAblPl;

        switch (extra){

            case "NOMINATIV":
                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 0;
                weightDatPl = 0;
                weightAkkSg = 0;
                weightAkkPl = 0;
                weightAblSg = 0;
                weightAblPl = 0;
                break;

            case "AKKUSATIV":
                weightNomSg = 2;
                weightNomPl = 2;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 0;
                weightDatPl = 0;
                weightAkkSg = 3;
                weightAkkPl = 3;
                weightAblSg = 0;
                weightAblPl = 0;
                break;

            case "DATIV":

                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 2;
                weightDatPl = 2;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 0;
                weightAblPl = 0;
                break;

            case "ABLATIV":
                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 1;
                weightDatPl = 1;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 3;
                weightAblPl = 3;
                break;

            case "GENITIV":

                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 4;
                weightGenPl = 4;
                weightDatSg = 1;
                weightDatPl = 1;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 1;
                weightAblPl = 1;
                break;

            // Alle gleichmäßig
            default:
                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 1;
                weightGenPl = 1;
                weightDatSg = 1;
                weightDatPl = 1;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 1;
                weightAblPl = 1;
        }

        weights = new int[]{
                weightNomSg,
                weightNomPl,
                weightGenSg,
                weightGenPl,
                weightDatSg,
                weightDatPl,
                weightAkkSg,
                weightAkkPl,
                weightAblSg,
                weightAblPl};
    }

    /**
     * Copied from GrammatikUserInputDeklinationsendung.class
     * @return
     */
    private String getRandomDeklination(){

        //Getting a upper bound for the random number being retrieved afterwards
        int max =  (weights[0]+
                    weights[1]+
                    weights[2]+
                    weights[3]+
                    weights[4]+
                    weights[5]+
                    weights[6]+
                    weights[7]+
                    weights[8]+
                    weights[9]);

        Random randomNumber = new Random();
        int intRandom = randomNumber.nextInt(max) + 1;
        int sum = 1;
        int sumNew;

        /*
        Each case gets a width corresponding to the 'weights'-arr.
        Goes through every case and checks if the 'randomInt' is in the area of the current case
         */
        int randomVocabulary = -1;
        for(int i = 0; i < weights.length; i++){

            sumNew = sum + weights[i];

            //checks if 'intRandom' is between the 'sum' and 'sumNew' and thus in the area of the current case
            if (intRandom >= sum && intRandom < sumNew){
                randomVocabulary = i;
                break;
            }
            else {
                sum = sumNew;
            }
        }

        if(randomVocabulary == -1){
            //Something went wrong. Log error-message
            Log.e("randomVocabulary", "Getting a randomDeclination failed! Returned -1 for " +
                    "\nrandomNumber: " + randomNumber +
                    "\nlektion: " + extraFromEinheitenUebersicht);
        }

        return faelle[randomVocabulary];
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
        if(compareString(userInput.getText().toString(), dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), currentDeclination))){
            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);

            SharedPreferences.Editor editor = sharedPref.edit();

            //Increasing the counter by 1
            editor.putInt("UserInputDeklinationsendung" + extraFromEinheitenUebersicht,
                    sharedPref.getInt("UserInputDeklinationsendung"+extraFromEinheitenUebersicht, 0) + 1);
            editor.apply();
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);

            SharedPreferences.Editor editor = sharedPref.edit();
            //Decreasing the counter by 1
            editor.putInt("UserInputDeklinationsendung" + extraFromEinheitenUebersicht,
                    sharedPref.getInt("UserInputDeklinationsendung"+extraFromEinheitenUebersicht, 0) - 1);
            editor.apply();
        }
        userInput.setBackgroundColor(color);

        //Showing the correct translation
        solution.setText(dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), currentDeclination));

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
                editor.putInt("UserInputDeklinationsendung"+extraFromEinheitenUebersicht, 0);
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
