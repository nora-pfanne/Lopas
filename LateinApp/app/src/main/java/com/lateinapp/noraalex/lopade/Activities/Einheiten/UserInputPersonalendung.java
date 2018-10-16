package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import java.util.Random;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_USERINPUT_PERSONALENDUNG;

public class UserInputPersonalendung extends LateinAppActivity {

    private static final String TAG = "UserInputPersonalendung";

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

    private TextView request,
            solution,
            titel;
    private EditText userInput;
    private ProgressBar progressBar;
    //FIXME: Remove button elevation to make it align with 'userInput'-EditText
    private Button bestaetigung,
            weiter,
            reset,
            zurück;

    private Vokabel currentVokabel;
    private String currentPersonalendung;

    private int[] weights;
    private final String[] faelle = {
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL};

    private String extraFromEinheitenUebersicht;
    private int backgroundColor;
    private final int maxProgress = 20;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_user_input);

        setup();

        newVocabulary();
    }

    private void setup(){
        Intent intent = getIntent();
        extraFromEinheitenUebersicht = intent.getStringExtra("ExtraInputPersonalendung");

        sharedPref = General.getSharedPrefrences(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);
        request = findViewById(R.id.textUserInputLatein);
        solution = findViewById(R.id.textUserInputDeutsch);
        userInput = findViewById(R.id.textUserInputUserInput);
        progressBar = findViewById(R.id.progressBarUserInput);
        bestaetigung = findViewById(R.id.buttonUserInputEingabeBestätigt);
        weiter = findViewById(R.id.buttonUserInputNächsteVokabel);
        reset = findViewById(R.id.scoreButtonReset);
        zurück = findViewById(R.id.scoreButtonBack);
        titel = findViewById(R.id.textUserInputÜberschrift);

        userInput.setHint("Konjugiertes Verb");
        //Makes it possible to move to the next vocabulary by pressing "enter"
        userInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {


                    userInputButtonClicked(findViewById(R.id.buttonUserInputEingabeBestätigt));
                    return true;
                }
                return false;
            }
        });
        titel.setText("Konjugationstrainer");

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        weightSubjects(extraFromEinheitenUebersicht);

        progressBar.setMax(maxProgress);

    }

    private void newVocabulary(){

        int progress = sharedPref.getInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht, 0);

        if (progress < maxProgress) {

            progressBar.setProgress(progress);

            showKeyboard();


            //Resetting the userInput.
            userInput.setText("");
            userInput.setBackgroundColor(backgroundColor);
            userInput.setFocusableInTouchMode(true);

            //Getting a new vocabulary.
            //FIXME: Don't return a random number but one according to the progress (nom->1 /...)
            //random number from 1 to 5 to choose, where the vocabulary comes from
            //Blueprint for randNum: int randomNum = rand.nextInt((max - min) + 1) + min;
            int rand = new Random().nextInt((5 - 1) + 1) + 1;
            currentVokabel = dbHelper.getRandomVocabulary(rand);
            currentPersonalendung = getRandomPersonalendung();

            String lateinText = dbHelper.getKonjugiertesVerb(currentVokabel.getId(), "Inf");
            String personalendungUser = currentPersonalendung.replace("_", " ");
            personalendungUser = personalendungUser.replace("Erste", "1.");
            personalendungUser = personalendungUser.replace("Zweite", "2.");
            personalendungUser = personalendungUser.replace("Dritte", "3.");
            personalendungUser = personalendungUser.replace("Sg", "Pers. Sg.");
            personalendungUser = personalendungUser.replace("Pl", "Pers. Pl.");
            lateinText += "\n" + personalendungUser + " Präsens";
            //#DEVELOPER
            if (DEVELOPER && DEV_CHEAT_MODE){
                lateinText += "\n" + dbHelper.getKonjugiertesVerb(currentVokabel.getId(), currentPersonalendung);
            }
            request.setText(lateinText);

            //Adjusting the button visibility.
            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            solution.setVisibility(View.GONE);

        } else {

            progressBar.setProgress(maxProgress);

            hideKeyboard();

            allLearned();
        }


    }

    private void checkInput(){
        userInput.setFocusable(false);

        hideKeyboard();


        //Checking the userInput against the translation
        int color;
        if(compareString(userInput.getText().toString(), dbHelper.getKonjugiertesVerb(currentVokabel.getId(), currentPersonalendung))){
            color = ResourcesCompat.getColor(getResources(), R.color.correct, null);

            SharedPreferences.Editor editor = sharedPref.edit();

            //Increasing the counter by 1
            editor.putInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht,
                    sharedPref.getInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht, 0) + 1);
            editor.apply();
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.error, null);

            if (sharedPref.getInt(TAG + extraFromEinheitenUebersicht, 0) > 0) {
                SharedPreferences.Editor editor = sharedPref.edit();
                //Decreasing the counter by 1
                editor.putInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht,
                        sharedPref.getInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht, 0) - 1);
                editor.apply();
            }
        }
        userInput.setBackgroundColor(color);

        //Showing the correct translation
        solution.setText(dbHelper.getKonjugiertesVerb(currentVokabel.getId(), currentPersonalendung));

        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.VISIBLE);
        solution.setVisibility(View.VISIBLE);
    }

    /**
     * Sets weights for all entries of 'faelle' depending on the current value of lektion
     */
    private void weightSubjects(String extra){

        int weightErsteSg,
                weightZweiteSg,
                weightDritteSg,
                weightErstePl,
                weightZweitePl,
                weightDrittePl;

        switch (extra){

            case "DRITTE_PERSON":
                weightErsteSg = 0;
                weightZweiteSg = 0;
                weightDritteSg = 1;
                weightErstePl = 0;
                weightZweitePl = 0;
                weightDrittePl = 1;
                break;

            case "ERSTE_ZWEITE_PERSON":
                weightErsteSg = 2;
                weightZweiteSg = 2;
                weightDritteSg = 1;
                weightErstePl = 2;
                weightZweitePl = 2;
                weightDrittePl = 1;
                break;

            default:

                weightErsteSg = 1;
                weightZweiteSg = 1;
                weightDritteSg = 1;
                weightErstePl = 1;
                weightZweitePl = 1;
                weightDrittePl = 1;
                break;

        }

        weights = new int[] {weightErsteSg,
                weightZweiteSg,
                weightDritteSg,
                weightErstePl,
                weightZweitePl,
                weightDrittePl};
    }

    /**
     * @return a int corresponding to to position of a case in faelle[] with respect to the
     *          previously set weights[]-arr
     */
    private String getRandomPersonalendung(){

        //Getting a upper bound for the random number being retrieved afterwards
        int max =  (weights[0] +
                weights[1] +
                weights[2] +
                weights[3] +
                weights[4] +
                weights[5]);

        Random randomNumber = new Random();
        int intRandom = randomNumber.nextInt(max) + 1;
        int sum = 1;
        int sumNew;

        /*
        Each case gets a width corresponding to the 'weights'-arr.
        Goes through every case and checks if the 'randomInt' is in the area of the current case
         */
        int randomVocabulary = -1;
        for(int i = 0; i < weights.length; i++){

            sumNew = sum + weights[i];

            //checks if 'intRandom' is between the 'sum' and 'sumNew' and thus in the area of the current case
            if (intRandom >= sum && intRandom < sumNew){

                randomVocabulary = i;
                break;
            }
            else {
                sum = sumNew;
            }
        }

        if(randomVocabulary == -1){
            //Something went wrong. Log error-message
            Log.e(TAG, "Getting a randomKonjugation failed! Returned -1 for " +
                    "\nrandomNumber: " + randomNumber);
        }


        return faelle[randomVocabulary];
    }

    /**
     * Compares the userInput with a wanted input and returns if the comparison was successful.
     * @param userInput String to be compared with the wanted input
     * @param wantedString the original string to be compared with
     * @return Was the comparison successful?
     */
    private boolean compareString(String userInput, String wantedString){

        // Returns false for empty input
        if (userInput.equals("")){
            return false;
        }

        //Deleting all whitespaces at the start of the input
        if (userInput.length() > 1) {
            while (userInput.charAt(0) == ' ') {
                userInput = userInput.substring(1, userInput.length());
                if (userInput.length() == 1) break;
            }
        }
        //Deleting all whitespaces at the end of the input
        if (userInput.length() > 1) {
            while (userInput.charAt(userInput.length() - 1) == ' ') {
                userInput = userInput.substring(0, userInput.length() - 1);
                if (userInput.length() == 1) break;
            }
        }

        if (userInput.equalsIgnoreCase(wantedString)) return true;
        else return false;

    }

    /**
     * Executed when all vocabularies are learned.
     */
    private void allLearned(){

        request.setVisibility(View.GONE);
        solution.setVisibility(View.GONE);
        userInput.setVisibility(View.GONE);
        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);
        reset.setVisibility(View.VISIBLE);
        zurück.setVisibility(View.VISIBLE);
    }

    /**
     * Handling the button-presses
     * @param view the view of the pressed button
     */
    public void userInputButtonClicked(View view){

        switch (view.getId()){

            //Checking if all vocabularies have been learned already and getting a new one
            case (R.id.buttonUserInputNächsteVokabel):

                newVocabulary();
                break;

            //Checking if the user input was correct
            case (R.id.buttonUserInputEingabeBestätigt):

                checkInput();
                break;

            //Setting the 'learned' state of all vocabularies of the current lektion to false
            case (R.id.scoreButtonReset):
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_USERINPUT_PERSONALENDUNG + extraFromEinheitenUebersicht, 0);
                editor.apply();
                finish();
                break;

            //Returning to the previous activity
            case (R.id.scoreButtonBack):
                finish();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
