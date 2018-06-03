package com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Activities.oldVersion.Home;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

public class ClickPersonalendung extends LateinAppActivity {
    
    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private Button ersteSg,
            zweiteSg,
            dritteSg,
            erstePl,
            zweitePl,
            drittePl,
            weiter,
            zurück,
            reset;
    private ProgressBar progressBar;
    private TextView latein;

    private String[] faelle = {
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL};
    private Button[] buttons;
    private int[] weights;

    private String konjugation;
    private int backgroundColor;
    private int maxProgress = 20;

    private int colorButtonCorrect,
                colorButtonIncorrect,
                colorButtonDefault,
                colorButtonGrey;

    private String extraFromEinheitenUebersicht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_click_personalendung);

        setup();

        newVocabulary();

    }

    private void setup(){
        Intent intent = getIntent();
        extraFromEinheitenUebersicht = intent.getStringExtra("ExtraClickPersonalendung");

        dbHelper = new DBHelper(getApplicationContext());

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        colorButtonCorrect = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
        colorButtonIncorrect = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
        colorButtonDefault = ResourcesCompat.getColor(getResources(), R.color.PrussianBlue, null);
        colorButtonGrey = ResourcesCompat.getColor(getResources(), R.color.ButtonGrey, null);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        latein = findViewById(R.id.textGrammatikPersonalendungLatein);
        ersteSg = findViewById(R.id.buttonGrammatikPersonalendung1PersSg);
        zweiteSg = findViewById(R.id.buttonGrammatikPersonalendung2PersSg);
        dritteSg = findViewById(R.id.buttonGrammatikPersonalendung3PersSg);
        erstePl = findViewById(R.id.buttonGrammatikPersonalendung1PersPl);
        zweitePl = findViewById(R.id.buttonGrammatikPersonalendung2PersPl);
        drittePl = findViewById(R.id.buttonGrammatikPersonalendung3PersPl);
        progressBar = findViewById(R.id.progressBarPersonalendung);
        weiter = findViewById(R.id.buttonGrammatikPersonalendungWeiter);
        zurück = findViewById(R.id.buttonGrammatikPersonalendungZurück);
        reset = findViewById(R.id.buttonGrammatikPersonalendungReset);

        buttons = new Button[]{
                ersteSg,
                zweiteSg,
                dritteSg,
                erstePl,
                zweitePl,
                drittePl
        };

        weiter.setVisibility(View.GONE);

        progressBar.setMax(maxProgress);

        weightSubjects(extraFromEinheitenUebersicht);
        setButtonsState(extraFromEinheitenUebersicht);
    }

    /**
     * Sets weights for all entries of 'faelle' depending on the current value of lektion
     */
    private void weightSubjects(String extra){

        int weightErsteSg,
            weightZweiteSg,
            weightDritteSg,
            weightErstePl,
            weightZweitePl,
            weightDrittePl;

        switch (extra){

            case "DRITTE_PERSON":

                weightErsteSg = 0;
                weightZweiteSg = 0;
                weightDritteSg = 1;
                weightErstePl = 0;
                weightZweitePl = 0;
                weightDrittePl = 1;
                break;

            case "ERSTE_ZWEITE_PERSON":

                weightErsteSg = 2;
                weightZweiteSg = 2;
                weightDritteSg = 1;
                weightErstePl = 2;
                weightZweitePl = 2;
                weightDrittePl = 1;
                break;

            default:

                weightErsteSg = 1;
                weightZweiteSg = 1;
                weightDritteSg = 1;
                weightErstePl = 1;
                weightZweitePl = 1;
                weightDrittePl = 1;
                break;

        }

        weights = new int[] {weightErsteSg,
                weightZweiteSg,
                weightDritteSg,
                weightErstePl,
                weightZweitePl,
                weightDrittePl};
    }

    /**
     * Handles button-clicks
     * @param view the clicked element
     */
    public void personalendungButtonClicked(View view){

        switch (view.getId()){
            case (R.id.buttonGrammatikPersonalendung1PersSg):

                if(faelle[0].equals(konjugation)) konjugationChosen(true, buttons[0]);
                else konjugationChosen(false, buttons[0]);
                break;

            case (R.id.buttonGrammatikPersonalendung2PersSg):

                if(faelle[1].equals(konjugation)) konjugationChosen(true, buttons[1]);
                else konjugationChosen(false, buttons[1]);
                break;

            case (R.id.buttonGrammatikPersonalendung3PersSg):

                if(faelle[2].equals(konjugation)) konjugationChosen(true, buttons[2]);
                else konjugationChosen(false, buttons[2]);
                break;

            case (R.id.buttonGrammatikPersonalendung1PersPl):

                if(faelle[3].equals(konjugation)) konjugationChosen(true, buttons[3]);
                else konjugationChosen(false, buttons[3]);
                break;

            case (R.id.buttonGrammatikPersonalendung2PersPl):

                if(faelle[4].equals(konjugation)) konjugationChosen(true, buttons[4]);
                else konjugationChosen(false, buttons[4]);
                break;

            case (R.id.buttonGrammatikPersonalendung3PersPl):

                if(faelle[5].equals(konjugation)) konjugationChosen(true, buttons[5]);
                else konjugationChosen(false, buttons[5]);
                break;

            //Gets the next vocabulary
            case (R.id.buttonGrammatikPersonalendungWeiter):

                weiter.setVisibility(View.GONE);
                newVocabulary();
                break;

            //Resets all progress up to this point
            case (R.id.buttonGrammatikPersonalendungReset):

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("ClickPersonalendung"+extraFromEinheitenUebersicht, 0);
                editor.apply();
                finish();

            //Closes the activity and returns to the last one
            case (R.id.buttonGrammatikPersonalendungZurück):

                finish();
                break;
        }
    }

    /**
     * Checks if the user already completed the 'grammatikKonjugation'.
     * Retrieves a new vocabulary and sets it to be the current one.
     */
    private void newVocabulary(){
        
        int progress = sharedPref.getInt("ClickPersonalendung"+extraFromEinheitenUebersicht, 0);
        latein.setBackgroundColor(backgroundColor);

        //Checks if the user has had enough correct inputs to complete the 'grammatikKonjugation'
        if (progress < maxProgress) {
            
            konjugation = getRandomPersonalendung();
            //FIXME: Don't return a random number but one according to the progress (nom->1 /...)
            //random number from 1 to 5 to choose, where the vocabulary comes from
            //Blueprint for randNum: int randomNum = rand.nextInt((max - min) + 1) + min;
            int rand = new Random().nextInt((5 - 1) + 1) + 1;
            Vokabel currentVokabel = dbHelper.getRandomVerb(rand);
            String lateinText = dbHelper.getKonjugiertesVerb(currentVokabel.getId(), konjugation);

            //#DEVELOPER
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) lateinText += "\n" + konjugation;
          
            latein.setText(lateinText);

            setButtonsState(extraFromEinheitenUebersicht);
            
        }else {
            latein.setText("");
            ersteSg.setVisibility(View.GONE);
            zweiteSg.setVisibility(View.GONE);
            dritteSg.setVisibility(View.GONE);
            erstePl.setVisibility(View.GONE);
            zweitePl.setVisibility(View.GONE);
            drittePl.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);

        }

    }

    /**
     * @return a int corresponding to to position of a case in faelle[] with respect to the
     *          previously set weights[]-arr
     */
    public String getRandomPersonalendung(){

        //Getting a upper bound for the random number being retrieved afterwards
        int max =  (weights[0] +
                    weights[1] +
                    weights[2] +
                    weights[3] +
                    weights[4] +
                    weights[5]);

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
            Log.e("randomVocabulary", "Getting a randomKonjugation failed! Returned -1 for " +
                    "\nrandomNumber: " + randomNumber +
                    "\nlektion: " + extraFromEinheitenUebersicht);
        }


        return faelle[randomVocabulary];
    }

    /**
     * Sets the button-visibility corresponding to the current lektion
     * @param extra holds information about what the button state should be
     */
    private void setButtonsState(String extra){

        switch (extra){

            case "DRITTE_PERSON":
                ersteSg.setBackgroundColor(colorButtonGrey);
                ersteSg.setEnabled(false);

                zweiteSg.setBackgroundColor(colorButtonGrey);
                zweiteSg.setEnabled(false);

                dritteSg.setBackgroundColor(colorButtonDefault);
                dritteSg.setEnabled(true);

                erstePl.setBackgroundColor(colorButtonGrey);
                erstePl.setEnabled(false);

                zweitePl.setBackgroundColor(colorButtonGrey);
                zweitePl.setEnabled(false);

                drittePl.setBackgroundColor(colorButtonDefault);
                drittePl.setEnabled(true);
                break;

            case "ERSTE_ZWEITE_PERSON":
                ersteSg.setBackgroundColor(colorButtonDefault);
                ersteSg.setEnabled(true);

                zweiteSg.setBackgroundColor(colorButtonDefault);
                zweiteSg.setEnabled(true);

                dritteSg.setBackgroundColor(colorButtonDefault);
                dritteSg.setEnabled(true);

                erstePl.setBackgroundColor(colorButtonDefault);
                erstePl.setEnabled(true);

                zweitePl.setBackgroundColor(colorButtonDefault);
                zweitePl.setEnabled(true);

                drittePl.setBackgroundColor(colorButtonDefault);
                drittePl.setEnabled(true);
                break;

            default:
                ersteSg.setBackgroundColor(colorButtonDefault);
                ersteSg.setEnabled(true);

                zweiteSg.setBackgroundColor(colorButtonDefault);
                zweiteSg.setEnabled(true);

                dritteSg.setBackgroundColor(colorButtonDefault);
                dritteSg.setEnabled(true);

                erstePl.setBackgroundColor(colorButtonDefault);
                erstePl.setEnabled(true);

                zweitePl.setBackgroundColor(colorButtonDefault);
                zweitePl.setEnabled(true);

                drittePl.setBackgroundColor(colorButtonDefault);
                drittePl.setEnabled(true);
                break;
        }
    }

    /**
     * @param correct was the chosen konjugation correct
     */
    private void konjugationChosen(boolean correct, Button button){

        SharedPreferences.Editor editor = sharedPref.edit();
        int color;

        if (correct) {
            color = colorButtonCorrect;

            //Increasing the counter by 1
            editor.putInt("ClickPersonalendung" + extraFromEinheitenUebersicht,
                          sharedPref.getInt("ClickPersonalendung"+extraFromEinheitenUebersicht, 0) + 1);
        }else {
            color = colorButtonIncorrect;


            for (int i = 0; i < faelle.length; i++){
                if (faelle[i].equals(konjugation)){
                    buttons[i].setBackgroundColor(colorButtonCorrect);
                }
            }
        }
        editor.apply();

        progressBar.setProgress(sharedPref.getInt("ClickPersonalendung" +extraFromEinheitenUebersicht, 0));

        button.setBackgroundColor(color);
        latein.setBackgroundColor(color);

        weiter.setVisibility(View.VISIBLE);

        for (Button b : buttons){
            b.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeDb();
        dbHelper.close();
    }

}
