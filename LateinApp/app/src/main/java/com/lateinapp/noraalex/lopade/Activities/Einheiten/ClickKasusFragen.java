package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import java.util.HashMap;
import java.util.Random;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_CLICK_KASUSFRAGEN;

public class ClickKasusFragen extends LateinAppActivity {

    private static final String TAG = "ClickKasusFragen";

    private SharedPreferences sharedPreferences;

    private Button weiter,
                    reset,
                    zurück;

    private final String[] kasusName =
            {"Nominativ", "Genitiv", "Dativ", "Akkusativ", "Ablativ"};
    private String currentKasus;

    private HashMap<String, String> kasusToFrage;
    private HashMap<ToggleButton, String> buttonToKasus;


    private ToggleButton[] buttons;

    private ProgressBar progressBar;
    private static final int maxProgress = 10;

    private TextView kasusText;

    private int backgroundColor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_fragen);

        setup();

        newKasus();
    }

    private void setup(){

        sharedPreferences = General.getSharedPrefrences(this);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);

        kasusText = findViewById(R.id.textGrammatikKasusLatein);
        progressBar = findViewById(R.id.progressBarKasusFragen);

        ToggleButton kasusFrage1 = findViewById(R.id.buttonGrammatikKasusFrage1);
        ToggleButton kasusFrage2 = findViewById(R.id.buttonGrammatikKasusFrage2);
        ToggleButton kasusFrage3 = findViewById(R.id.buttonGrammatikKasusFrage3);
        ToggleButton kasusFrage4 = findViewById(R.id.buttonGrammatikKasusFrage4);
        ToggleButton kasusFrage5 = findViewById(R.id.buttonGrammatikKasusFrage5);

        weiter = findViewById(R.id.buttonGrammatikKasusFragenWeiter);
        reset = findViewById(R.id.buttonGrammatikKasusFragenReset);
        zurück = findViewById(R.id.buttonGrammatikKasusFragenZurück);

        kasusToFrage = new HashMap<>(5);
        kasusToFrage.put(kasusName[0], "Wer oder was?");
        kasusToFrage.put(kasusName[1], "Wessen?");
        kasusToFrage.put(kasusName[2], "Wem oder für wen?");
        kasusToFrage.put(kasusName[3], "Wen oder was?");
        kasusToFrage.put(kasusName[4], "Womit oder wodurch?");

        buttonToKasus = new HashMap<>(5);

        buttons = new ToggleButton[]{
                kasusFrage1,
                kasusFrage2,
                kasusFrage3,
                kasusFrage4,
                kasusFrage5
        };

        progressBar.setMax(maxProgress);
        int progress = sharedPreferences.getInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, 0);
        if (progress > maxProgress) progress = maxProgress;
        progressBar.setProgress(progress);
    }

    private void newKasus(){

        int progress = sharedPreferences.getInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, 0);

        kasusText.setBackgroundColor(backgroundColor);

        if (progress < maxProgress) {
            progressBar.setProgress(progress);

            //randomizing the order that the solution buttons will be shown
            //Not always Nom-Gen-Dat-Akk-Abl
            General.shuffleArray(kasusName);

            //assigning each button a kasus and setting its text accordingly
            buttonToKasus.clear();
            for(int i = 0; i < kasusName.length; i++){
                buttonToKasus.put(buttons[i], kasusName[i]);
                buttons[i].setTextOn(kasusToFrage.get(kasusName[i]));
                buttons[i].setTextOff(kasusToFrage.get(kasusName[i]));
                buttons[i].setText(kasusToFrage.get(kasusName[i]));
            }

            currentKasus = kasusName[new Random().nextInt(5)];
            kasusText.setText(currentKasus);
            kasusText.setText(currentKasus);
            if (DEVELOPER && DEV_CHEAT_MODE){
                kasusText.setText(currentKasus + "\n" + kasusToFrage.get(currentKasus));
            }

        }else {
            progressBar.setProgress(progress);

            for(ToggleButton tb: buttons){
                tb.setVisibility(View.GONE);
            }

            weiter.setVisibility(View.GONE);
            kasusText.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }
    }

    public void kasusFragenButtonClicked(View view){

        //This is easier than placing all elements of the array into the switch statement
        for(ToggleButton tb: buttons){
            if(tb.getId() == view.getId()){

                kasusChosen(tb);

                weiter.setVisibility(View.VISIBLE);

                for(ToggleButton toggleButton: buttons){
                    toggleButton.setClickable(false);
                }
                return;
            }
        }

        switch(view.getId()){

            case (R.id.buttonGrammatikKasusFragenWeiter):
                weiter.setVisibility(View.GONE);
                newKasus();

                for (ToggleButton tb: buttons){
                    tb.setChecked(false);
                    tb.setClickable(true);
                    tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_selector));
                }
                break;

            case (R.id.buttonGrammatikKasusFragenReset):

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, 0);
                editor.apply();

                finish();

            case (R.id.buttonGrammatikKasusFragenZurück):

                finish();
                break;
        }
    }

    private void kasusChosen(ToggleButton tb){

        //TODO: Handle NullPointerExceptions

        boolean correct;

        String userInputText = tb.getTextOff().toString();
        correct = (userInputText.equals(kasusToFrage.get(currentKasus)));


        int color;

        int currentScore = sharedPref.getInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(correct){
            tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_correct_selector));

            color = ResourcesCompat.getColor(getResources(), R.color.correct, null);

            editor.putInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, currentScore +1);

        }else{

            for(ToggleButton toggleButton : buttonToKasus.keySet()){
                //getting the correct button
                if(toggleButton.getTextOn().toString().equals( kasusToFrage.get(currentKasus))){
                    toggleButton.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_correct_selector));
                }
            }

            tb.setBackground(ContextCompat.getDrawable(this, R.drawable.toggle_button_wrong_selector));

            color = ResourcesCompat.getColor(getResources(), R.color.error, null);

            if (currentScore > 0) {
                editor.putInt(KEY_PROGRESS_CLICK_KASUSFRAGEN, currentScore - 1);
            }

        }
        editor.apply();

        kasusText.setBackgroundColor(color);
    }

}