package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.R;

public class ClickKasusFragen extends LateinAppActivity {

    private SharedPreferences sharedPreferences;

    private Button  kasusFrageNom,
                    kasusFrageGen,
                    kasusFrageDat,
                    kasusFrageAkk,
                    kasusFrageAbl,
                    weiter,
                    reset,
                    zurück;

    private String[] kasusName =
            {"Nominativ", "Genitiv", "Dativ", "Akkusativ", "Ablativ"};

    private Button[] buttons;

    private ProgressBar progressBar;
    private int maxProgress = 10;

    private TextView kasusText;
    private String kasus;

    private int     colorButtonCorrect,
                    colorButtonIncorrect,
                    colorButtonDefault;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasus_fragen);

        setup();

        newKasus();
    }

    private void setup(){

        Intent intent = getIntent();

        sharedPreferences = getSharedPreferences("SharedPreferences, 0")

        colorButtonCorrect = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
        colorButtonIncorrect = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
        colorButtonDefault = ResourcesCompat.getColor(getResources(), R.color.PrussianBlue, null);

        kasusFrageNom = findViewById(R.id.buttonGrammatikKasusFrageNom);
        kasusFrageGen = findViewById(R.id.buttonGrammatikKasusFrageGen);
        kasusFrageDat = findViewById(R.id.buttonGrammatikKasusFrageDat);
        kasusFrageAkk = findViewById(R.id.buttonGrammatikKasusFrageAkk);
        kasusFrageAbl = findViewById(R.id.buttonGrammatikKasusFrageAbl);
        weiter = findViewById(R.id.buttonGrammatikKasusFragenWeiter);
        reset = findViewById(R.id.buttonGrammatikKasusFragenReset);
        zurück = findViewById(R.id.buttonGrammatikKasusFragenZurück);

        buttons = new Button[]{
                kasusFrageNom,
                kasusFrageGen,
                kasusFrageDat,
                kasusFrageAkk,
                kasusFrageAbl
        };

        weiter.setVisibility(View.GONE);

        progressBar.setMax(maxProgress);
    }

    private void kasusFragenButtonClicked(View view){


    }

    private void newKasus(){


    }

    private int newKasusNummer(){

        int kasusNummer = (int)(Math.random() * ((5) + 1));

        return kasusNummer;
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

            for(int i = 0; i<kasusName.length; i++){
                if(kasusName[i].equals(kasus)){
                    buttons[i].setBackgroundColor(colorButtonCorrect);
                }
            }
        }
        editor.apply();

        progressBar.setProgress(sharedPreferences.getInt("ClickKasusFragen", 0));

        button.setBackgroundColor(color);
        kasusText.setBackgroundColor(color);

        weiter.setVisibility(View.VISIBLE);
    }
}


