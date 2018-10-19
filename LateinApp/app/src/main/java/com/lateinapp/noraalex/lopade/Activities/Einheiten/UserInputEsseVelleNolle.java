package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.Personalendung_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Sprechvokal_PräsensDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE;

public class UserInputEsseVelleNolle extends LateinAppActivity {

    private static final String TAG = "UserInputEsseVelleNolle";

    private ArrayList<Vokabel> viableVocabularies;

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

    private TextView request,
            solution,
            titel, amountWrong;
    private EditText userInput;
    private ProgressBar progressBar;
    //FIXME: Remove button elevation to make it align with 'userInput'-EditText
    private Button bestaetigung,
            weiter,
            reset,
            zurück;

    private Vokabel currentVokabel;
    private String currentPersonalendung;

    private final String[] faelle = {
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_SG,
            Personalendung_PräsensDB.FeedEntry.COLUMN_1_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_2_PL,
            Personalendung_PräsensDB.FeedEntry.COLUMN_3_PL};

    private int backgroundColor;
    private final int maxProgress = 20;

    Animation animShake;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_user_input);

        setup();

        newVocabulary();
    }

    private void setup(){

        sharedPref = General.getSharedPrefrences(getApplicationContext());
        dbHelper = DBHelper.getInstance(getApplicationContext());

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

        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        amountWrong = findViewById(R.id.textUserInputMistakes);

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
        titel.setText("Esse & Velle & Nolle");

        TextView score = findViewById(R.id.textUserInputScore);
        score.setVisibility(View.GONE);

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressBar.setMax(maxProgress);

        viableVocabularies = getViableVocabularies();

        int wrong = Score.getCurrentMistakesKasus(sharedPref);
        if (wrong == -1){
            wrong = 0;
        }
        amountWrong.setText("Fehler: " + wrong);


    }

    private void newVocabulary(){

        int progress = sharedPref.getInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0);
        if (progress < maxProgress) {

            progressBar.setProgress(progress);

            showKeyboard();


            //Resetting the userInput.
            userInput.setText("");
            userInput.setBackgroundColor(backgroundColor);
            userInput.setFocusableInTouchMode(true);

            currentVokabel = viableVocabularies.get(
                    ThreadLocalRandom.current().nextInt(0, viableVocabularies.size()));
            currentPersonalendung = faelle[
                    ThreadLocalRandom.current().nextInt(0, faelle.length)];

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
            editor.putInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE,
                    sharedPref.getInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0) + 1);
            editor.apply();
        }else {
            color = ResourcesCompat.getColor(getResources(), R.color.error, null);


            //Decreasing the counter by 1
            if (sharedPref.getInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0) > 0) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE,
                        sharedPref.getInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0) - 1);
                editor.apply();
            }

            weiter.startAnimation(animShake);
            userInput.startAnimation(animShake);
            Score.incrementCurrentMistakesEsseVelleNolle(sharedPref);

            int wrong = Score.getCurrentMistakesEsseVelleNolle(sharedPref);
            if (wrong == -1){
                wrong = 0;
            }
            amountWrong.setText("Fehler: " + wrong);

        }
        userInput.setBackgroundColor(color);

        //Showing the correct translation
        solution.setText(dbHelper.getKonjugiertesVerb(currentVokabel.getId(), currentPersonalendung));

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
            case (R.id.scoreButtonReset):
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0);
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

    /**
     * @return an array with the ids of the vocabularies "esse", "velle" and "nolle"
     */
    private ArrayList<Vokabel> getViableVocabularies(){

        DBHelper dbHelper = DBHelper.getInstance(this);

        String query = "SELECT " + VerbDB.FeedEntry.TABLE_NAME + "." + VerbDB.FeedEntry._ID + ", " + VerbDB.FeedEntry.TABLE_NAME + "." + VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH +
                " FROM " + VerbDB.FeedEntry.TABLE_NAME + ", " + Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME +
                " WHERE " + VerbDB.FeedEntry.TABLE_NAME + "." + VerbDB.FeedEntry.COLUMN_SPRECHVOKAL_ID + " = " +  Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME + "." + Sprechvokal_PräsensDB.FeedEntry._ID +
                " AND ("
                +  Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME + "." + Sprechvokal_PräsensDB.FeedEntry.COLUMN_TITLE + " = 'esse' OR "
                +  Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME + "." + Sprechvokal_PräsensDB.FeedEntry.COLUMN_TITLE + " = 'velle' OR "
                +  Sprechvokal_PräsensDB.FeedEntry.TABLE_NAME + "." + Sprechvokal_PräsensDB.FeedEntry.COLUMN_TITLE + " = 'nolle')";
        Cursor cursor = dbHelper.getWritableDatabase().rawQuery(query, null);

        ArrayList<Vokabel> vocabularies = new ArrayList<>();
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String latein = dbHelper.getKonjugiertesVerb(id, "inf");
            String deutsch = cursor.getString( 1);

            vocabularies.add(new VerbDB(id, latein, deutsch));
        }

        cursor.close();

        return vocabularies;
    }
}
