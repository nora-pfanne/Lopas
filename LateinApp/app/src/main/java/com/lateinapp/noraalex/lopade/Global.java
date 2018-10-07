package com.lateinapp.noraalex.lopade;

public class Global {

    //#DEVELOPER
    public static boolean DEVELOPER = true;
    public static boolean DEV_CHEAT_MODE = true;

    //
    //Public keys for accessing SharedPreferences values;
    //

    //#DEVELOPER: the values indicate, whether the corresponding app mode was activated the last time the app was running
    public static final String KEY_DEV_MODE = "DEVELOPER";                                                          //Boolean
    public static final String KEY_DEV_CHEAT_MODE = "DEV_CHEAT_MODE";                                               //Boolean

    public static final String KEY_NOT_FIRST_STARTUP = "NotFirstStartup";

    public static final String KEY_PROGRESS_CLICK_KASUSFRAGEN = "Progress_Click_KasusFragen";                       //Integer
    public static final String KEY_PROGRESS_SATZGLIEDER = "Progress_Satzglieder";                                   //Integer
    public static final String KEY_PROGRESS_USERINPUT_ADJEKTIVE = "Progress_UserInput_Adjektive";                   //Integer
    public static final String KEY_PROGRESS_USERINPUT_ESSEVELLENOLLE = "Progress_UserInput_EsseVelleNolle";         //Integer


    //The trainers corresponding the SharedPrefrences values below have different modi. Append a number to the key to excess them.
    //Example: Progress_UserInput_Deklinationsendung_5

    public static final String KEY_FINISHED_USERINPUT_VOKABELTRAINER = "Progress_UserInput_Vokabeltrainer_";         //Integer
    public static final String KEY_PROGRESS_USERINPUT_DEKLINATIONSENDUNG = "Progress_UserInput_Deklinationsendung_"; //Integer
    public static final String KEY_PROGRESS_USERINPUT_PERSONALENDUNG = "Progress_UserInput_Personalendung_";         //Integer
    public static final String KEY_PROGRESS_CLICK_DEKLINATIONSENDUNG = "Progress_Click_Deklinationsendung_";         //Integer
    public static final String KEY_PROGRESS_CLICK_PERSONALENDUNG = "Progress_Click_Personalendung_";                 //Integer

    //Example: High_Score_Vocabulary_Trainer_3
    public static final String KEY_SCORE_VOCAULARY = "High_Score_Vocabulary_Trainer_";                          //Integer
    public static final String KEY_HIGH_SCORE_VOCAULARY = "High_Score_Vocabulary_Trainer_";                          //Integer
    public static final String KEY_HIGH_COMBO_VOCAULARY = "High_Score_Vocabulary_Combo_";                            //Integer


    public static final String KEY_HIGH_SCORE_ALL_TRAINERS = "High_Score_All_Trainers";                              //Integer

}
