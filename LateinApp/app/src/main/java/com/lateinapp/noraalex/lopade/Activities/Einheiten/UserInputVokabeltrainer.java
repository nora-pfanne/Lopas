package com.lateinapp.noraalex.lopade.Activities.Einheiten;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.AdjektivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.AdverbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.PräpositionDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SprichwortDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SubstantivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.Vokabel;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

import static com.lateinapp.noraalex.lopade.Databases.SQL_DUMP.allVocabularyTables;
import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_FINISHED_USERINPUT_VOKABELTRAINER;

public class UserInputVokabeltrainer extends LateinAppActivity implements Animation.AnimationListener {

    private static final String TAG = "UserInputVokabeltrainer";

    private SharedPreferences sharedPref;
    private DBHelper dbHelper;

    private TextView request,
         solution,
         titel,
         score,
         combo;
    private EditText userInput;
    private ProgressBar progressBar;
    //FIXME: Remove button elevation to make it align with 'userInput'-EditText
    private Button bestaetigung,
        weiter;

    //Score stuff
    private TextView sCongratulations,
        sCurrentTrainer,
        sPercent,
        sPercentValue,
        sEndScore,
        sEndScoreValue,
        sHighScore,
        sHighScoreValue,
        sGrade,
        sGradeValue;
    private Button sBack,
        sReset;



    private Vokabel currentVokabel;

    private int lektion;
    private int backgroundColor;

    private static final int pointBaseline = 100;

    Animation animScore,
        animShake;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_user_input);

        setup();

        newRequest();
    }

    private void setup(){

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        sharedPref = General.getSharedPrefrences(getApplicationContext());
        dbHelper = new DBHelper(getApplicationContext());

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.GhostWhite, null);
        request = findViewById(R.id.textUserInputLatein);
        solution = findViewById(R.id.textUserInputDeutsch);
        userInput = findViewById(R.id.textUserInputUserInput);
        progressBar = findViewById(R.id.progressBarUserInput);
        bestaetigung = findViewById(R.id.buttonUserInputEingabeBestätigt);
        weiter = findViewById(R.id.buttonUserInputNächsteVokabel);
        titel = findViewById(R.id.textUserInputÜberschrift);
        score = findViewById(R.id.textUserInputScore);
        combo = findViewById(R.id.textUserInputCombo);

        //Score stuff
        sCongratulations = findViewById(R.id.scoreCongratulations);
        sCurrentTrainer = findViewById(R.id.scoreCurrentTrainer);
        sPercent = findViewById(R.id.scorePercent);
        sPercentValue = findViewById(R.id.scorePercentValue);
        sEndScore = findViewById(R.id.scoreEndScore);
        sEndScoreValue = findViewById(R.id.scoreEndScoreValue);
        sHighScore = findViewById(R.id.scoreHighScore);
        sHighScoreValue = findViewById(R.id.scoreHighScoreValue);
        sGrade = findViewById(R.id.scoreGrade);
        sGradeValue = findViewById(R.id.scoreGradeValue);
        sBack = findViewById(R.id.scoreButtonBack);
        sReset = findViewById(R.id.scoreButtonReset);


        animScore = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.score_move_fade);
        animScore.setAnimationListener(this);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        animShake.setAnimationListener(this);

        //TODO: We dont have a score on other trainers yet.
        //This means that we have to hide the score/combo TextView originally
        //and only make it visible here in this trainer
        score.setVisibility(View.VISIBLE);
        combo.setVisibility(View.VISIBLE);

        userInput.setHint("Übersetzung");
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
        titel.setText("Vokabeltrainer");

        solution.setVisibility(View.GONE);
        weiter.setVisibility(View.GONE);

        progressBar.setMax(100);
        progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

        combo.setText("Combo: " + Score.getCombo(lektion, sharedPref) + "x");
        score.setText("Score: " + Score.getScoreVocabularyTrainer(lektion, sharedPref));
    }

    private void newRequest(){

        progressBar.setProgress((int)(dbHelper.getGelerntProzent(lektion) * 100));

        //Checks if all vocabularies have been learned already
        if (dbHelper.getGelerntProzent(lektion) == 1) {

            hideKeyboard();

            allLearned();


        } else {
            userInput.setText("");
            userInput.setBackgroundColor(backgroundColor);
            userInput.setFocusableInTouchMode(true);

            showKeyboard();

            //Getting a new vocabulary.
            currentVokabel = dbHelper.getRandomVocabulary(lektion);
            String lateinText = currentVokabel.getLatein();
            //#DEVELOPER
            if (DEVELOPER && DEV_CHEAT_MODE) lateinText += "\n" + currentVokabel.getDeutsch();
            request.setText(lateinText);

            //Adjusting the visibility of the buttons.
            bestaetigung.setVisibility(View.VISIBLE);
            weiter.setVisibility(View.GONE);
            solution.setVisibility(View.GONE);
        }
    }

    private void checkInput(){
        userInput.setFocusable(false);

        hideKeyboard();

        bestaetigung.setVisibility(View.GONE);
        weiter.setVisibility(View.VISIBLE);
        solution.setVisibility(View.VISIBLE);

        //Checking the userInput against the translation
        int color;
        int scoreDifference;
        if(compareTranslation(userInput.getText().toString(), currentVokabel.getDeutsch())){

            //Input was correct

            dbHelper.incrementValue(getVokabelTable(currentVokabel), "Amount_Correct", currentVokabel.getId(), 1);

            scoreDifference = Score.modifyScore(pointBaseline, true, lektion, sharedPref);

            //Checking the vocabulary as learned
            dbHelper.setGelernt(getVokabelTable(currentVokabel), currentVokabel.getId(), true);

            color = ResourcesCompat.getColor(getResources(), R.color.InputRightGreen, null);
        }else {

            //Input was incorrect

            scoreDifference = Score.modifyScore(pointBaseline, false, lektion, sharedPref);

            dbHelper.incrementValue(getVokabelTable(currentVokabel), "Amount_Incorrect", currentVokabel.getId(), 1);

            color = ResourcesCompat.getColor(getResources(), R.color.InputWrongRed, null);

            weiter.startAnimation(animShake);
            userInput.startAnimation(animShake);
        }
        userInput.setBackgroundColor(color);

        popupScore(scoreDifference);
        combo.setText("Combo: " + Score.getCombo(lektion, sharedPref) + 'x');
        score.setText("Score: " + Score.getScoreVocabularyTrainer(lektion, sharedPref) + " | HS: " + Score.getHighScoreVocabularyTrainer(sharedPref,lektion));


        //Showing the correct translation
        solution.setText(currentVokabel.getDeutsch());

    }

    /**
     * Compares the userInput with a wanted translation and returns if the comparison was successful.
     * @param userInput String to be compared with the translation
     * @param wantedTranslation the original translation to be compared with
     * @return Was the comparison successful
     */
    private boolean compareTranslation(String userInput, String wantedTranslation){

        String[] userTokens = userInput.split(",", -1);
        String[] translationTokens = wantedTranslation.split(",", -1);

        //Checking if every userToken[]-element matches with a translation
        for (String user : userTokens){

            // Returns false for empty tokens
            if (user.equals("")){
                return false;
            }

            //Deleting all whitespaces at the start of the token
            if (user.length() > 1) {
                while (user.charAt(0) == ' ') {
                    user = user.substring(1, user.length());
                    if (user.length() == 1) break;
                }
            }
            //Deleting all whitespaces at the end of the token
            if (user.length() > 1) {
                while (user.charAt(user.length() - 1) == ' ') {
                    user = user.substring(0, user.length() - 1);
                    if (user.length() == 1) break;
                }
            }

            boolean found = false;

            for (String translation : translationTokens){

                //Deleting all whitespaces at the start of the token
                while (translation.charAt(0) == ' '){
                    if (translation.length() == 1) break;
                    translation = translation.substring(1, translation.length());
                }

                //Deleting all whitespaces at the end of the token
                while (translation.charAt(translation.length()-1) == ' '){
                    if (translation.length() == 1) break;
                    translation = translation.substring(0, translation.length()-1);
                }
                //Checking with pronouns
                if (user.equalsIgnoreCase(translation)){
                    found = true;
                }
                //Checking without pronouns
                if (translation.contains("der") || translation.contains("die") || translation.contains("das") ||
                    translation.contains("Der") || translation.contains("Die") || translation.contains("Das")){
                    if (user.equalsIgnoreCase(translation.substring(4))){
                        found = true;
                    }
                }
                //Checking without 'Sich'/'sich'
                if (translation.contains("sich") || translation.contains("Sich")){
                    if (user.equalsIgnoreCase(translation.substring(5))){
                        found = true;
                    }
                }

            }

            if (!found){
                return false;
            }

        }

        //Everything was correct if we got here without returning false.
        return true;
    }

    /**
     * Determines what subclass of 'Vokabel' the given object is.
     * @param vokabel the 'Vokabel' where the type is to be determined
     * @return type of the 'Vokabel'-instance
     */
    private String getVokabelTable(Vokabel vokabel){

        if(vokabel instanceof SubstantivDB){

            return SubstantivDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof VerbDB){

            return VerbDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof SprichwortDB){

            return SprichwortDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof PräpositionDB){

            return PräpositionDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof AdverbDB){

            return AdverbDB.FeedEntry.TABLE_NAME;

        }else if(vokabel instanceof AdjektivDB){

            return AdjektivDB.FeedEntry.TABLE_NAME;

        }else{

            Log.e(TAG,"No VokabelTyp found");

            return "";
        }
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
        progressBar.setVisibility(View.GONE);
        score.setVisibility(View.GONE);
        combo.setVisibility(View.GONE);

        //Score screen
        sCongratulations.setVisibility(View.VISIBLE);
        sCurrentTrainer.setVisibility(View.VISIBLE);
        sPercent.setVisibility(View.VISIBLE);
        sPercentValue.setVisibility(View.VISIBLE);
        sEndScore.setVisibility(View.VISIBLE);
        sEndScoreValue.setVisibility(View.VISIBLE);
        sHighScore.setVisibility(View.VISIBLE);
        sHighScoreValue.setVisibility(View.VISIBLE);
        sGrade.setVisibility(View.VISIBLE);
        sGradeValue.setVisibility(View.VISIBLE);
        sBack.setVisibility(View.VISIBLE);
        sReset.setVisibility(View.VISIBLE);

        sCurrentTrainer.setText("Du hast gerade Lektion " + lektion + " abgeschlossen!");

        int vocabularyAmount = dbHelper.countTableEntries(allVocabularyTables, lektion);

        int score = Score.getScoreVocabularyTrainer(lektion, sharedPref);
        int scoreMax = Score.getMaxPossiblePoints(pointBaseline, vocabularyAmount);
        float scorePercent = (float)score / (float)scoreMax;
        int highScore = Score.getHighScoreVocabularyTrainer(sharedPref, lektion);
        int grade = Score.getGrade(pointBaseline, vocabularyAmount, lektion, sharedPref);

        SpannableStringBuilder percentText = General.makeSectionOfTextBold((int)(scorePercent*100) + "%", (int)(scorePercent*100) + "%");
        SpannableStringBuilder endScoreText = General.makeSectionOfTextBold(score+"/"+scoreMax,""+score);
        SpannableStringBuilder highScoreText = General.makeSectionOfTextBold(highScore + "/" + scoreMax, ""+highScore);
        SpannableStringBuilder gradeText = General.makeSectionOfTextBold(grade+"P", ""+grade);

        sPercentValue.setText(percentText);
        sEndScoreValue.setText(endScoreText);
        sHighScoreValue.setText(highScoreText);
        sGradeValue.setText(gradeText);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(KEY_FINISHED_USERINPUT_VOKABELTRAINER + lektion, true);
        editor.apply();
    }

    /**
     * Handling the button-presses
     * @param view the view of the pressed button
     */
    public void userInputButtonClicked(View view){

        switch (view.getId()){

            //Checking if all vocabularies have been learned already and getting a new one
            case (R.id.buttonUserInputNächsteVokabel):

                newRequest();
                break;

            //Checking if the user input was correct
            case (R.id.buttonUserInputEingabeBestätigt):

                checkInput();
                break;

            //Setting the 'learned' state of all vocabularies of the current lektion to false
            case (R.id.scoreButtonReset):

                resetCurrentLektion();
                break;

            //Returning to the previous activity
            case (R.id.scoreButtonBack):

                finish();
                break;
        }
    }

    private void resetCurrentLektion(){
        dbHelper.resetLektion(lektion);
        Score.resetScoreVocabulary(lektion, sharedPref);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();

        hideKeyboard();
    }

    @Override
    public void openInfoPopup() {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView;

        popupView = layoutInflater.inflate(R.layout.popup_info_vokabeltrainer, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = popupView.findViewById(R.id.popup_info_vokabeltrainer_dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private TextView tempScoreView;
    private void popupScore(int score){


        String message = "";
        int color;

        if(score > 0){
            message += "+";
            color = ResourcesCompat.getColor(getResources(), R.color.fullGreen, null);

        }else if(score < 0){
            color = ResourcesCompat.getColor(getResources(), R.color.fullRed, null);

        }else{
            //No score difference
            color = ResourcesCompat.getColor(getResources(), R.color.fullGray, null);

        }
        message += score;


        tempScoreView = new TextView(this);
        tempScoreView.setText(message);
        tempScoreView.setTextColor(color);
        tempScoreView.setTextSize(30);
        tempScoreView.setTypeface(tempScoreView.getTypeface(), Typeface.BOLD);
        tempScoreView.setId(View.generateViewId());

        tempScoreView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        ConstraintLayout layout = findViewById(R.id.constraint_Layout);
        layout.addView(tempScoreView, tempScoreView.getLayoutParams());

        ConstraintSet c = new ConstraintSet();
        c.clone(layout);
        c.connect(tempScoreView.getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT,0);
        c.connect(tempScoreView.getId(), ConstraintSet.LEFT,   layout.getId(), ConstraintSet.LEFT,0);
        c.connect(tempScoreView.getId(), ConstraintSet.TOP,   layout.getId(), ConstraintSet.TOP,0);
        c.connect(tempScoreView.getId(), ConstraintSet.BOTTOM,   layout.getId(), ConstraintSet.BOTTOM,0);
        c.applyTo(layout);

        tempScoreView.startAnimation(animScore);

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(animation == animScore) {
            tempScoreView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {}
    @Override
    public void onAnimationRepeat(Animation animation) {}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.reset_trainer_button, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            //Opening a popup window
            case (R.id.action_reset_trainer):

                new AlertDialog.Builder(this)
                        .setTitle("Lektion zurücksetzen?")
                        .setMessage("Willst du den Vokabeltrainer für die Lektion " + lektion + "wirklich neu starten?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                General.showMessage("Lektion " + lektion + " zurückgesetzt!", getApplicationContext());
                                resetCurrentLektion();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();


                break;

        }

        return super.onOptionsItemSelected(item);
    }
}



