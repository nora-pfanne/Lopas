package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
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
import static com.lateinapp.noraalex.lopade.Global.KEY_CURRENT_MISTAKE_AMOUNT_KASUS;
import static com.lateinapp.noraalex.lopade.Global.KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE;

public class UserInputEsseVelleNolle extends LateinAppActivity {

    private static final String TAG = "UserInputEsseVelleNolle";

    private ArrayList<Vokabel> viableVocabularies;

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

    //Score stuff
    private TextView sCongratulations,
            sCurrentTrainer,
            sMistakeAmount,
            sMistakeAmountValue,
            sBestTry,
            sBestTryValue,
            sHighScore,
            sHighScoreValue,
            sGrade,
            sGradeValue;
    private Button sBack,
            sReset;

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

        //Score stuff
        sCongratulations = findViewById(R.id.scoreCongratulations);
        sCurrentTrainer = findViewById(R.id.scoreCurrentTrainer);
        sMistakeAmount = findViewById(R.id.scoreMistakes);
        sMistakeAmountValue = findViewById(R.id.scoreMistakeValue);
        sBestTry = findViewById(R.id.scoreBestRunMistakeAmount);
        sBestTryValue = findViewById(R.id.scoreEndScoreValue);
        sHighScore = findViewById(R.id.scoreHighScore);
        sHighScoreValue = findViewById(R.id.scoreHighScoreValue);
        sGrade = findViewById(R.id.scoreGrade);
        sGradeValue = findViewById(R.id.scoreGradeValue);
        sBack = findViewById(R.id.scoreButtonBack);
        sReset = findViewById(R.id.scoreButtonReset);

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

        int wrong = Score.getCurrentMistakesEsseVelleNolle(sharedPref);
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
        reset.setVisibility(View.GONE);
        zurück.setVisibility(View.GONE);
        titel.setVisibility(View.GONE);


        sCongratulations.setVisibility(View.VISIBLE);
        sCurrentTrainer.setVisibility(View.VISIBLE);
        sMistakeAmount.setVisibility(View.VISIBLE);
        sMistakeAmountValue.setVisibility(View.VISIBLE);
        sBestTry.setVisibility(View.VISIBLE);
        sBestTryValue.setVisibility(View.VISIBLE);
        sHighScore.setVisibility(View.GONE);
        sHighScoreValue.setVisibility(View.GONE);
        sGrade.setVisibility(View.VISIBLE);
        sGradeValue.setVisibility(View.VISIBLE);
        sBack.setVisibility(View.VISIBLE);
        sReset.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);

        amountWrong.setVisibility(View.GONE);

        int mistakeAmount = Score.getCurrentMistakesPersInput(sharedPref);

        Score.updateLowestMistakesEsseVelleNolle(mistakeAmount, sharedPref);

        sCurrentTrainer.setText("Du hast gerade den Personalendung-Trainer abgeschlossen!");

        String grade = Score.getGradeFromMistakeAmount(maxProgress + 2*mistakeAmount, mistakeAmount);

        String lowestEverText = Score.getLowestMistakesEsseVelleNolle(sharedPref) + "";
        SpannableStringBuilder gradeText = General.makeSectionOfTextBold(grade, ""+grade);

        if(mistakeAmount != -1){
            sMistakeAmountValue.setText(Integer.toString(mistakeAmount) + "");
        }else{
            sMistakeAmountValue.setText("N/A");
        }
        sBestTryValue.setText(lowestEverText);
        sGradeValue.setText(gradeText);
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

            case (R.id.scoreButtonReset):

                resetCurrentLektion();
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

    private void resetCurrentLektion(){


        new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                .setTitle("Trainer zurücksetzen?")
                .setMessage("Willst du den Esse-Velle-Nolle-Trainer wirklich neu starten?\nDeine beste Note wird beibehalten!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        General.showMessage("Esse-Velle-Nolle-Trainer zurückgesetzt!", getApplicationContext());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE, 0);
                        editor.apply();

                        Score.resetCurrentMistakesEsseVelleNolle(sharedPref);
                        finish();

                    }})
                .setNegativeButton(android.R.string.no, null).show();



    }
}
