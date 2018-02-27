package com.example.norablakaj.lateinapp.Activities.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Activities.DevActivity;
import com.example.norablakaj.lateinapp.Activities.Home;
import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;

public class GrammatikDeklination extends DevActivity {

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private TextView lateinVokabel;
    private Button nom_sg, nom_pl,
            gen_sg, gen_pl,
            dat_sg, dat_pl,
            akk_sg, akk_pl,
            abl_sg, abl_pl,
            weiter,
            zurück,
            reset;
    private ProgressBar progressBar;

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

    private int lektion;
    private int backgroundColor;
    private int maxProgress = 20;

    private String declination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_deklination);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion", 0);

        dbHelper = new DBHelper(getApplicationContext());

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        lateinVokabel = findViewById(R.id.textGrammatikDeklinationLatein);
        nom_sg = findViewById(R.id.buttonGrammatikDeklinationNomSg);
        nom_pl = findViewById(R.id.buttonGrammatikDeklinationNomPl);
        gen_sg = findViewById(R.id.buttonGrammatikDeklinationGenSg);
        gen_pl = findViewById(R.id.buttonGrammatikDeklinationGenPl);
        dat_sg = findViewById(R.id.buttonGrammatikDeklinationDatSg);
        dat_pl = findViewById(R.id.buttonGrammatikDeklinationDatPl);
        akk_sg = findViewById(R.id.buttonGrammatikDeklinationAkkSg);
        akk_pl = findViewById(R.id.buttonGrammatikDeklinationAkkPl);
        abl_sg = findViewById(R.id.buttonGrammatikDeklinationAblSg);
        abl_pl = findViewById(R.id.buttonGrammatikDeklinationAblPl);
        weiter = findViewById(R.id.buttonGrammatikDeklinationWeiter);
        zurück = findViewById(R.id.buttonGrammatikDeklinationZurück);
        progressBar = findViewById(R.id.progressBarGrammatikDeklination);
        reset = findViewById(R.id.buttonGrammatikDeklinationReset);

        weiter.setVisibility(View.GONE);

        progressBar.setMax(maxProgress);

        weightSubjects(lektion);

        newVocabulary();
    }

    /**
     * Sets weights for all entries of 'faelle' depending on the current value of lektion
     */
    private void weightSubjects(int lektion){

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

        switch (lektion){

            case 1:
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

            case 2:
                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 0;
                weightDatPl = 0;
                weightAkkSg = 2;
                weightAkkPl = 2;
                weightAblSg = 0;
                weightAblPl = 0;
                break;

            case 3:

                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 4;
                weightDatPl = 4;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 0;
                weightAblPl = 0;
                break;

            case 4:
                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 0;
                weightGenPl = 0;
                weightDatSg = 1;
                weightDatPl = 1;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 6;
                weightAblPl = 6;
                break;

            case 5:

                weightNomSg = 1;
                weightNomPl = 1;
                weightGenSg = 8;
                weightGenPl = 8;
                weightDatSg = 1;
                weightDatPl = 1;
                weightAkkSg = 1;
                weightAkkPl = 1;
                weightAblSg = 1;
                weightAblPl = 1;
                break;

            // lektion > 5
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
     * Checks if the user already completed the 'grammatikDeklination'.
     * Retrieves a new vocabulary and sets it to be the current one.
     */
    private void newVocabulary(){

        int progress = sharedPref.getInt("Deklination"+lektion, 0);
        lateinVokabel.setBackgroundColor(backgroundColor);

        //Checks if the user has had enough correct inputs to complete the 'grammatikDeklination'
        if (progress < maxProgress) {

            declination = faelle[getRandomVocabularyNumber()];
            Vokabel currentVokabel = dbHelper.getRandomSubstantiv(lektion);
            String lateinText = (dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(),
                                 declination));

            //#DEVELOPER
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) lateinText += "\n" + declination;

            lateinVokabel.setText(lateinText);

            setButtonsVisible(lektion);
        }else {

            nom_sg.setVisibility(View.GONE);
            nom_pl.setVisibility(View.GONE);
            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            dat_sg.setVisibility(View.GONE);
            dat_pl.setVisibility(View.GONE);
            akk_sg.setVisibility(View.GONE);
            akk_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);
            lateinVokabel.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }

    }

    /**
     * @return a int corresponding to to position of a case in faelle[] with respect to the
     *          previously set weights[]-arr
     */
    private int getRandomVocabularyNumber(){

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
                    "\nlektion: " + lektion);
        }

        return randomVocabulary;
    }

    /**
     * Handles button-clicks
     * @param view the clicked element
     */
    public void deklinationstrainerButtonClicked(View view){

        switch (view.getId()){
            case (R.id.buttonGrammatikDeklinationNomSg):

                if(faelle[0].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationNomPl):

                if(faelle[1].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationGenSg):

                if(faelle[2].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationGenPl):

                if(faelle[3].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationDatSg):

                if(faelle[4].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationDatPl):

                if(faelle[5].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAkkSg):

                if(faelle[6].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAkkPl):

                if(faelle[7].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAblSg):

                if(faelle[8].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAblPl):

                if(faelle[9].equals(declination)) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            //Gets the next vocabulary
            case (R.id.buttonGrammatikDeklinationWeiter):

                weiter.setVisibility(View.GONE);
                newVocabulary();
                break;

            //Resets all progress up to this point
            case (R.id.buttonGrammatikDeklinationReset):

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Deklination"+lektion, 0);
                editor.apply();
                finish();

            //Closes the activity and returns to the last one
            case (R.id.buttonGrammatikDeklinationZurück):

                finish();
                break;
        }
    }

    /**
     * @param correct was the chosen declination correct
     */
    private void deklinationChosen(boolean correct){

        SharedPreferences.Editor editor = sharedPref.edit();
        int color;

        if (correct) {
            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);

            //Increasing the counter by 1
            editor.putInt("Deklination" + lektion,
                          sharedPref.getInt("Deklination"+lektion, 0) + 1);
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);

            //Decreasing the counter by 1
            if (sharedPref.getInt("Deklination" + lektion, 0) > 0) {
                editor.putInt("Deklination" + lektion,
                              sharedPref.getInt("Deklination" + lektion, 0) - 1);
            }
        }
        editor.apply();

        progressBar.setProgress(sharedPref.getInt("Deklination" +lektion, 0));
        lateinVokabel.setBackgroundColor(color);
    
        weiter.setVisibility(View.VISIBLE);
        nom_sg.setVisibility(View.GONE);
        nom_pl.setVisibility(View.GONE);
        gen_sg.setVisibility(View.GONE);
        gen_pl.setVisibility(View.GONE);
        dat_sg.setVisibility(View.GONE);
        dat_pl.setVisibility(View.GONE);
        akk_sg.setVisibility(View.GONE);
        akk_pl.setVisibility(View.GONE);
        abl_sg.setVisibility(View.GONE);
        abl_pl.setVisibility(View.GONE);
    }

    /**
     * Sets the button-visibility corresponding to the current lektion
     * @param lektion the current lektion
     */
    private void setButtonsVisible(int lektion){

        switch (lektion){

            case 1:
                nom_sg.setVisibility(View.VISIBLE);
                nom_pl.setVisibility(View.VISIBLE);
                gen_sg.setVisibility(View.GONE);
                gen_pl.setVisibility(View.GONE);
                dat_sg.setVisibility(View.GONE);
                dat_pl.setVisibility(View.GONE);
                akk_sg.setVisibility(View.GONE);
                akk_pl.setVisibility(View.GONE);
                abl_sg.setVisibility(View.GONE);
                abl_pl.setVisibility(View.GONE);
                break;

            case 2:

                nom_sg.setVisibility(View.VISIBLE);
                nom_pl.setVisibility(View.VISIBLE);
                gen_sg.setVisibility(View.GONE);
                gen_pl.setVisibility(View.GONE);
                dat_sg.setVisibility(View.GONE);
                dat_pl.setVisibility(View.GONE);
                akk_sg.setVisibility(View.VISIBLE);
                akk_pl.setVisibility(View.VISIBLE);
                abl_sg.setVisibility(View.GONE);
                abl_pl.setVisibility(View.GONE);
                break;

            case 3:

                nom_sg.setVisibility(View.VISIBLE);
                nom_pl.setVisibility(View.VISIBLE);
                gen_sg.setVisibility(View.GONE);
                gen_pl.setVisibility(View.GONE);
                dat_sg.setVisibility(View.VISIBLE);
                dat_pl.setVisibility(View.VISIBLE);
                akk_sg.setVisibility(View.VISIBLE);
                akk_pl.setVisibility(View.VISIBLE);
                abl_sg.setVisibility(View.GONE);
                abl_pl.setVisibility(View.GONE);
                break;

            case 4:

                nom_sg.setVisibility(View.VISIBLE);
                nom_pl.setVisibility(View.VISIBLE);
                gen_sg.setVisibility(View.GONE);
                gen_pl.setVisibility(View.GONE);
                dat_sg.setVisibility(View.VISIBLE);
                dat_pl.setVisibility(View.VISIBLE);
                akk_sg.setVisibility(View.VISIBLE);
                akk_pl.setVisibility(View.VISIBLE);
                abl_sg.setVisibility(View.VISIBLE);
                abl_pl.setVisibility(View.VISIBLE);
                break;

            default:

                nom_sg.setVisibility(View.VISIBLE);
                nom_pl.setVisibility(View.VISIBLE);
                gen_sg.setVisibility(View.VISIBLE);
                gen_pl.setVisibility(View.VISIBLE);
                dat_sg.setVisibility(View.VISIBLE);
                dat_pl.setVisibility(View.VISIBLE);
                akk_sg.setVisibility(View.VISIBLE);
                akk_pl.setVisibility(View.VISIBLE);
                abl_sg.setVisibility(View.VISIBLE);
                abl_pl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeDb();
        dbHelper.close();
    }
}