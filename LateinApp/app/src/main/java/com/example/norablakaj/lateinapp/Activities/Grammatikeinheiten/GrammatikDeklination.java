package com.example.norablakaj.lateinapp.Activities.Grammatikeinheiten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GrammatikDeklination extends AppCompatActivity {

    TextView grammatikUeberschrift;
    TextView grammatikAufgabenstellung;
    TextView latein;

    Button nom_sg, nom_pl,
            gen_sg, gen_pl,
            dat_sg, dat_pl,
            akk_sg, akk_pl,
            abl_sg, abl_pl;

    ProgressBar progressBar;

    String[] faelle = {"Nom_Sg", "Nom_Pl", "Gen_Sg", "Gen_Pl", "Dat_Sg", "Dat_Pl",
            "Akk_Sg", "Akk_Pl", "Abl_Sg", "Abl_Pl"};

    DBHelper dbHelper;

    int lektion;

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

        if (lektion == 1) {

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            dat_sg.setVisibility(View.GONE);
            dat_pl.setVisibility(View.GONE);
            akk_sg.setVisibility(View.GONE);
            akk_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

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

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            dat_sg.setVisibility(View.GONE);
            dat_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

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

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

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

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);

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

        ArrayList<Integer> testList = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            int declinationNr = getRandomVocabularyNumber(weightNomSg,
                    weightNomPl,
                    weightGenSg,
                    weightGenPl,
                    weightDatSg,
                    weightDatPl,
                    weightAkkSg,
                    weightAkkPl,
                    weightAblSg,
                    weightAblPl);
            testList.add(declinationNr);
        }

        Log.d("Length", ""+testList.size());
        Log.d("OccCounter",
                "NomSg: " + Collections.frequency(testList, 0) + "\t" +
                "NomPl: " + Collections.frequency(testList, 1) + "\n" +

                "GenSg: " + Collections.frequency(testList, 2) + "\t" +
                "GenPl: " + Collections.frequency(testList, 3) + "\n" +

                "DatSg: " + Collections.frequency(testList, 4) + "\t" +
                "DatPl: " + Collections.frequency(testList, 5) + "\n" +

                "AkkSg: " + Collections.frequency(testList, 6) + "\t" +
                "AkkPl: " + Collections.frequency(testList, 7) + "\n" +

                "AblSg: " + Collections.frequency(testList, 8) + "\t" +
                "AblPl: " + Collections.frequency(testList, 9) + "\n");
        //FIXME SOME BUGS HERE?

    }

    public int getRandomVocabularyNumber(int weightNomSg,
                                        int weightNomPl,
                                        int weightGenSg,
                                        int weightGenPl,
                                        int weightDatSg,
                                        int weightDatPl,
                                        int weightAkkSg,
                                        int weightAkkPl,
                                        int weightAblSg,
                                        int weightAblPl){

        int[] weights = {weightNomSg,
                        weightNomPl,
                        weightGenSg,
                        weightGenPl,
                        weightDatSg,
                        weightDatPl,
                        weightAkkSg,
                        weightAkkPl,
                        weightAblSg,
                        weightAblPl};
        
        int max =  (weightNomSg +
                    weightNomPl +
                    weightGenSg +
                    weightGenPl +
                    weightDatSg +
                    weightDatPl +
                    weightAkkSg +
                    weightAkkPl +
                    weightAblSg +
                    weightAblPl);

        Random randomNumber = new Random();
        int intRandom = randomNumber.nextInt(max);
        intRandom++;
        int sum = 1;

        //TODO: Can we make this initialisation better? Can we prevent the error without?
        int randomVocabulary = -1;
        //Log.d("0","intRandom: \t" + intRandom);
        for(int i = 0; i < weights.length; i++){

            //Log.d("Log", "sum:   \t" + sum +"\n"
            //                +"weight[i]: \t" + weights[i] + "\n");

            if (intRandom == sum){

                randomVocabulary = i;
            //    Log.d("Found!", "randomVocabulary is "+randomVocabulary);
                break;
            }
            else {
                sum += weights[i];
            }
        }


        return randomVocabulary;
    }

}
