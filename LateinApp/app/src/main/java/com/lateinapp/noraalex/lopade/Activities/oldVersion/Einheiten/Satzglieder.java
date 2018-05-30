package com.lateinapp.noraalex.lopade.Activities.oldVersion.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.Home;
import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.BeispielsatzDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Satzglieder extends LateinAppActivity {

    //TODO: We may want to weight the sentences
    //TODO: We may want to weight the selected elements
    //TODO: We will want to place the buttons such that it acually looks like a sentence -> not 1 button per row

    LinearLayout linearLayout;
    Button resetButton;
    ProgressBar progressBar;

    DBHelper dbHelper;
    SharedPreferences sharedPref;
    int maxProgress = 20;
    int lektion;

    int sentenceCount,
        sentenceID;


    private final String
            NUM_SG = "Singular",
            NUM_PL = "Plural";

    private final String
            G_SUBJEKT = "Subjekt",
            G_SUBJEKT_PRAEDIKAT = "Subjekt + Prädikat",
            G_PRAEDIKAT = "Prädikat",
            G_OBJ_GENITIV = "Genitivobjekt",
            G_OBJ_DATIV = "Dativobjekt",
            G_OBJ_AKKUSATIV = "Akkusativobjekt";

    private String[][]cases= {
            //Alle Fälle
            //{G_SUBJEKT_PRAEDIKAT},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_GENITIV},
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

    private ArrayList<Button> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_satzglieder);

        setup();
    }

    private void setup(){

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        dbHelper = new DBHelper(this);
        sharedPref = getSharedPreferences("SharedPreferences", 0);

        resetButton = findViewById(R.id.satzglieder_reset_button);
        linearLayout = findViewById(R.id.satzglieder_lin_layout);
        progressBar = findViewById(R.id.satzglieder_progress_bar);

        progressBar.setMax(maxProgress);

        sentenceCount = dbHelper.countTableEntries(new String[] {BeispielsatzDB.FeedEntry.TABLE_NAME});

        newSentence();

    }

    private void newSentence(){


        int progress = sharedPref.getInt("Satzglieder"+lektion, 0);

        Log.d("ProgressSatzglieder", progress+"");
        if (progress < maxProgress) {

            progressBar.setProgress(progress);

            //random sentence structure
            String[] currentSentence = cases[new Random().nextInt(cases.length)];

            //random sentence element the user has to select
            String elementToSelect = currentSentence[new Random().nextInt(currentSentence.length)];

            //Shuffle the selected array
            currentSentence = shuffleArray(currentSentence);

            //Setting instruction text
            setInstructionText(elementToSelect);

            //Getting a new sentence
            sentenceID = ThreadLocalRandom.current().nextInt(1, sentenceCount + 1);


            //Adding buttons to the layout
            addButtons(currentSentence, elementToSelect);
        }else {

            progressBar.setProgress(maxProgress);
            
            endGame();
        }

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

    private void removeButtons(){

        for (Button b : buttons){
            linearLayout.removeView(b);
        }
        buttons.clear();

    }

    private void addButtons(String[] currentSentence, String elementToSelect){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //Selecting SG/PL for the subject and predicate as they have to be the same.
        String numerusSubjektPraedikat = new Random().nextBoolean() ? NUM_SG : NUM_PL;
        String numerus;
        int vocID;

        for (String s : currentSentence) {
            Button button = new Button(this);


            //No try&catch with "NumberFormatException" if parseInt(String) fails because it shouldnt possibly be able to fail

            String text;
            switch (s){

                case G_SUBJEKT:

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                sentenceID,
                                BeispielsatzDB.FeedEntry.TABLE_NAME,
                                BeispielsatzDB.FeedEntry.COLUMN_SUBJEKT)
                    );

                    if (numerusSubjektPraedikat.equals(NUM_SG)){
                        //Singular
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG
                        );
                    }else {
                        //Plural
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL
                        );
                    }

                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(S)";
                    }

                    break;

                case G_SUBJEKT_PRAEDIKAT:

                    float ranNum = new Random().nextFloat();
                    String konjugation;

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                    sentenceID,
                                    BeispielsatzDB.FeedEntry.TABLE_NAME,
                                    BeispielsatzDB.FeedEntry.COLUMN_PRAEDIKAT)
                    );

                    //selecting a random konjugation
                    if (numerusSubjektPraedikat.equals(NUM_SG)){
                        //Singular

                        if (ranNum < 0.33) konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG;
                        else if (ranNum < 0.66) konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG;
                        else konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG;

                    }else {
                        //Plural

                        if (ranNum < 0.33) konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL;
                        else if (ranNum < 0.66) konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL;
                        else konjugation = Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL;
                    }

                    text = dbHelper.getKonjugiertesVerb(
                            vocID,
                            konjugation);

                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(SP)";
                    }

                    break;

                case G_PRAEDIKAT:

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                    sentenceID,
                                    BeispielsatzDB.FeedEntry.TABLE_NAME,
                                    BeispielsatzDB.FeedEntry.COLUMN_PRAEDIKAT)
                    );

                    if (numerusSubjektPraedikat.equals(NUM_SG)){
                        //Singular

                        text = dbHelper.getKonjugiertesVerb(
                                vocID,
                                Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG
                        );
                    }else {
                        //Plural

                        text = dbHelper.getKonjugiertesVerb(
                                vocID,
                                Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL
                        );

                    }
                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(P)";
                    }

                    break;


                case G_OBJ_GENITIV:
                    numerus = new Random().nextBoolean() ? NUM_SG : NUM_PL;

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                    sentenceID,
                                    BeispielsatzDB.FeedEntry.TABLE_NAME,
                                    BeispielsatzDB.FeedEntry.COLUMN_GENITIV)
                    );

                    if (numerus.equals(NUM_SG)){
                        //Singular
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG
                        );
                    }else {
                        //Plural
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL
                        );
                    }

                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(G)";
                    }

                    break;

                case G_OBJ_DATIV:
                    numerus = new Random().nextBoolean() ? NUM_SG : NUM_PL;

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                    sentenceID,
                                    BeispielsatzDB.FeedEntry.TABLE_NAME,
                                    BeispielsatzDB.FeedEntry.COLUMN_DATIV)
                    );

                    if (numerus.equals(NUM_SG)){
                        //Singular
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG
                        );
                    }else {
                        //Plural
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL
                        );
                    }

                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(D)";
                    }

                    break;

                case G_OBJ_AKKUSATIV:
                    numerus = new Random().nextBoolean() ? NUM_SG : NUM_PL;

                    //Getting the id of the vocabulary
                    vocID = Integer.parseInt(
                            dbHelper.getColumnFromId(
                                    sentenceID,
                                    BeispielsatzDB.FeedEntry.TABLE_NAME,
                                    BeispielsatzDB.FeedEntry.COLUMN_AKKUSATIV)
                    );

                    if (numerus.equals(NUM_SG)){
                        //Singular
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG
                        );
                    }else {
                        //Plural
                        text = dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL
                        );
                    }

                    //#DEVELOPER
                    if (Home.isDEV_CHEAT_MODE()){
                        text += "(A)";
                    }

                    break;

                default:
                    Log.e("CaseNotFound", "The requested case '" + s + "' in \"addButtons\" was not found");

                    text = "N/A";

            }

            button.setText(text);

            button.setLayoutParams(params);
            button.setGravity(Gravity.CENTER_HORIZONTAL);
            button.setTextSize(15);

            if (s.equals(elementToSelect)) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button)v;
                        answerSelected(true, button);
                    }
                });
                //Adding the correct button at pos 0 so that it can be easily found in other methods
                buttons.add(0, button);
            }else {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button)v;
                        answerSelected(false, button);
                    }
                });
                buttons.add(button);
            }

            buttons.add(button);
            linearLayout.addView(button);
        }
    }

    private void answerSelected(boolean correct, Button button){

        SharedPreferences.Editor editor = sharedPref.edit();
        int currentProgress = sharedPref.getInt("Satzglieder"+lektion, 0);

        //TODO: Set the 'correct' and 'incorrect' colors according to the app styles
        if (correct){
            button.setBackgroundColor(Color.GREEN);

            if (currentProgress <= maxProgress) {
                editor.putInt("Satzglieder" + lektion,
                        currentProgress + 1);
            }

        }else{
            button.setBackgroundColor(Color.RED);
            //Setting the color of the correct button to green
            buttons.get(0).setBackgroundColor(Color.GREEN);

            if (currentProgress > 0){
                editor.putInt("Satzglieder" + lektion,
                        currentProgress - 1);
            }
        }

        editor.apply();

        //Making all buttons unclickable.
        //Only new Buttons that are added on reset will be clickable again
        for (Button b: buttons){
            b.setClickable(false);
        }

    }

    public void satzgliederResetPressed(View v){

        removeButtons();
        newSentence();
    }


    private void endGame(){
        //TODO:
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
