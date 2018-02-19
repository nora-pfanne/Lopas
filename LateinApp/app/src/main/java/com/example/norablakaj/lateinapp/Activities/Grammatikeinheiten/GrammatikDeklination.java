package com.example.norablakaj.lateinapp.Activities.Grammatikeinheiten;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.R;

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

        String[] faelle = {"Nom_Sg", "Nom_Pl", "Gen_Sg", "Gen_Pl", "Dat_Sg", "Dat_Pl",
                "Akk_Sg", "Akk_Pl", "Abl_Sg", "Abl_Pl"};

        if(lektion == 1){

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            dat_sg.setVisibility(View.GONE);
            dat_pl.setVisibility(View.GONE);
            akk_sg.setVisibility(View.GONE);
            akk_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

        } else if(lektion == 2){

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            dat_sg.setVisibility(View.GONE);
            dat_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

        } else if(lektion == 3){

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);
            abl_sg.setVisibility(View.GONE);
            abl_pl.setVisibility(View.GONE);

        } else if(lektion == 4){

            gen_sg.setVisibility(View.GONE);
            gen_pl.setVisibility(View.GONE);

        } else if(lektion >= 5){

        }
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

        if(lektion == 1){

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

        } else if(lektion == 2){

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

        } else if(lektion == 3){

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

        } else if(lektion == 4){

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

        }else if(lektion == 5){

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
        }

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

        int min = 0;
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
        int intRandom = randomNumber.nextInt(max+1);
        int sum = 1;

        int randomVocabulary;

        for(int i = 0; i < weights.length; i++){

            if (intRandom == sum){

                randomVocabulary = i;
            }
            else {

                sum += weights[i+1];
            }
        }

        return randomVocabulary;
    }
}
