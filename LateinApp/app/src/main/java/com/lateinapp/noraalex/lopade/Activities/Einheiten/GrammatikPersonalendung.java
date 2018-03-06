package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Activities.Home;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

public class GrammatikPersonalendung extends LateinAppActivity {
    
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
    private int lektion;
    private int maxProgress = 20;

    private int colorButtonCorrect,
                colorButtonIncorrect,
                colorButtonDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_personalendung);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion", 0);

        dbHelper = new DBHelper(getApplicationContext());
        
        sharedPref = getSharedPreferences("SharedPreferences", 0);

        colorButtonCorrect = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
        colorButtonIncorrect = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
        colorButtonDefault = ResourcesCompat.getColor(getResources(), R.color.PrussianBlue, null);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);;
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

        weightSubjects(lektion);
        setButtonsVisible(lektion);

        newVocabulary();

    }

    /**
     * Sets weights for all entries of 'faelle' depending on the current value of lektion
     */
    private void weightSubjects(int lektion){

        int weightErsteSg,
            weightZweiteSg,
            weightDritteSg,
            weightErstePl,
            weightZweitePl,
            weightDrittePl;
        
        if (lektion == 1){
            weightErsteSg = 0;
            weightZweiteSg = 0;
            weightDritteSg = 1;
            weightErstePl = 0;
            weightZweitePl = 0;
            weightDrittePl = 1;
        }else if (lektion == 2){
            weightErsteSg = 2;
            weightZweiteSg = 2;
            weightDritteSg = 1;
            weightErstePl = 2;
            weightZweitePl = 2;
            weightDrittePl = 1;
        }else {
            weightErsteSg = 1;
            weightZweiteSg = 1;
            weightDritteSg = 1;
            weightErstePl = 1;
            weightZweitePl = 1;
            weightDrittePl = 1;
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
                editor.putInt("Personalendung"+lektion, 0);
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
        
        int progress = sharedPref.getInt("Personalendung"+lektion, 0);
        latein.setBackgroundColor(backgroundColor);

        //Checks if the user has had enough correct inputs to complete the 'grammatikKonjugation'
        if (progress < maxProgress) {
            
            konjugation = faelle[getRandomVocabularyNumber()];
            Vokabel currentVokabel = dbHelper.getRandomVerb(lektion);
            String lateinText = dbHelper.getKonjugiertesVerb(currentVokabel.getId(), konjugation);

            //#DEVELOPER
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) lateinText += "\n" + konjugation;
          
            latein.setText(lateinText);

            for (Button b : buttons){
                b.setEnabled(true);
                b.setBackgroundColor(colorButtonDefault);
            }
            
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
    public int getRandomVocabularyNumber(){

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
                    "\nlektion: " + lektion);
        }


        return randomVocabulary;
    }

    /**
     * Sets the button-visibility corresponding to the current lektion
     * @param lektion the current lektion
     */
    private void setButtonsVisible(int lektion){

        if (lektion == 1){
            ersteSg.setVisibility(View.GONE);
            zweiteSg.setVisibility(View.GONE);
            dritteSg.setVisibility(View.VISIBLE);
            erstePl.setVisibility(View.GONE);
            zweitePl.setVisibility(View.GONE);
            drittePl.setVisibility(View.VISIBLE);

        }else{

            ersteSg.setVisibility(View.VISIBLE);
            zweiteSg.setVisibility(View.VISIBLE);
            dritteSg.setVisibility(View.VISIBLE);
            erstePl.setVisibility(View.VISIBLE);
            zweitePl.setVisibility(View.VISIBLE);
            drittePl.setVisibility(View.VISIBLE);
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
            editor.putInt("Personalendung" + lektion,
                          sharedPref.getInt("Personalendung"+lektion, 0) + 1);
        }else {
            color = colorButtonIncorrect;


            for (int i = 0; i < faelle.length; i++){
                if (faelle[i].equals(konjugation)){
                    buttons[i].setBackgroundColor(colorButtonCorrect);
                }
            }


            //Decreases the counter by 1
            if (sharedPref.getInt("Personalendung" + lektion, 0) > 0) {
                editor.putInt("Personalendung" + lektion,
                              sharedPref.getInt("Personalendung" + lektion, 0) - 1);
            }
        }
        editor.apply();

        progressBar.setProgress(sharedPref.getInt("Personalendung" +lektion, 0));

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
