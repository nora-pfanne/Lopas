package com.lateinapp.noraalex.lopade;

public class Global {

    public static final int LEKTION_COUNT = 6;

    //#DEVELOPER
    public static boolean DEVELOPER = true;
    public static boolean DEV_CHEAT_MODE = true;

    //
    //Public keys for accessing SharedPreferences values;
    //

    //Saves where in which fragment of the home screen the user last was
    public static final String KEY_HOME_FRAGMENT = "LAST_HOME_FRAGMENT";                                            //Integer
    public static final int STATE_FRAGMENT_VOCABULARY =  0;
    public static final int STATE_FRAGMENT_GRAMMAR =     1;
    public static final int STATE_FRAGMENT_WOERTERBUCH = 2;

    //#DEVELOPER: the values indicate, whether the corresponding app mode was activated the last time the app was running
    public static final String KEY_DEV_MODE =       "DEVELOPER";                                                    //Boolean
    public static final String KEY_DEV_CHEAT_MODE = "DEV_CHEAT_MODE";                                               //Boolean

    public static final String KEY_NOT_FIRST_STARTUP = "NotFirstStartup";

    public static final String KEY_PROGRESS_CLICK_KASUSFRAGEN =        "Progress_Click_KasusFragen";                //Integer
    public static final String KEY_PROGRESS_SATZGLIEDER =              "Progress_Satzglieder";                      //Integer
    public static final String KEY_PROGRESS_USERINPUT_ADJEKTIVE =      "Progress_UserInput_Adjektive";              //Integer
    public static final String KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE = "Progress_UserInput_EsseVelleNolle";         //Integer



    //@SCORE_CLEANUP

    //The trainers corresponding the SharedPrefrences values below have different modi. Append a number to the key to excess them.
    //Example: Progress_UserInput_Deklinationsendung_5

    public static final String KEY_FINISHED_USERINPUT_VOKABELTRAINER =     "Progress_UserInput_Vokabeltrainer_";     //Integer
    public static final String KEY_PROGRESS_USERINPUT_DEKLINATIONSENDUNG = "Progress_UserInput_Deklinationsendung_"; //Integer
    public static final String KEY_PROGRESS_USERINPUT_PERSONALENDUNG =     "Progress_UserInput_Personalendung_";     //Integer
    public static final String KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG =     "Progress_Click_Deklinationsendung_";     //Integer
    public static final String KEY_PROGRESS_CLICK_PERSONALENDUNG =         "Progress_Click_Personalendung_";         //Integer

    //Example: High_Score_Vocabulary_Trainer_3
    public static final String KEY_SCORE_VOCAULARY =      "Score_Vocabulary_Trainer_";                               //Integer
    public static final String KEY_HIGH_SCORE_VOCAULARY = "High_Score_Vocabulary_Trainer_";                          //Integer
    public static final String KEY_COMBO_VOCABULARY =     "High_Score_Vocabulary_Combo_";                            //Integer

    //States that describe if this iteration of the trainer
    // -Is still the first playthrough of the trainer
    // -Did not achieve a new highscore yet
    // -Did just achieve a new highscore -> popup NOW
    // -Did achieve a new highscore at an earlier point
    //
    //We need this information for a "new Highscore" popup that should be shows ONCE per run
    //whenever a new highscore is achieved.
    public static final int STATE_FIRST_PLAYTHROUGH = 0;
    public static final int STATE_NO_HS_YET         = 1;
    public static final int STATE_NEW_HS_NOW        = 2;
    public static final int STATE_NEW_HS_EARLIER    = 3;
    public static final String KEY_NEW_HIGHSCORE_VOKABELTRAINER = "New_Highscore_Vocabulary_";                       //Integer -> one of the above

    public static final String KEY_HIGH_SCORE_ALL_TRAINERS = "High_Score_All_Trainers";                              //Integer





    public static final String KEY_LOWEST_MISTAKE_AMOUNT_VOC = "Lowest_Mistakes_in_lektion_";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_PERSONALENDUNG_CLICK = "Lowest_Mistakes_Personalendung_Click";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_PERSONALENDUNG_CLICK = "Current_Mistakes_Personalendung_Click";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_PERSONALENDUNG_INPUT = "Lowest_Mistakes_Personalendung_Input";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_PERSONALENDUNG_INPUT = "Current_Mistakes_Personalendung_Input";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_DEKLINATIONSENDUNG_CLICK = "Lowest_Mistakes_Deklinationsendung_Click";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_DEKLINATIONSENDUNG_CLICK = "Current_Mistakes_Deklinationsendung_Click";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_DEKLINATIONSENDUNG_INPUT = "Lowest_Mistakes_Deklinationsendung_Input";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_DEKLINATIONSENDUNG_INPUT = "Current_Mistakes_Deklinationsendung_Input";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_KASUS = "Lowest_Mistakes_Kasus";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_KASUS = "Current_Mistakes_Kasus";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_ESSE_VELLE_NOLLE = "Lowest_Mistakes_esse_velle_nolle";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_ESSE_VELLE_NOLLE = "Current_Mistakes_esse_velle_nolle";

    public static final String KEY_LOWEST_MISTAKE_AMOUNT_SATZGLIEDER = "Lowest_Mistakes_Satzglieder";
    public static final String KEY_CURRENT_MISTAKE_AMOUNT_SATZGLIEDER = "Current_Mistakes_Satzglieder";

    public static final int POINT_BASELINE = 100;

}
