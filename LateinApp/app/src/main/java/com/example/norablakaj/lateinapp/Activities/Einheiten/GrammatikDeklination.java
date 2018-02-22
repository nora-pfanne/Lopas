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

    TextView grammatikUeberschrift;
    TextView grammatikAufgabenstellung;
    TextView latein;

    Button nom_sg, nom_pl,
            gen_sg, gen_pl,
            dat_sg, dat_pl,
            akk_sg, akk_pl,
            abl_sg, abl_pl;

    Button weiter;
    Button zurück;

    ProgressBar progressBar;

    String[] faelle = {
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

    int[] weights;

    int progress;

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

    DBHelper dbHelper;

    int lektion;

    Vokabel currentVokabel;
    String declination;

    SharedPreferences sharedPref;

    int maxProgress = 20;

    //TODO: make all DBHelper into a private variable that calls .close() on onDestroy()/onFinish()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_deklination);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion", 0);

        dbHelper = new DBHelper(getApplicationContext());

        grammatikUeberschrift = findViewById(R.id.GrammatikDeklinationÜberschrift);
        grammatikAufgabenstellung = findViewById(R.id.GrammatikDeklinationAufgabe);
        latein = findViewById(R.id.GrammatikDeklinationLatein);

        nom_sg = findViewById(R.id.GrammatikDeklinationNomSg);
        nom_pl = findViewById(R.id.GrammatikDeklinationNomPl);
        gen_sg = findViewById(R.id.GrammatikDeklinationGenSg);
        gen_pl = findViewById(R.id.GrammatikDeklinationGenPl);
        dat_sg = findViewById(R.id.GrammatikDeklinationDatSg);
        dat_pl = findViewById(R.id.GrammatikDeklinationDatPl);
        akk_sg = findViewById(R.id.GrammatikDeklinationAkkSg);
        akk_pl = findViewById(R.id.GrammatikDeklinationAkkPl);
        abl_sg = findViewById(R.id.GrammatikDeklinationAblSg);
        abl_pl = findViewById(R.id.GrammatikDeklinationAblPl);
        weiter = findViewById(R.id.GrammatikDeklinationWeiter);

        weiter.setVisibility(View.GONE);
        zurück = findViewById(R.id.GrammatikDeklinationZurück);

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        progressBar = findViewById(R.id.GrammatikDeklinationProgressBar);
        progressBar.setMax(maxProgress);
        progress = sharedPref.getInt("Deklination"+lektion, 0);
        if (progress < maxProgress){
            progressBar.setProgress(progress);
        }else {
            progressBar.setProgress(maxProgress);
        }

        if (lektion == 1) {

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

        } else if (lektion == 2) {

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

        } else if (lektion == 3) {

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

        } else if (lektion == 4) {

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

        } else if (lektion >= 5) {

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

        } else {
            Log.e("LektionNotFound", "Lektion " + lektion + " in GrammatikDeklination.java not found");
            weightNomSg = 0;
            weightNomPl = 0;
            weightGenSg = 0;
            weightGenPl = 0;
            weightDatSg = 0;
            weightDatPl = 0;
            weightAkkSg = 0;
            weightAkkPl = 0;
            weightAblSg = 0;
            weightAblPl = 0;
        }

        weights = new int[]
                {weightNomSg,
                weightNomPl,
                weightGenSg,
                weightGenPl,
                weightDatSg,
                weightDatPl,
                weightAkkSg,
                weightAkkPl,
                weightAblSg,
                weightAblPl};
        /*
        DEBUGGING

        ArrayList<String> testList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            declination = faelle[getRandomVocabularyNumber()];
            testList.add(declination);
        }

        Log.d("TestResult",
                faelle[0] + ": \t" + Collections.frequency(testList, faelle[0]) + "\n" +
                faelle[1] + ": \t" + Collections.frequency(testList, faelle[1]) + "\n" +
                faelle[2] + ": \t" + Collections.frequency(testList, faelle[2]) + "\n" +
                faelle[3] + ": \t" + Collections.frequency(testList, faelle[3]) + "\n" +
                faelle[4] + ": \t" + Collections.frequency(testList, faelle[4]) + "\n" +
                faelle[5] + ": \t" + Collections.frequency(testList, faelle[5]) + "\n" +
                faelle[6] + ": \t" + Collections.frequency(testList, faelle[6]) + "\n" +
                faelle[7] + ": \t" + Collections.frequency(testList, faelle[7]) + "\n" +
                faelle[8] + ": \t" + Collections.frequency(testList, faelle[8]) + "\n" +
                faelle[9] + ": \t" + Collections.frequency(testList, faelle[9]) + "\n");
        */

        newVocabulary();
    }

    private void newVocabulary(){
        progress = sharedPref.getInt("Deklination"+lektion, 0);

        int color = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        latein.setBackgroundColor(color);

        if (progress < maxProgress) {
            declination = faelle[getRandomVocabularyNumber()];
            currentVokabel = dbHelper.getRandomSubstantiv(lektion);

            if (Home.isDEVELOPER() && Home.isDevCheatMode()) {
                latein.setText(dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), declination)
                        + "\n" + declination);
            } else {
                latein.setText(dbHelper.getDekliniertenSubstantiv(currentVokabel.getId(), declination));
            }

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
            latein.setVisibility(View.GONE);

            Button reset = findViewById(R.id.GrammatikDeklinationReset);
            reset.setVisibility(View.VISIBLE);
            zurück.setVisibility(View.VISIBLE);
        }

    }

    public int getRandomVocabularyNumber(){
        
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
            Log.e("randomVocabulary", "Getting a randomDeclination failed! Returned -1 for " +
                    "\nrandomNumber: " + randomNumber +
                    "\nlektion: " + lektion);
        }


        return randomVocabulary;
    }

    public void deklinationstrainerButtonClicked(View view){

        if (view.getId() == R.id.GrammatikDeklinationNomSg){
            if(faelle[0].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationNomPl){
            if(faelle[1].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationGenSg){
            if(faelle[2].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationGenPl){
            if(faelle[3].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationDatSg){
            if(faelle[4].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationDatPl){
            if(faelle[5].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationAkkSg){
            if(faelle[6].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationAkkPl){
            if(faelle[7].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationAblSg){
            if(faelle[8].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationAblPl){
            if(faelle[9].equals(declination)){
                deklinationChosen(true);
            }else{
                deklinationChosen(false);
            }
        }else if (view.getId() == R.id.GrammatikDeklinationWeiter){
            
            weiter.setVisibility(View.GONE);

            newVocabulary();


        }else if (view.getId() == R.id.GrammatikDeklinationReset){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Deklination"+lektion, 0);
            editor.apply();
            finish();
        }else if (view.getId() == R.id.GrammatikDeklinationZurück){
            finish();
        }

    }

    private void deklinationChosen(boolean correct){

        SharedPreferences.Editor editor = sharedPref.edit();

        int color;
        if (correct) {
            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
            editor.putInt("Deklination"+lektion, sharedPref.getInt("Deklination"+lektion, 0) + 1);
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);
            if (sharedPref.getInt("Deklination" + lektion, 0) > 0) {
                editor.putInt("Deklination" + lektion, sharedPref.getInt("Deklination" + lektion, 0) - 1);
            }
        }

        editor.apply();

        progressBar.setProgress(sharedPref.getInt("Deklination" +lektion, 0));

        latein.setBackgroundColor(color);
    
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

    private void setButtonsVisible(int lektion){
        if (lektion == 1){
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

        }else if (lektion == 2){
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

        }else if (lektion == 3){
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

        }else if (lektion == 4){
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
        }else if (lektion >= 5){
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
}
