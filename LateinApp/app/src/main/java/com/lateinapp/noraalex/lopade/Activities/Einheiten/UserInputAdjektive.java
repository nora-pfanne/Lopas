package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.SharedPreferences;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_USERINPUT_ADJEKTIVE;

public class UserInputAdjektive extends LateinAppActivity {

    private static final String TAG = "UserInputAdjektive";

    private SharedPreferences sharedPref;

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

    private String deutscherText,
                lateinText;


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

        sharedPref = General.getSharedPrefrences(getApplicationContext());

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.background, null);
        request = findViewById(R.id.textUserInputLatein);
        solution = findViewById(R.id.textUserInputDeutsch);
        userInput = findViewById(R.id.textUserInputUserInput);
        progressBar = findViewById(R.id.progressBarUserInput);
        bestaetigung = findViewById(R.id.buttonUserInputEingabeBestätigt);
        weiter = findViewById(R.id.buttonUserInputNächsteVokabel);
        reset = findViewById(R.id.buttonUserInputFortschrittLöschen);
        zurück = findViewById(R.id.buttonUserInputZurück);
        titel = findViewById(R.id.textUserInputÜberschrift);

        userInput.setHint("Dekliniertes Adjektiv");
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
        titel.setText("Adjektive");

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressBar.setMax(maxProgress);
    }

    private void newVocabulary(){

        int progress = sharedPref.getInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE, 0);

        if (progress < maxProgress) {

            progressBar.setProgress(progress);

            showKeyboard();

            //Resetting the userInput.
            userInput.setText("");
            userInput.setBackgroundColor(backgroundColor);
            userInput.setFocusableInTouchMode(true);

            String[] answer = getRandomAdjektiv();

            deutscherText = answer[0];
            lateinText = answer[1];

            request.setText(deutscherText);

            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            solution.setVisibility(View.GONE);

        } else {

            progressBar.setProgress(maxProgress);

            hideKeyboard();

            allLearned();
        }


    }

    private String[] getRandomAdjektiv(){

        int randomInt = (int)(Math.random() * ((4) + 1));

        String deutsch = "deutscher Fülltext";
        String latein = "leer";

        switch(randomInt) {

            case 0:
                deutsch = "großer Gott";
                latein = "magnus deus";
                break;

            case 1:
                deutsch = "kleines Mädchen";
                latein = "parva puella";
                break;

            case 2:
                deutsch = "arme Muse";
                latein = "misera musa";
                break;

            case 3:
                deutsch = "erstaunliches Land";
                latein = "mira terra";
                break;

            case 4:
                deutsch = "guter Weg";
                latein = "bona via";
                break;
        }


        return new String[]{deutsch, latein};
    }

    private void checkInput(){
        userInput.setFocusable(false);

        hideKeyboard();


        //Checking the userInput against the translation
        int color;
        if(compareString(userInput.getText().toString(), lateinText)){
            color = ResourcesCompat.getColor(getResources(), R.color.correct, null);

            SharedPreferences.Editor editor = sharedPref.edit();

            //Increasing the counter by 1
            editor.putInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE,
                    sharedPref.getInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE, 0) + 1);
            editor.apply();
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.error, null);

            if (sharedPref.getInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE, 0) > 0) {
                SharedPreferences.Editor editor = sharedPref.edit();
                //Decreasing the counter by 1
                editor.putInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE,
                        sharedPref.getInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE, 0) - 1);
                editor.apply();
            }
        }
        userInput.setBackgroundColor(color);

        //Showing the correct translation
        solution.setText(lateinText);

        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.VISIBLE);
        solution.setVisibility(View.VISIBLE);
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
            case (R.id.buttonUserInputFortschrittLöschen):
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_USERINPUT_ADJEKTIVE, 0);
                editor.apply();
                finish();
                break;

            //Returning to the previous activity
            case (R.id.buttonUserInputZurück):
                finish();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        hideKeyboard();
    }
}
