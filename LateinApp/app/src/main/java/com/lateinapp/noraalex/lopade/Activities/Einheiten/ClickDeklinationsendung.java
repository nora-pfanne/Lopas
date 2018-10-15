package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SubstantivDB;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import java.util.ArrayList;
import java.util.Random;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG;

public class ClickDeklinationsendung extends LateinAppActivity {

    private static final String TAG = "ClickDeklinationsendung";

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private TextView lateinVokabel;

    private Button weiter,
            zurück,
            reset,
            checkInput;

    private ProgressBar progressBar;

    private final String[] faelle = {
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

    private ToggleButton[] buttons;

    private int backgroundColor;

    private static final int maxProgress = 20;

    private ArrayList<String> allCorrectCases;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_click_deklination);

        setup();

        newVocabulary();
    }

    private void setup(){

        dbHelper = new DBHelper(getApplicationContext());
        sharedPref = General.getSharedPrefrences(this);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);

        lateinVokabel = findViewById(R.id.textGrammatikDeklinationLatein);
        progressBar = findViewById(R.id.progressBarGrammatikDeklination);

        ToggleButton nom_sg = findViewById(R.id.buttonGrammatikDeklinationNomSg);
        ToggleButton nom_pl = findViewById(R.id.buttonGrammatikDeklinationNomPl);
        ToggleButton gen_sg = findViewById(R.id.buttonGrammatikDeklinationGenSg);
        ToggleButton gen_pl = findViewById(R.id.buttonGrammatikDeklinationGenPl);
        ToggleButton dat_sg = findViewById(R.id.buttonGrammatikDeklinationDatSg);
        ToggleButton dat_pl = findViewById(R.id.buttonGrammatikDeklinationDatPl);
        ToggleButton akk_sg = findViewById(R.id.buttonGrammatikDeklinationAkkSg);
        ToggleButton akk_pl = findViewById(R.id.buttonGrammatikDeklinationAkkPl);
        ToggleButton abl_sg = findViewById(R.id.buttonGrammatikDeklinationAblSg);
        ToggleButton abl_pl = findViewById(R.id.buttonGrammatikDeklinationAblPl);

        reset = findViewById(R.id.buttonGrammatikDeklinationReset);
        checkInput = findViewById(R.id.buttonGrammatikDeklinationCheckInput);
        weiter = findViewById(R.id.buttonGrammatikDeklinationWeiter);
        zurück = findViewById(R.id.buttonGrammatikDeklinationZurück);

        buttons = new ToggleButton[]{
                nom_sg, nom_pl,
                gen_sg, gen_pl,
                dat_sg, dat_pl,
                akk_sg, akk_pl,
                abl_sg, abl_pl
        };

        progressBar.setMax(maxProgress);
        int progress = sharedPref.getInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, 0);
        if (progress > maxProgress) progress = maxProgress;
        progressBar.setProgress(progress);

        allCorrectCases = new ArrayList<>(10);
    }

    /**
     * Checks if the user already completed the 'grammatikDeklination'.
     * Retrieves a new vocabulary and sets it to be the current one.
     */
    private void newVocabulary(){

        int progress = sharedPref.getInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, 0);

        lateinVokabel.setBackgroundColor(backgroundColor);

        if (progress < maxProgress) {
            progressBar.setProgress(progress);

            String declination = faelle[new Random().nextInt(faelle.length)];

            //TODO: Failproof this -> extended tests?
            SubstantivDB currentVokabel = dbHelper.getRandomSubstantiv();

            allCorrectCases.clear();
            allCorrectCases.add(declination);

            //Adding all declinations that have the same form of the substantive:
            //Example: templum is  nom & akk Sg. -> both should be correct
            for (String fall : faelle){

                if (!declination.equals(fall)){

                    //Comparing if the declinated vocabulary in both cases are the same
                    if (dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), fall).equals(currentVokabel.getLatein())){

                        allCorrectCases.add(fall);
                    }

                }
            }

            String lateinText = dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), allCorrectCases.get(0));

            //#DEVELOPER
            if (DEVELOPER && DEV_CHEAT_MODE) {
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

        }else {
            progressBar.setProgress(maxProgress);

            for (ToggleButton b : buttons){
                b.setVisibility(View.GONE);
            }
            weiter.setVisibility(View.GONE);
            checkInput.setVisibility(View.GONE);
            lateinVokabel.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Handles button-clicks
     * @param view the clicked element
     */
    public void deklinationstrainerButtonClicked(View view){

        switch (view.getId()){

            //Checks the user input
            case (R.id.buttonGrammatikDeklinationCheckInput):

                weiter.setVisibility(View.VISIBLE);
                checkInput.setVisibility(View.GONE);
                checkInput();

                for (ToggleButton tb: buttons){
                    tb.setClickable(false);
                }

                break;

            //Gets the next vocabulary
            case (R.id.buttonGrammatikDeklinationWeiter):

                weiter.setVisibility(View.GONE);
                checkInput.setVisibility(View.VISIBLE);
                newVocabulary();

                for (ToggleButton tb: buttons){
                    tb.setChecked(false);
                    tb.setClickable(true);
                    tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_selector));
                }

                break;

            //Resets all progress up to this point
            case (R.id.buttonGrammatikDeklinationReset):

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, 0);
                editor.apply();

                finish();
                break;

            //Closes the activity and returns to the last one
            case (R.id.buttonGrammatikDeklinationZurück):

                finish();
                break;
        }
    }

    private void checkInput(){

        ArrayList<String> checkedButtons = new ArrayList<>(allCorrectCases.size());

        for(int i = 0; i < buttons.length; i++){
            if(buttons[i].isChecked()){
                checkedButtons.add(faelle[i]);
            }
        }


        boolean correct = true;

        for(int i = 0; i < faelle.length; i++){

            boolean isCorrect = allCorrectCases.contains(faelle[i]);
            boolean isChecked = checkedButtons.contains(faelle[i]);

            //XOR
            if(isChecked ^ isCorrect){
                buttons[i].setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_wrong_selector));
                correct = false;
            }else if(isChecked && isCorrect){
                buttons[i].setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_correct_selector));
            }
            //Dont change style if the buttons was not selected and that was correct

        }


        int color;

        int currentScore = sharedPref.getInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, 0);

        SharedPreferences.Editor editor = sharedPref.edit();
        if (correct) {
            color = ResourcesCompat.getColor(getResources(), R.color.correct, null);

            editor.putInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, currentScore + 1);
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.error, null);

            if (currentScore > 0) {
                editor.putInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, currentScore - 1);
            }
        }
        editor.apply();

        lateinVokabel.setBackgroundColor(color);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
