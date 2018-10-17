package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.BeispielsatzDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_SATZGLIEDER;

public class Satzglieder extends LateinAppActivity {

    //TODO: We may want to weight the sentences
    //TODO: We may want to weight the selected elements

    private static final String TAG = "Satzglieder";

    private LinearLayout linearLayout;
    private Button weiterButton;
    private ProgressBar progressBar;
    private TextView aufgabenstellung;
    private Space space;

    private int backgroundColor;
    private int colorButtonCorrect;
    private int colorButtonIncorrect;

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;
    private final int maxProgress = 20;

    private int correctButtonLocation;

    private int sentenceCount;
    private int sentenceID;


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

    private final String[][]cases= {
            //{G_SUBJEKT_PRAEDIKAT},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_GENITIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_DATIV},
            {G_SUBJEKT_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_DATIV, G_OBJ_GENITIV},

            {G_SUBJEKT, G_PRAEDIKAT},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_GENITIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_DATIV},
            {G_SUBJEKT, G_PRAEDIKAT, G_OBJ_AKKUSATIV, G_OBJ_DATIV, G_OBJ_GENITIV}
    };

    private final ArrayList<Button> buttons = new ArrayList<>();
    //Button with its according width
    private HashMap<Button, Integer> buttonHashMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_satzglieder);

        setup();
    }

    /**
     * Initializing variables and starting a new sentence
     */
    private void setup(){

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);
        colorButtonIncorrect = ResourcesCompat.getColor(getResources(), R.color.error, null);
        colorButtonCorrect = ResourcesCompat.getColor(getResources(), R.color.correct, null);

        dbHelper = DBHelper.getInstance(this);
        buttonHashMap = new HashMap<>();
        sharedPref = General.getSharedPrefrences(getApplicationContext());

        weiterButton = findViewById(R.id.satzglieder_next_button);
        linearLayout = findViewById(R.id.satzglieder_lin_layout);
        progressBar = findViewById(R.id.satzglieder_progress_bar);
        aufgabenstellung = findViewById(R.id.satzglieder_aufgabenstellung);
        space = findViewById(R.id.satzglieder_space);

        weiterButton.setVisibility(View.GONE);
        progressBar.setMax(maxProgress);

        sentenceCount = dbHelper.countTableEntries(BeispielsatzDB.FeedEntry.TABLE_NAME);

        newSentence();

    }

    /**
     * Creates a new sentence structure if the maxProgress is not yet reached.
     * Ending the game otherwise
     */
    private void newSentence(){


        int progress = sharedPref.getInt(KEY_PROGRESS_SATZGLIEDER, 0);

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


            //Creating the buttons
            createButtons(currentSentence, elementToSelect);

            //Adding buttons to the layout
            confirmButtonWidth(buttons);

        }else {

            progressBar.setProgress(maxProgress);
            endGame();
        }

    }

    /**
     * Sets the text of the TextView 'aufgabenstellung' according
     * to the chosen element of the sentence.
     * @param selectedElement the sentence element that is correct.
     */
    private void setInstructionText(String selectedElement){

        String elementDescription;

        switch (selectedElement){

            case G_SUBJEKT_PRAEDIKAT:
                //Since the chosen element is "Subjekt" & "Prädikat" at the same time we chose
                //one of the both at random to be the text
                Random rand = new Random();
                elementDescription = rand.nextBoolean() ? "Subjekt" : "Prädikat";

                break;

            default:
                //All other elements can be named according to their String value
                elementDescription = selectedElement;
                break;
        }

        String instructionText = "Bestimme das " + elementDescription + "!";
        aufgabenstellung.setText(instructionText);
    }

    /**
     * Shuffles an array with the Fisher–Yates-Method
     * @param array The array that we want shuffled
     * @return The shuffled array
     */
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

    /**
     * Removes all elements from the main-linearLayout
     * Re-Adds the elements that have to be there at all times
     * -> Only buttons are removed
     *
     * Clears the ArrayList/HashMap holding information about the buttons since they aren't needed anymore
     */
    private void removeButtons(){

        linearLayout.removeAllViews();

        linearLayout.addView(progressBar);
        linearLayout.addView(aufgabenstellung);
        linearLayout.addView(space);

        buttons.clear();
        buttonHashMap.clear();
    }

    /**
     * Gets the text for each of the sentence elements.
     * Sets the text as the text of a newly created button
     * Adds the button to an array for access later.
     *
     * @param currentSentence The current structure of the sentence.
     *                        Each array-element holds one element of the sentence.
     * @param elementToSelect The sentence element which was chosen to be the correct one
     *                        that is to be selected by the user.
     */
    private void createButtons(String[] currentSentence, String elementToSelect){


        //Selecting SG/PL for the subject and predicate as they have to be the same.
        String numerusSubjektPraedikat = new Random().nextBoolean() ? NUM_SG : NUM_PL;
        String numerus;
        int vocID;

        for (String s : currentSentence) {
            Button button = new Button(this, null, android.R.attr.borderlessButtonStyle);

            //Adding spaces between the words so that they aren't directly next to eachother
            String text = " ";
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
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG
                        );
                    }else {
                        //Plural
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_NOM_PL
                        );
                    }

                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
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

                    text += dbHelper.getKonjugiertesVerb(
                            vocID,
                            konjugation);

                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
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

                        text += dbHelper.getKonjugiertesVerb(
                                vocID,
                                Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG
                        );
                    }else {
                        //Plural

                        text += dbHelper.getKonjugiertesVerb(
                                vocID,
                                Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL
                        );

                    }
                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
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
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_GEN_SG
                        );
                    }else {
                        //Plural
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_GEN_PL
                        );
                    }

                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
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
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_DAT_SG
                        );
                    }else {
                        //Plural
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_DAT_PL
                        );
                    }

                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
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
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_AKK_SG
                        );
                    }else {
                        //Plural
                        text += dbHelper.getDekliniertenSubstantiv(
                                vocID,
                                DeklinationsendungDB.FeedEntry.COLUMN_AKK_PL
                        );
                    }

                    //#DEVELOPER
                    if (DEV_CHEAT_MODE){
                        text += "(A)";
                    }

                    break;

                default:
                    Log.e(KEY_PROGRESS_SATZGLIEDER, "The requested case '" + s + "' in \"addButtons\" was not found");

                    text += "N/A";

            }

            //Adding spaces between the words so that they aren't directly next to eachother
            text += " ";


            text = text.toLowerCase();
            button.setText(text);

            if (s.equals(elementToSelect)) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button button = (Button)v;
                        answerSelected(true, button);
                    }
                });
                buttons.add(button);
                correctButtonLocation = buttons.size()-1;
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

        }
    }

    /**
     * Adding entries to the HashMap "buttonHashMap" where every button from "buttons" is associated
     * with its width as value so that we can use it later to manage the button-placement in drawButtons
     *
     * The buttons are also configured here (->style etc.).
     * @param buttons A array containing the buttons where the width is needed.
     */
    private void confirmButtonWidth(ArrayList<Button> buttons) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (Button b : buttons) {

            //Initializing each button with set parameters
            b.setLayoutParams(params);
            b.setGravity(Gravity.CENTER_HORIZONTAL);
            b.setTextSize(30);

            b.setBackgroundColor(backgroundColor);

            b.setVisibility(View.INVISIBLE);
            linearLayout.addView(b);

            //Creating a 'final' copy of the button so that we can track when the button is fully rendered
            //We need to wait till then because we cant get the width of the button otherwise
            final Button view = b;
            view.post(new Runnable() {
                //Will only run when the button is fully rendered
                @Override
                public void run() {

                    //Getting the width of the button after it is drawn
                    //and associates it with the button on the HashMap
                    buttonHashMap.put(view, view.getWidth());
                    //Removing the drawn button
                    linearLayout.removeView(linearLayout.getChildAt(linearLayout.indexOfChild(view)));

                    //calling a seperate mathod to signal that the progress was executed
                    drawButtons();
                }
            });

        }
    }

    /**
     * Drawing buttons in a single row until they would reach out of the screen
     * -> starting a new row for the remaining buttons
     *
     * => Makes the layout look more like a real sentence
     */
    private void drawButtons(){

        //This method does nothing until all buttons from "button" have been assigned a width in "buttonHashMap"
        if (buttons.size() != buttonHashMap.size()) return;

        int layoutWidth = linearLayout.getWidth();
        int buttonsTogetherWidth = 0;

        //Creating a new horizontal LinearLayout that contains buttons till they are wider than the screen
        //Then a new LinearLayout is created
        // -> Makes the buttons look more like sentences instead of just buttons placed
        // in a straight line downwards
        LinearLayout row = new LinearLayout(getApplicationContext());
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        row.setOrientation(LinearLayout.HORIZONTAL);

        for (Button b : buttons){

            if (buttonsTogetherWidth + buttonHashMap.get(b) >= layoutWidth){
                //Button would reach out of the screen

                //Current row gets added to the main-linearLayout
                linearLayout.addView(row);

                buttonsTogetherWidth = 0;

                //New row gets created for the word
                row = new LinearLayout(getApplicationContext());
                row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                row.setOrientation(LinearLayout.HORIZONTAL);
            }

            //Setting the button to be visible as they were invisible when they were first added
            //This is because we don't want the buttons to flash for 1 frame in the original position
            b.setVisibility(View.VISIBLE);
            row.addView(b);
            //Adding the width of the button to the counter so that it gets measured on the next looping
            buttonsTogetherWidth += buttonHashMap.get(b);
        }
        //Adding the last row to the main-LinearLayout
        linearLayout.addView(row);

    }

    /**
     * Raises or lowers the progressBar based on if the answer was correct or not.
     * Sets the color of the correct button as green (and false red).
     * Sets the buttons to be unclickable.
     * @param correct Was the selected answer correct
     * @param button The selected button where the method-call originated from
     */
    private void answerSelected(boolean correct, Button button){

        SharedPreferences.Editor editor = sharedPref.edit();
        int currentProgress = sharedPref.getInt(KEY_PROGRESS_SATZGLIEDER, 0);

        if (correct){
            button.setBackgroundColor(colorButtonCorrect);

            if (currentProgress <= maxProgress) {
                editor.putInt(KEY_PROGRESS_SATZGLIEDER,
                        currentProgress + 1);
            }

        }else{
            button.setBackgroundColor(colorButtonIncorrect);
            //Setting the color of the correct button to green
            buttons.get(correctButtonLocation).setBackgroundColor(colorButtonCorrect);

            if (currentProgress > 0){
                editor.putInt(KEY_PROGRESS_SATZGLIEDER,
                        currentProgress - 1);
            }
        }

        editor.apply();

        //Making all buttons unclickable.
        //Only new Buttons that are added on reset will be clickable again
        for (Button b: buttons){
            b.setClickable(false);
        }

        weiterButton.setVisibility(View.VISIBLE);
    }

    /**
     * Called from the reset-button placed in the .xml-file
     * Starting a new sentence.
     *
     * @param v the button that was clicked.
     */
    public void satzgliederButtonPressed(View v) {

        switch (v.getId()) {
            case R.id.satzglieder_next_button:
                removeButtons();
                newSentence();
                weiterButton.setVisibility(View.GONE);
                break;

            case R.id.satzglieder_back_button:
                finish();
                break;

            case R.id.satzglieder_progress_reset_button:
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_SATZGLIEDER, 0);
                editor.apply();
                finish();
                break;
        }
    }

    private void endGame(){

        Button resetProgress = findViewById(R.id.satzglieder_progress_reset_button);
        Button back = findViewById(R.id.satzglieder_back_button);

        resetProgress.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);

        aufgabenstellung.setText("");
    }
}
