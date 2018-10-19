package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.lateinapp.noraalex.lopade.Score;

import java.util.ArrayList;
import java.util.Random;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE;

public class ClickDeklinationsendung extends LateinAppActivity {

    private static final String TAG = "ClickDeklinationsendung";

    //Score stuff
    private TextView sCongratulations,
            sCurrentTrainer,
            sMistakeAmount,
            sMistakeAmountValue,
            sBestTry,
            sBestTryValue,
            sHighScore,
            sHighScoreValue,
            sGrade,
            sGradeValue;
    private Button sBack,
            sReset;

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private TextView lateinVokabel,
            amountWrong;

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

    Animation animShake;

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

        dbHelper = DBHelper.getInstance(getApplicationContext());
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

        //Score stuff
        sCongratulations = findViewById(R.id.scoreCongratulations);
        sCurrentTrainer = findViewById(R.id.scoreCurrentTrainer);
        sMistakeAmount = findViewById(R.id.scoreMistakes);
        sMistakeAmountValue = findViewById(R.id.scoreMistakeValue);
        sBestTry = findViewById(R.id.scoreBestRunMistakeAmount);
        sBestTryValue = findViewById(R.id.scoreEndScoreValue);
        sHighScore = findViewById(R.id.scoreHighScore);
        sHighScoreValue = findViewById(R.id.scoreHighScoreValue);
        sGrade = findViewById(R.id.scoreGrade);
        sGradeValue = findViewById(R.id.scoreGradeValue);
        sBack = findViewById(R.id.scoreButtonBack);
        sReset = findViewById(R.id.scoreButtonReset);

        reset = findViewById(R.id.buttonGrammatikDeklinationReset);
        checkInput = findViewById(R.id.buttonGrammatikDeklinationCheckInput);
        weiter = findViewById(R.id.buttonGrammatikDeklinationWeiter);
        zurück = findViewById(R.id.buttonGrammatikDeklinationZurück);

        amountWrong = findViewById(R.id.textUserInputMistakes4);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

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

        int wrong = Score.getCurrentMistakesDeklClick(sharedPref);
        if (wrong == -1){
            wrong = 0;
        }
        amountWrong.setText("Fehler: " + wrong);

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
            trainerFinished();
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

            case (R.id.scoreButtonReset):

                resetCurrentLektion();
                break;

            //Returning to the previous activity
            case (R.id.scoreButtonBack):
                finish();
                break;
        }
    }

    private void resetCurrentLektion(){


        new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle("Trainer zurücksetzen?")
                .setMessage("Willst du den Deklinationsendungs-Trainer wirklich neu starten?\nDeine beste Note wird beibehalten!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        General.showMessage("Deklinationsendungs-Trainer zurückgesetzt!", getApplicationContext());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG, 0);
                        editor.apply();

                        Score.resetCurrentMistakesDeklClick(sharedPref);
                        finish();

                    }})
                .setNegativeButton(android.R.string.no, null).show();



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

            Score.incrementCurrentMistakesDeklClick(sharedPref);

            int wrong = Score.getCurrentMistakesDeklClick(sharedPref);
            if (wrong == -1){
                wrong = 0;
            }
            amountWrong.setText("Fehler: " + wrong);
        }
        editor.apply();

        lateinVokabel.setBackgroundColor(color);

    }

    private void trainerFinished(){
        progressBar.setProgress(maxProgress);

        for (ToggleButton b : buttons){
            b.setVisibility(View.GONE);
        }
        weiter.setVisibility(View.GONE);
        checkInput.setVisibility(View.GONE);
        lateinVokabel.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
        zurück.setVisibility(View.GONE);
        ((TextView)findViewById(R.id.textGrammatikDeklinationAufgabe)).setVisibility(View.GONE);

        sCongratulations.setVisibility(View.VISIBLE);
        sCurrentTrainer.setVisibility(View.VISIBLE);
        sMistakeAmount.setVisibility(View.VISIBLE);
        sMistakeAmountValue.setVisibility(View.VISIBLE);
        sBestTry.setVisibility(View.VISIBLE);
        sBestTryValue.setVisibility(View.VISIBLE);
        sHighScore.setVisibility(View.GONE);
        sHighScoreValue.setVisibility(View.GONE);
        sGrade.setVisibility(View.VISIBLE);
        sGradeValue.setVisibility(View.VISIBLE);

        sBack.setVisibility(View.VISIBLE);
        sReset.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);

        amountWrong.setVisibility(View.GONE);

        int mistakeAmount = Score.getCurrentMistakesDeklClick(sharedPref);

        Score.updateLowestMistakesDeklClick(mistakeAmount, sharedPref);

        sCurrentTrainer.setText("Du hast gerade den Deklinationsendung-Trainer abgeschlossen!");

        String grade = Score.getGradeFromMistakeAmount(maxProgress + 2*mistakeAmount, mistakeAmount);

        String lowestEverText = Score.getLowestMistakesDeklClick(sharedPref) + "";
        SpannableStringBuilder gradeText = General.makeSectionOfTextBold(grade, ""+grade);

        if(mistakeAmount != -1){
            sMistakeAmountValue.setText(Integer.toString(mistakeAmount) + "");
        }else{
            sMistakeAmountValue.setText("N/A");
        }
        sBestTryValue.setText(lowestEverText);
        sGradeValue.setText(gradeText);

    }
}
