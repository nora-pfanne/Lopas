package com.lateinapp.noraalex.lopade;

import android.content.SharedPreferences;

import static com.lateinapp.noraalex.lopade.Global.KEY_FINISHED_USERINPUT_VOKABELTRAINER;
import static com.lateinapp.noraalex.lopade.Global.KEY_NEW_HIGHSCORE_VOKABELTRAINER;
import static com.lateinapp.noraalex.lopade.Global.KEY_SCORE_VOCAULARY;
import static com.lateinapp.noraalex.lopade.Global.KEY_HIGH_SCORE_VOCAULARY;
import static com.lateinapp.noraalex.lopade.Global.KEY_HIGH_SCORE_ALL_TRAINERS;
import static com.lateinapp.noraalex.lopade.Global.KEY_COMBO_VOCABULARY;
import static com.lateinapp.noraalex.lopade.Global.LEKTION_COUNT;
import static com.lateinapp.noraalex.lopade.Global.STATE_FIRST_PLAYTHROUGH;
import static com.lateinapp.noraalex.lopade.Global.STATE_NEW_HS_EARLIER;
import static com.lateinapp.noraalex.lopade.Global.STATE_NEW_HS_NOW;
import static com.lateinapp.noraalex.lopade.Global.STATE_NO_HS_YET;

public class Score {

    private static final int MAX_COMBO = 3;
    private static final int MIN_COMBO = 0;

    public static void firstStartup(SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(int lektion = 1; lektion <= LEKTION_COUNT; lektion++){
            editor.putInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_FIRST_PLAYTHROUGH);
        }
        editor.apply();
    }

    public static int getMaxPossiblePoints(int pointBaseline, int inputAmount){

        int score = 0;

        int combo = MIN_COMBO;

        for(int i = 0; i < inputAmount; i++){

            score += getScoreDifference(getComboMultiplier(combo), pointBaseline, true);;

            if(combo >= MIN_COMBO && combo < MAX_COMBO){
                combo++;
            }
        }

        return score;
    }

    public static int modifyScore(int pointBaseline, boolean inputCorrect, int lektion, SharedPreferences sharedPreferences){


        int oldScore = getScoreVocabularyTrainer(lektion, sharedPreferences);
        int difference = getScoreDifference(getCombo(lektion, sharedPreferences), pointBaseline, inputCorrect);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Score doesnt get modified when it does below 0
        if(oldScore + difference >= 0){

            int newScore = oldScore + difference;

            editor.putInt(KEY_SCORE_VOCAULARY + lektion, newScore);

            if(newScore > getHighScoreVocabularyTrainer(sharedPreferences, lektion)){

                //THE HIGHSCORE DOESNT GET UPDATED HERE YET
                //This happens in updateHighscore() when the trainer is finished

                int newHighscoreState = sharedPreferences.getInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_FIRST_PLAYTHROUGH);
                if(newHighscoreState == STATE_NO_HS_YET){
                    editor.putInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_NEW_HS_NOW);
                }else if(newHighscoreState == STATE_NEW_HS_NOW){
                    editor.putInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_NEW_HS_EARLIER);
                }

            }

        }

        editor.apply();

        //Updating combo
        if(inputCorrect){
            increaseCombo(lektion, sharedPreferences);
        }else{
            resetCombo(lektion, sharedPreferences);
        }

        //Does not return the change in score but what wouldve been changed..
        //if the score is 0 we dont subtract points but still return a "-100" for example.
        return difference;
    }

    public static boolean isNewHighscoreNow(int lektion, SharedPreferences sharedPreferences){
        return (sharedPreferences.getInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_FIRST_PLAYTHROUGH) == STATE_NEW_HS_NOW);
    }

    public static void resetScoreVocabulary(int lektion, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SCORE_VOCAULARY + lektion, 0);
        editor.putInt(KEY_NEW_HIGHSCORE_VOKABELTRAINER + lektion, STATE_NO_HS_YET);
        editor.putBoolean(KEY_FINISHED_USERINPUT_VOKABELTRAINER + lektion, false);
        editor.apply();

        resetCombo(lektion, sharedPreferences);
    }

    public static void resetHighscoreVocabulary(int lektion, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_HIGH_SCORE_VOCAULARY + lektion, 0);
        editor.apply();
    }

    public static void updateHighscore(int lektion, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Setting trainer to "done"
        editor.putBoolean(KEY_FINISHED_USERINPUT_VOKABELTRAINER + lektion, true);

        //Updating HighScore
        int newHighScore = getScoreVocabularyTrainer(lektion, sharedPreferences);

        if(newHighScore > getHighScoreVocabularyTrainer(sharedPreferences, lektion)) {


            int oldHighscore = sharedPreferences.getInt(KEY_HIGH_SCORE_VOCAULARY + lektion, 0);
            int delta = newHighScore - oldHighscore;

            editor.putInt(KEY_HIGH_SCORE_ALL_TRAINERS, getScoreAll(sharedPreferences) + delta);
            editor.putInt(KEY_HIGH_SCORE_VOCAULARY + lektion, newHighScore);

        }

        editor.apply();
    }

    //
    //Private methods
    //

    private static void increaseCombo(int lektion, SharedPreferences sharedPreferences){

        int currentCombo = sharedPreferences.getInt(KEY_COMBO_VOCABULARY + lektion, MIN_COMBO);

        if(currentCombo >= MIN_COMBO && currentCombo < MAX_COMBO){
            currentCombo++;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COMBO_VOCABULARY + lektion, currentCombo);
        editor.apply();
    }

    private static void decreaseCombo(int lektion, SharedPreferences sharedPreferences){

        int currentCombo = sharedPreferences.getInt(KEY_COMBO_VOCABULARY + lektion, MIN_COMBO);

        if(currentCombo > MIN_COMBO && currentCombo <= MAX_COMBO){
            currentCombo--;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_COMBO_VOCABULARY + lektion, currentCombo);
        editor.apply();
    }

    private static int getScoreDifference(int combo, int pointBaseline, boolean inputCorrect){

        int amount;

        //Calculating final amount to increase the score by
        if(inputCorrect){
            amount = combo * pointBaseline;
        }else{
            amount = -pointBaseline;
        }
        return (amount);
    }

    private static void resetCombo(int lektion, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_COMBO_VOCABULARY + lektion, MIN_COMBO);

        editor.apply();
    }

    private static String percentageToGrade(float percentage){
        //Currently using this scale:
        //http://www.wzemann.de/physik/page18/files/notenskala.jpg
        //from 0 to 15, 15 being the best possible

        /*
        int score;

        if(percentage > 0.95f){
            score = 15;
        }else if(percentage > 0.90f){
            score = 14;
        }else if(percentage > 0.85f){
            score = 13;
        }else if(percentage > 0.80f){
            score = 12;
        }else if(percentage > 0.75f){
            score = 11;
        }else if(percentage > 0.70f){
            score = 10;
        }else if(percentage > 0.65f){
            score = 9;
        }else if(percentage > 0.60f){
            score = 8;
        }else if(percentage > 0.55f){
            score = 7;
        }else if(percentage > 0.50f){
            score = 6;
        }else if(percentage > 0.45f){
            score = 5;
        }else if(percentage > 0.40f){
            score = 4;
        }else if(percentage > 0.33f){
            score = 3;
        }else if(percentage > 0.26f){
            score = 2;
        }else if(percentage > 0.19f){
            score = 1;
        }else{
            score = 0;
        }
        */

        String score;

        if(percentage > 0.95f){
            score = "1+";
        }else if(percentage > 0.90f){
            score = "1";
        }else if(percentage > 0.85f){
            score = "1-";
        }else if(percentage > 0.80f){
            score = "2+";
        }else if(percentage > 0.75f){
            score = "2";
        }else if(percentage > 0.70f){
            score = "2-";
        }else if(percentage > 0.65f){
            score = "3+";
        }else if(percentage > 0.60f){
            score = "3";
        }else if(percentage > 0.55f){
            score = "3-";
        }else if(percentage > 0.50f){
            score = "4+";
        }else if(percentage > 0.45f){
            score = "4";
        }else if(percentage > 0.40f){
            score = "4-";
        }else if(percentage > 0.33f){
            score = "5+";
        }else if(percentage > 0.26f){
            score = "5";
        }else if(percentage > 0.19f){
            score = "5-";
        }else{
            score = "6";
        }

        return score;

    }

    private static int getComboMultiplier(int combo){
        return (int)General.pow(2, combo);
    }

    //
    //Public getters and setters
    //

    public static int getCombo(int lektion, SharedPreferences sharedPreferences){
        int combo = sharedPreferences.getInt(KEY_COMBO_VOCABULARY + lektion, MIN_COMBO);
        return getComboMultiplier(combo);
    }

    public static String getGrade(int pointBaseline, int entryAmount, int lektion, SharedPreferences sharedPreferences){

        int points = getScoreVocabularyTrainer(lektion, sharedPreferences);
        int maxPoints = getMaxPossiblePoints(pointBaseline, entryAmount);

        float percentage = (float)points / (float)maxPoints;

        return percentageToGrade(percentage);

    }

    public static int getHighScoreVocabularyTrainer(SharedPreferences sharedPreferences, int lektion){
        return sharedPreferences.getInt(KEY_HIGH_SCORE_VOCAULARY + lektion, 0);
    }

    public static int getScoreVocabularyTrainer(int lektion, SharedPreferences sharedPreferences){
        return sharedPreferences.getInt(KEY_SCORE_VOCAULARY + lektion, 0);
    }

    public static int getScoreAll(SharedPreferences sharedPreferences){
        return sharedPreferences.getInt(KEY_HIGH_SCORE_ALL_TRAINERS, 0);
    }
}
