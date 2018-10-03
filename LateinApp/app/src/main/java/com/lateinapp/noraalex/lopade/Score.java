package com.lateinapp.noraalex.lopade;

import android.content.SharedPreferences;

public class Score {

    private static final int MAX_COMBO = 3;
    private static final int MIN_COMBO = 0;

    public static int calculateMaxPossiblePoints(int pointBaseline, int inputAmount){

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

        int oldScore = getPoints(lektion, sharedPreferences);
        int combo = getCombo(lektion, sharedPreferences);

        int difference = getScoreDifference(combo, pointBaseline, inputCorrect);

        int delta = difference;
        if(oldScore + difference < 0){
            delta = 0;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score_" + lektion, oldScore + delta);
        editor.putInt("Score_All", getPoints(lektion, sharedPreferences) + delta);
        editor.apply();

        //Does not return the change in score.
        //if the score is 0 we dont subtract points but still show a "-100" for example
        return difference;
    }

    public static int getPoints(int lektion, SharedPreferences sharedPreferences){
        return sharedPreferences.getInt("Score_" + lektion, 0);
    }

    public static int getTotalPoints(SharedPreferences sharedPreferences){
        return sharedPreferences.getInt("Score_All", 0);
    }

    public static void resetPoints(int lektion, SharedPreferences sharedPreferences){

        int oldPoints = getPoints(lektion, sharedPreferences);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Score_" + lektion, 0);
        editor.putInt("Score_All", getPoints(lektion, sharedPreferences) - oldPoints);
        editor.apply();
    }

    public static void increaseCombo(int lektion, SharedPreferences sharedPreferences){

        int currentCombo = sharedPreferences.getInt("Combo_" + lektion, MIN_COMBO);

        if(currentCombo >= MIN_COMBO && currentCombo < MAX_COMBO){
            currentCombo++;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Combo_" + lektion, currentCombo);
        editor.apply();
    }

    public static void decreaseCombo(int lektion, SharedPreferences sharedPreferences){

        int currentCombo = sharedPreferences.getInt("Combo_" + lektion, MIN_COMBO);

        if(currentCombo > MIN_COMBO && currentCombo <= MAX_COMBO){
            currentCombo--;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Combo_" + lektion, currentCombo);
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

    private static int getComboMultiplier(int combo){
        return (int)General.pow(2, combo);
    }

    public static int getCombo(int lektion, SharedPreferences sharedPreferences){
        int combo = sharedPreferences.getInt("Combo_" + lektion, MIN_COMBO);
        return getComboMultiplier(combo);
    }

    public static void resetCombo(int lektion, SharedPreferences sharedPreferences){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("Combo_" + lektion, MIN_COMBO);

        editor.apply();
    }

    public static int getGrade(int pointBaseline, int entryAmount, int lektion, SharedPreferences sharedPreferences){

        int points = getPoints(lektion, sharedPreferences);
        int maxPoints = calculateMaxPossiblePoints(pointBaseline, entryAmount);

        float percentage = (float)points / (float)maxPoints;

        return percentageToGrade(percentage);

    }

    private static int percentageToGrade(float percentage){
        //Currently using this scale:
        //http://www.wzemann.de/physik/page18/files/notenskala.jpg
        //from 0 to 15, 15 being the best possible

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

        return score;

    }
}
