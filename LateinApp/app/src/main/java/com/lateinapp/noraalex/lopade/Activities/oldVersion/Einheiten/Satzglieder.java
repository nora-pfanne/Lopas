package com.lateinapp.noraalex.lopade.Activities.oldVersion.Einheiten;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

public class Satzglieder extends LateinAppActivity {

    LinearLayout linearLayout;

    private final String
            G_SUBJEKT = "Subjekt",
            G_SUBJEKT_PRAEDIKAT = "Subjekt + Prädikat",
            G_PRAEDIKAT = "Prädikat",
            G_OBJ_NOMINATIV = "Nominativobjekt",
            G_OBJ_GENITIV = "Genitivobjekt",
            G_OBJ_DATIV = "Dativobjekt",
            G_OBJ_AKKUSATIV = "Akkusativobjekt";

    private String[][] sentence = {
            //Alle Fälle
            {G_SUBJEKT_PRAEDIKAT},
            {G_SUBJEKT_PRAEDIKAT, G_PRAEDIKAT, G_OBJ_AKKUSATIV},
            {G_SUBJEKT_PRAEDIKAT, G_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_GENITIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_DATIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_DATIV, G_OBJ_GENITIV},

            {G_SUBJEKT, G_PRAEDIKAT},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_GENITIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_DATIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_DATIV, G_OBJ_GENITIV}

            /*
            => Ablativ in Präpositionstrainer
             */
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_satzglieder);

        linearLayout = findViewById(R.id.satzglieder_lin_layout);

        setup();
    }

    private void setup(){

        //random sentence structure
        String[] currentSentence = sentence[new Random().nextInt(sentence.length)];

        //random sentence element the user has to select
        String elementToSelect = currentSentence[new Random().nextInt(currentSentence.length)];

        //Setting instruction text
        setInstructionText(elementToSelect);

        //Shuffle the selected array
        currentSentence = shuffleArray(currentSentence);

        //Adding buttons to the layout
        addButtons(currentSentence, elementToSelect);

    }

    private void setInstructionText(String selectedElement){

        TextView instructionText = findViewById(R.id.satzglieder_aufgabenstellung);

        String elementDescription;

        switch (selectedElement){

            case G_SUBJEKT_PRAEDIKAT:
                Random rand = new Random();
                elementDescription = rand.nextBoolean() ? "Subjekt" : "Prädikat";

                break;

            default:

                elementDescription = selectedElement;
                break;
        }


        instructionText.setText("Bestimme das " + elementDescription + "!");
    }

    private String[] shuffleArray(String[] array) {
        int index;
        String temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        return array;
    }

    private void addButtons(String[] currentSentence, String elementToSelect){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (String s : currentSentence) {
            Button button = new Button(this);
            button.setText(s);

            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setTextSize(15);

            if (s.equals(elementToSelect)) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button)v;
                        button.setBackgroundColor(Color.GREEN);
                    }
                });
            }else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button)v;
                        button.setBackgroundColor(Color.RED);
                    }
                });

            }


            linearLayout.addView(button);
        }
    }














    /*

    Immer:
        Prädikat

    Wenn Prädikat erste/zweite Sg/Pl:
        Kein Glied Subjekt
        -> Prädikat == Subjekt

    => Sonst:
        Es gibt ein Subjekt-Glied
        -> Nominativ
        -> Gleicher Nummerus wie Prädikat


    Anfügbar:
        Nominativobjekt
            -> Implementierung nicht nötig

        Akkusativobjekt
            -> Kann eingefügt werden (ohne Präposition)

        Ablativobjekt
            -> Braucht meist eine Präposition
            -> Nicht implementiert

        Genitivobjekt
            -> Bezugswort nötig
                =>
     */





    /*
    //Src: https://www.uzh.ch/latinum/amann/Grammatikunterlagen/Satzteile.pdf
    private abstract class Satzglied{
        private String textLatein;
        private String textDeutsch;

        public Satzglied(){}
    }

    private class Praedikat extends Satzglied{}
    private class Subjekt extends Satzglied{}
    private class Objekt extends Satzglied{}
    private class Attribut extends Satzglied{}
    private class Abverbium extends Satzglied{}
    */

}
