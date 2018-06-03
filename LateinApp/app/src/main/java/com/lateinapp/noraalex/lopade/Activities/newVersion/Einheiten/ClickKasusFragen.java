package com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.R;

public class ClickKasusFragen extends LateinAppActivity {

    //TODO: Randomize button order

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
                    colorButtonDefault,
                    backgroundColor;

    protected void onCreate(Bundle savedInstanceState) {
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
        kasusFrageNom = findViewById(R.id.buttonGrammatikKasusFrageNom);
        kasusFrageGen = findViewById(R.id.buttonGrammatikKasusFrageGen);
        kasusFrageDat = findViewById(R.id.buttonGrammatikKasusFrageDat);
        kasusFrageAkk = findViewById(R.id.buttonGrammatikKasusFrageAkk);
        kasusFrageAbl = findViewById(R.id.buttonGrammatikKasusFrageAbl);
        weiter = findViewById(R.id.buttonGrammatikKasusFragenWeiter);
        reset = findViewById(R.id.buttonGrammatikKasusFragenReset);
        zurück = findViewById(R.id.buttonGrammatikKasusFragenZurück);
        progressBar = findViewById(R.id.progressBarKasusFragen);

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

    public void kasusFragenButtonClicked(View view){

        switch(view.getId()){

            case (R.id.buttonGrammatikKasusFrageNom):
                if(kasusName[0].equals(kasus)) kasusChosen(true, buttons[0]);
                else kasusChosen(false, buttons[0]);
                break;

            case (R.id.buttonGrammatikKasusFrageGen):
                if(kasusName[1].equals(kasus)) kasusChosen(true, buttons[1]);
                else kasusChosen(false, buttons[1]);
                break;

            case (R.id.buttonGrammatikKasusFrageDat):
                if(kasusName[2].equals(kasus)) kasusChosen(true, buttons[2]);
                else kasusChosen(false, buttons[2]);
                break;

            case (R.id.buttonGrammatikKasusFrageAkk):
                if(kasusName[3].equals(kasus)) kasusChosen(true, buttons[3]);
                else kasusChosen(false, buttons[3]);
                break;

            case (R.id.buttonGrammatikKasusFrageAbl):
                if(kasusName[4].equals(kasus)) kasusChosen(true, buttons[4]);
                else kasusChosen(false, buttons[4]);
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

            kasus = kasusName[newKasusNummer()];

            kasusText.setText(kasus);
            progressBar.setProgress(progress);
        }else {

            kasusText.setVisibility(View.GONE);
            kasusFrageNom.setVisibility(View.GONE);
            kasusFrageGen.setVisibility(View.GONE);
            kasusFrageDat.setVisibility(View.GONE);
            kasusFrageAkk.setVisibility(View.GONE);
            kasusFrageAbl.setVisibility(View.GONE);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }
    }

    private int newKasusNummer(){

        int kasusNummer = (int)(Math.random() * ((4) + 1));

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

            editor.putInt("ClickKasusFragen",
                    sharedPreferences.getInt("ClickKasusFragen", 0) -1);

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

        for(Button b : buttons){
            b.setEnabled(false);
        }
    }
}