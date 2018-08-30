package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.EinheitenUebersicht;
import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.R;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ClickKasusFragen extends LateinAppActivity {

    private SharedPreferences sharedPreferences;

    private Button  kasusFrage1,
                    kasusFrage2,
                    kasusFrage3,
                    kasusFrage4,
                    kasusFrage5,
                    weiter,
                    reset,
                    zurück;

    private final String[] kasusName =
            {"Nominativ", "Genitiv", "Dativ", "Akkusativ", "Ablativ"};
    private String currentKasus;

    private HashMap<String, String> kasusToFrage;
    private HashMap<Button, String> buttonToKasus;


    private Button[] buttons;

    private ProgressBar progressBar;
    private final int maxProgress = 10;

    private TextView kasusText;

    private int     colorButtonCorrect,
                    colorButtonIncorrect,
                    colorButtonDefault,
                    backgroundColor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_fragen);

        setup();

        newKasus();
    }

    private void setup(){

        sharedPreferences = getSharedPreferences("SharedPreferences", 0);

        colorButtonCorrect = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
        colorButtonIncorrect = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
        colorButtonDefault = ResourcesCompat.getColor(getResources(), R.color.PrussianBlue, null);

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        kasusText = findViewById(R.id.textGrammatikKasusLatein);
        kasusFrage1 = findViewById(R.id.buttonGrammatikKasusFrage1);
        kasusFrage2 = findViewById(R.id.buttonGrammatikKasusFrage2);
        kasusFrage3 = findViewById(R.id.buttonGrammatikKasusFrage3);
        kasusFrage4 = findViewById(R.id.buttonGrammatikKasusFrage4);
        kasusFrage5 = findViewById(R.id.buttonGrammatikKasusFrage5);
        weiter = findViewById(R.id.buttonGrammatikKasusFragenWeiter);
        reset = findViewById(R.id.buttonGrammatikKasusFragenReset);
        zurück = findViewById(R.id.buttonGrammatikKasusFragenZurück);
        progressBar = findViewById(R.id.progressBarKasusFragen);

        kasusToFrage = new HashMap<>();
        kasusToFrage.put("Nominativ",   "Wer oder was?");
        kasusToFrage.put("Genitiv",     "Wessen?");
        kasusToFrage.put("Dativ",       "Wem oder für wen?");
        kasusToFrage.put("Akkusativ",   "Wen oder was?");
        kasusToFrage.put("Ablativ",     "Womit oder wodurch?");

        buttonToKasus = new HashMap<>();

        buttons = new Button[]{
                kasusFrage1,
                kasusFrage2,
                kasusFrage3,
                kasusFrage4,
                kasusFrage5
        };

        weiter.setVisibility(View.GONE);


        progressBar.setMax(maxProgress);

        int progress = sharedPreferences.getInt("ClickKasusFragen", 0);
        if (progress > maxProgress) progress = maxProgress;
        progressBar.setProgress(progress);
    }

    public void kasusFragenButtonClicked(View view){

        switch(view.getId()){

            case (R.id.buttonGrammatikKasusFrage1):
                if(currentKasus.equals(
                        buttonToKasus.get(view)
                )){
                    kasusChosen(true, (Button)view);
                }
                else kasusChosen(false, (Button)view);
                break;

            case (R.id.buttonGrammatikKasusFrage2):
                if(currentKasus.equals(
                        buttonToKasus.get(view)
                )){
                    kasusChosen(true, (Button)view);
                }
                else kasusChosen(false, (Button)view);
                break;

            case (R.id.buttonGrammatikKasusFrage3):
                if(currentKasus.equals(
                        buttonToKasus.get(view)
                )){
                    kasusChosen(true, (Button)view);
                }
                else kasusChosen(false, (Button)view);
                break;

            case (R.id.buttonGrammatikKasusFrage4):
                if(currentKasus.equals(
                        buttonToKasus.get(view)
                )){
                    kasusChosen(true, (Button)view);
                }
                else kasusChosen(false, (Button)view);
                break;

            case (R.id.buttonGrammatikKasusFrage5):
                if(currentKasus.equals(
                        buttonToKasus.get(view)
                )){
                    kasusChosen(true, (Button)view);
                }
                else kasusChosen(false, (Button)view);
                break;

            case (R.id.buttonGrammatikKasusFragenWeiter):
                weiter.setVisibility(View.GONE);
                newKasus();
                break;

            case (R.id.buttonGrammatikKasusFragenReset):
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("ClickKasusFragen", 0);
                editor.apply();
                finish();

            case (R.id.buttonGrammatikKasusFragenZurück):
                finish();
                break;
        }
    }

    private void newKasus(){

        for (Button b : buttons){
            b.setEnabled(true);
            b.setBackgroundColor(colorButtonDefault);
        }

        int progress = sharedPreferences.getInt("ClickKasusFragen", 0);

        kasusText.setBackgroundColor(backgroundColor);

        if (progress < maxProgress) {


            shuffleArray(kasusName);
            assignButtons();

            currentKasus = kasusName[new Random().nextInt(5)];
            kasusText.setText(currentKasus);
            if (EinheitenUebersicht.DEVELOPER && EinheitenUebersicht.DEV_CHEAT_MODE){
                kasusText.setText(currentKasus + "\n" + kasusToFrage.get(currentKasus));
            }

            progressBar.setProgress(progress);
        }else {

            kasusText.setVisibility(View.GONE);
            kasusFrage1.setVisibility(View.GONE);
            kasusFrage2.setVisibility(View.GONE);
            kasusFrage3.setVisibility(View.GONE);
            kasusFrage4.setVisibility(View.GONE);
            kasusFrage5.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }
    }

    private void assignButtons(){

        buttonToKasus.clear();

        for(int i = 0; i < 5; i++){
            buttonToKasus.put(buttons[i], kasusName[i]);
            buttons[i].setText(kasusToFrage.get(kasusName[i]));
        }
    }

    // Implementing Fisher–Yates shuffle
    private static void shuffleArray(String[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void kasusChosen(boolean correct, Button button){

        int color;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(correct){

            color = colorButtonCorrect;

            editor.putInt("ClickKasusFragen",
                    sharedPreferences.getInt("ClickKasusFragen", 0) +1);

        }else{

            color = colorButtonIncorrect;
            if (sharedPreferences.getInt("ClickKasusFragen", 0) > 0) {
                editor.putInt("ClickKasusFragen",
                        sharedPreferences.getInt("ClickKasusFragen", 0) - 1);
            }
            for (Button b : buttons){

                if (buttonToKasus.get(b).equals(currentKasus)){
                    b.setBackgroundColor(colorButtonCorrect);
                    break;
                }
            }
        }
        editor.apply();
        Log.d("CurrentProgress", ""+sharedPreferences.getInt("ClickKasusFragen", 0));
        progressBar.setProgress(sharedPreferences.getInt("ClickKasusFragen", 0));

        button.setBackgroundColor(color);
        kasusText.setBackgroundColor(color);

        weiter.setVisibility(View.VISIBLE);

        for(Button b : buttons){
            b.setEnabled(false);
        }
    }
}