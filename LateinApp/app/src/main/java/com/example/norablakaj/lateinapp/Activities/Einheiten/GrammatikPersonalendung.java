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
import com.example.norablakaj.lateinapp.Databases.Tables.Personalendung_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Vokabel;
import com.example.norablakaj.lateinapp.R;

import java.util.Random;

public class GrammatikPersonalendung extends DevActivity {

    /*
    WRONG FORMS

    3. PL venire -> vebi(u)nt

    is cognitare right? -> cogitare?
     */
    DBHelper dbHelper;

    String konjugation;
    
    Button ersteSg,
            zweiteSg,
            dritteSg,
            erstePl,
            zweitePl,
            drittePl;

    int weightErsteSg,
            weightZweiteSg,
            weightDritteSg,
            weightErstePl,
            weightZweitePl,
            weightDrittePl;

    int[] weights;

    String[] faelle = {Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL};
    
    Button weiter;
    Button zurück;

    TextView latein;

    Vokabel currentVokabel;

    ProgressBar progressBar;

    int lektion;
    int maxProgress = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_personalendung);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion", 0);

        latein = findViewById(R.id.GrammatikPersonalendungLatein);

        ersteSg = findViewById(R.id.GrammatikPersonalendung1PersSg);
        zweiteSg = findViewById(R.id.GrammatikPersonalendung2PersSg);
        dritteSg = findViewById(R.id.GrammatikPersonalendung3PersSg);
        erstePl = findViewById(R.id.GrammatikPersonalendung1PersPl);
        zweitePl = findViewById(R.id.GrammatikPersonalendung2PersPl);
        drittePl = findViewById(R.id.GrammatikPersonalendung3PersPl);

        weiter = findViewById(R.id.GrammatikPersonalendungWeiter);
        weiter.setVisibility(View.GONE);
        zurück = findViewById(R.id.GrammatikPersonalendungZurück);

        sharedPref = getSharedPreferences("SharedPreferences", 0);
        dbHelper = new DBHelper(getApplicationContext());

        progressBar = findViewById(R.id.progressBarPersonalendung);
        progressBar.setMax(maxProgress);
        int progress = sharedPref.getInt("Personalendung"+lektion, 0);
        if (progress < maxProgress){
            progressBar.setProgress(progress);
        }else {
            progressBar.setProgress(maxProgress);
        }

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
        }else if (lektion > 2){
            weightErsteSg = 1;
            weightZweiteSg = 1;
            weightDritteSg = 1;
            weightErstePl = 1;
            weightZweitePl = 1;
            weightDrittePl = 1;
        }else{
            Log.e("LektionNotFound", "Lektion '" + lektion + "' was not found in GrammatikPersonalendung.class");
            weightErsteSg = 0;
            weightZweiteSg = 0;
            weightDritteSg = 0;
            weightErstePl = 0;
            weightZweitePl = 0;
            weightDrittePl = 0;
        }

        weights = new int[] {weightErsteSg,
                weightZweiteSg,
                weightDritteSg,
                weightErstePl,
                weightZweitePl,
                weightDrittePl};


        newVocabulary();

    }

    public void personalendungButtonClicked(View view){

        if (view.getId() == R.id.GrammatikPersonalendung1PersSg){
            if(faelle[0].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendung2PersSg){
            if(faelle[1].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendung3PersSg){
            if(faelle[2].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendung1PersPl){
            if(faelle[3].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendung2PersPl){
            if(faelle[4].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendung3PersPl){
            if(faelle[5].equals(konjugation)){
                konjugationChosen(true);
            }else{
                konjugationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikPersonalendungWeiter){
            weiter.setVisibility(View.GONE);

            newVocabulary();

        }else if (view.getId() == R.id.GrammatikPersonalendungReset){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Personalendung"+lektion, 0);
            editor.apply();
            finish();
        }else if (view.getId() == R.id.GrammatikPersonalendungZurück){
            finish();
        }
    }

    private void newVocabulary(){
        int progress = sharedPref.getInt("Personalendung"+lektion, 0);

        int color = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        latein.setBackgroundColor(color);

        if (progress < maxProgress) {
            konjugation = faelle[getRandomVocabularyNumber()];

            currentVokabel = dbHelper.getRandomVerb(lektion);
            if (Home.isDEVELOPER() && Home.isDEV_CHEAT_MODE()) {
                latein.setText(dbHelper.getKonjugiertesVerb(currentVokabel.getId(), konjugation)
                        + "\n" + konjugation);
            } else {
                latein.setText(dbHelper.getKonjugiertesVerb(currentVokabel.getId(), konjugation));
            }
            setButtonsVisible(lektion);
        }else {
            latein.setText("");
            ersteSg.setVisibility(View.GONE);
            zweiteSg.setVisibility(View.GONE);
            dritteSg.setVisibility(View.GONE);
            erstePl.setVisibility(View.GONE);
            zweitePl.setVisibility(View.GONE);
            drittePl.setVisibility(View.GONE);

            Button reset = findViewById(R.id.GrammatikPersonalendungReset);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);

        }

    }

    public int getRandomVocabularyNumber(){

        int max =  (weights[0] +
                weights[1] +
                weights[2] +
                weights[3] +
                weights[4] +
                weights[5]);

        Random randomNumber = new Random();
        int intRandom = randomNumber.nextInt(max);
        intRandom++;
        int sum = 1;
        int sumNew;

        //TODO: Can we make this initialisation better? Can we prevent the error without?
        int randomVocabulary = -1;
        for(int i = 0; i < weights.length; i++){

            sumNew = sum + weights[i];

            if (intRandom >= sum && intRandom < sumNew){

                randomVocabulary = i;
                break;
            }
            else {
                sum = sumNew;
            }
        }

        if(randomVocabulary == -1){
            Log.e("randomVocabulary", "Getting a randomKonjugation failed! Returned -1 for " +
                    "\nrandomNumber: " + randomNumber +
                    "\nlektion: " + lektion);
        }


        return randomVocabulary;
    }

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
    
    private void konjugationChosen(boolean correct){

        SharedPreferences.Editor editor = sharedPref.edit();

        int color;
        if (correct) {
            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);

            editor.putInt("Personalendung"+lektion, sharedPref.getInt("Personalendung"+lektion, 0) + 1);
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
            if (sharedPref.getInt("Personalendung" + lektion, 0) > 0) {
                editor.putInt("Personalendung" + lektion, sharedPref.getInt("Personalendung" + lektion, 0) - 1);
            }
        }
        editor.apply();

        progressBar.setProgress(sharedPref.getInt("Personalendung" +lektion, 0));

        latein.setBackgroundColor(color);

        weiter.setVisibility(View.VISIBLE);
        ersteSg.setVisibility(View.GONE);
        zweiteSg.setVisibility(View.GONE);
        dritteSg.setVisibility(View.GONE);
        erstePl.setVisibility(View.GONE);
        zweitePl.setVisibility(View.GONE);
        drittePl.setVisibility(View.GONE);
    }
}
