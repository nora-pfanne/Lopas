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
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.R;

import java.util.ArrayList;
import java.util.Random;

//TODO: Make the user choose all correct cases -> not just one
public class GrammatikDeklination extends LateinAppActivity {

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
    private boolean[] buttonClicked;
    private Button[] buttons;

    private int colorButtonInactive;
    private int colorButtonActive;
    private int lektion;
    private int backgroundColor;
    private int maxProgress = 20;

    private ArrayList<String> allCorrectCases;

    Vokabel currentVokabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_deklination);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion", 0);

        dbHelper = new DBHelper(getApplicationContext());

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        colorButtonActive = ResourcesCompat.getColor(getResources(), R.color.PrussianBlueLight, null);
        colorButtonInactive = ResourcesCompat.getColor(getResources(), R.color.PrussianBlue, null);

        allCorrectCases = new ArrayList<>();

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        lateinVokabel = findViewById(R.id.textGrammatikDeklinationLatein);
        weiter = findViewById(R.id.buttonGrammatikDeklinationWeiter);
        zurück = findViewById(R.id.buttonGrammatikDeklinationZurück);
        progressBar = findViewById(R.id.progressBarGrammatikDeklination);
        reset = findViewById(R.id.buttonGrammatikDeklinationReset);
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

        buttons = new Button[]{
                nom_sg,
                nom_pl,
                gen_sg,
                gen_pl,
                dat_sg,
                dat_pl,
                akk_sg,
                akk_pl,
                abl_sg,
                abl_pl
        };

        buttonClicked = new boolean[]{
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        };

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

            String declination = faelle[getRandomVocabularyNumber()];
            currentVokabel = dbHelper.getRandomSubstantiv(lektion);
            allCorrectCases.clear();
            allCorrectCases.add(declination);
            //Adding all declinations that have the same form of the substantive:
            //Example: templum is  nom & akk Sg. -> both should be correct
            for (String fall : faelle){

                if (!declination.equals(fall)){

                    //Comparing if the declinated vocabulary in both cases are the same
                    if (
                            dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), declination).equals(
                                    dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), fall))
                            ){
                        allCorrectCases.add(fall);
                    }

                }
            }

            String lateinText = (dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(),
                    allCorrectCases.get(0)));

            //#DEVELOPER
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) {
                //Lowering the text size if more than 2 correct cases exist so it fits the screen.
                if (allCorrectCases.size() > 2) {
                    lateinVokabel.setTextSize(24);
                }else{
                    lateinVokabel.setTextSize(30);
                }

                //Setting the text containing the right declinations.
                lateinText += "\n";
                for (String correctCase : allCorrectCases) {
                    if (allCorrectCases.indexOf(correctCase) != 0) lateinText += " & ";
                    lateinText += correctCase;
                }
            }

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

                buttonClicked[0] = !buttonClicked[0];
                if(allCorrectCases.contains(faelle[0])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationNomPl):

                buttonClicked[1] = !buttonClicked[1];
                if(allCorrectCases.contains(faelle[1])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationGenSg):

                buttonClicked[2] = !buttonClicked[2];
                if(allCorrectCases.contains(faelle[2])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationGenPl):

                buttonClicked[3] = !buttonClicked[3];
                if(allCorrectCases.contains(faelle[3])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationDatSg):

                buttonClicked[4] = !buttonClicked[4];
                if(allCorrectCases.contains(faelle[4])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationDatPl):

                buttonClicked[5] = !buttonClicked[5];
                if(allCorrectCases.contains(faelle[5])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAkkSg):

                buttonClicked[6] = !buttonClicked[6];
                if(allCorrectCases.contains(faelle[6])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAkkPl):

                buttonClicked[7] = !buttonClicked[7];
                if(allCorrectCases.contains(faelle[7])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAblSg):

                buttonClicked[8] = !buttonClicked[8];
                if(allCorrectCases.contains(faelle[8])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            case (R.id.buttonGrammatikDeklinationAblPl):

                buttonClicked[9] = !buttonClicked[9];
                if(allCorrectCases.contains(faelle[9])) deklinationChosen(true);
                else deklinationChosen(false);
                break;

            //Gets the next vocabulary
            case (R.id.buttonGrammatikDeklinationWeiter):

                buttonClicked = new boolean[]{
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                };
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

        updateButtons();
    }

    /**
     * Updating the color of the buttons depending on if the button has been selected or deselected
     */
    private void updateButtons(){
        for (int i = 0; i < buttonClicked.length; i++){

            if (buttonClicked[i]){
                buttons[i].setBackgroundColor(colorButtonActive);
            }else {
                buttons[i].setBackgroundColor(colorButtonInactive);
            }
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
