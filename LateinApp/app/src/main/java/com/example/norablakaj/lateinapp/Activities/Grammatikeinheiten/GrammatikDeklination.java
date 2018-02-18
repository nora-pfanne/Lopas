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
/*
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


    }
*/
}
