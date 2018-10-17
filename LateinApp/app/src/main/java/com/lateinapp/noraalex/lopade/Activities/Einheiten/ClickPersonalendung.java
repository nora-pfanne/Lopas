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
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import java.util.Arrays;
import java.util.Random;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_CLICK_PERSONALENDUNG;

public class ClickPersonalendung extends LateinAppActivity {

    private static final String TAG = "ClickPersonalendung";

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private Button weiter,
            zurück,
            reset;
    private ProgressBar progressBar;
    private TextView latein;

    private final String[] faelle = {
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL};
    private ToggleButton[] buttons;

    private String konjugation;
    private int backgroundColor;
    private static final int maxProgress = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_click_personalendung);

        setup();

        newVocabulary();

    }

    private void setup(){

        dbHelper = DBHelper.getInstance(getApplicationContext());

        sharedPref = General.getSharedPrefrences(this);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);

        latein = findViewById(R.id.textGrammatikPersonalendungLatein);
        progressBar = findViewById(R.id.progressBarPersonalendung);

        ToggleButton ersteSg = findViewById(R.id.buttonGrammatikPersonalendung1PersSg);
        ToggleButton zweiteSg = findViewById(R.id.buttonGrammatikPersonalendung2PersSg);
        ToggleButton dritteSg = findViewById(R.id.buttonGrammatikPersonalendung3PersSg);
        ToggleButton erstePl = findViewById(R.id.buttonGrammatikPersonalendung1PersPl);
        ToggleButton zweitePl = findViewById(R.id.buttonGrammatikPersonalendung2PersPl);
        ToggleButton drittePl = findViewById(R.id.buttonGrammatikPersonalendung3PersPl);

        reset = findViewById(R.id.buttonGrammatikPersonalendungReset);
        weiter = findViewById(R.id.buttonGrammatikPersonalendungWeiter);
        zurück = findViewById(R.id.buttonGrammatikPersonalendungZurück);

        buttons = new ToggleButton[]{
                ersteSg, zweiteSg,
                dritteSg, erstePl,
                zweitePl, drittePl
        };

        progressBar.setMax(maxProgress);
        int progress = sharedPref.getInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, 0);
        if (progress > maxProgress) progress = maxProgress;
        progressBar.setProgress(progress);
    }

    /**
     * Checks if the user already completed the 'grammatikKonjugation'.
     * Retrieves a new vocabulary and sets it to be the current one.
     */
    private void newVocabulary(){
        
        int progress = sharedPref.getInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, 0);

        latein.setBackgroundColor(backgroundColor);

        if (progress < maxProgress) {
            progressBar.setProgress(progress);

            konjugation = faelle[new Random().nextInt(faelle.length)];

            VerbDB currentVokabel = dbHelper.getRandomVerb();
            String lateinText = dbHelper.getKonjugiertesVerb(currentVokabel.getId(), konjugation);

            //#DEVELOPER
            if (DEVELOPER && DEV_CHEAT_MODE) lateinText += "\n" + konjugation;
          
            latein.setText(lateinText);
            
        }else {
            progressBar.setProgress(progress);

            for (ToggleButton b : buttons){
                b.setVisibility(View.GONE);
            }

            weiter.setVisibility(View.GONE);
            latein.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);

        }

    }

    /**
     * Handles button-clicks
     * @param view the clicked element
     */
    public void personalendungButtonClicked(View view){

        //This is easier than placing all elements of the array into the switch statement
        for(ToggleButton tb: buttons){
            if(tb.getId() == view.getId()){
                checkInput(tb);
                weiter.setVisibility(View.VISIBLE);

                for(ToggleButton toggleButton: buttons){
                    toggleButton.setClickable(false);
                }
                return;
            }
        }

        switch (view.getId()){

            //Gets the next vocabulary
            case (R.id.buttonGrammatikPersonalendungWeiter):

                weiter.setVisibility(View.GONE);
                newVocabulary();

                for (ToggleButton tb: buttons){
                    tb.setChecked(false);
                    tb.setClickable(true);
                    tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_selector));
                }
                break;

            //Resets all progress up to this point
            case (R.id.buttonGrammatikPersonalendungReset):

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, 0);
                editor.apply();

                finish();
                break;

            case (R.id.buttonGrammatikPersonalendungZurück):

                finish();
                break;
        }
    }

    private void checkInput(ToggleButton tb){

        int index = Arrays.asList(buttons).indexOf(tb);
        int wantedIndex = Arrays.asList(faelle).indexOf(konjugation);

        boolean correct = (index == wantedIndex);

        int color;

        int currentScore = sharedPref.getInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, 0);

        SharedPreferences.Editor editor = sharedPref.edit();
        if (correct) {
            tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_correct_selector));

            color = ResourcesCompat.getColor(getResources(), R.color.correct, null);

            editor.putInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, currentScore + 1);
        }else {
            buttons[wantedIndex].setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_correct_selector));
            tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_wrong_selector));

            color = ResourcesCompat.getColor(getResources(), R.color.error, null);

            if (currentScore > 0) {
                editor.putInt(KEY_PROGRESS_CLICK_PERSONALENDUNG, currentScore - 1);
            }
        }
        editor.apply();

        latein.setBackgroundColor(color);
    }

}
