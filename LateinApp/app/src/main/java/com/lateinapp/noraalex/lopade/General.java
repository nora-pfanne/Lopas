package com.lateinapp.noraalex.lopade;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


public class General {

    private static SharedPreferences sharedPreferences;

    public static void showMessage(String msg, Context context){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(String msg, Context context, int duration){

        Toast.makeText(context, msg, duration).show();
    }

    /**
     * Replaces a set of characterCombinations with a corresponding umlaut each
     * TODO: This would be redundant if we could just import a .csv file with a better charset -> Maybe google sheets??
     * @param s the String to be updated
     * @return the updated String
     */
    public static String replaceWithUmlaut(String s){

        s = s.replace("aeae", "ä");
        s = s.replace("AeAe", "Ä");
        s = s.replace("ueue", "ü");
        s = s.replace("UeUe", "Ü");
        s = s.replace("oeoe", "ö");
        s = s.replace("OeOe", "Ö");
        s = s.replace("sz", "ß");

        /*
       TODO: Implement this
        s = s.replace("-A", "Ā");
        s = s.replace("-a", "ā");
        s = s.replace("-E", "Ē");
        s = s.replace("-e", "ē");
        s = s.replace("-I", "Ī");
        s = s.replace("-i", "ī");
        s = s.replace("-O", "Ō");
        s = s.replace("-o", "ō");
        s = s.replace("-U", "Ū");
        s = s.replace("-u", "ū");

        s = s.replace("#A", "Ă");
        s = s.replace("#a", "ă");
        s = s.replace("#E", "Ĕ");
        s = s.replace("#e", "ĕ");
        s = s.replace("#I", "Ĭ");
        s = s.replace("#i", "ĭ");
        s = s.replace("#O", "Ŏ");
        s = s.replace("#o", "ŏ");
        s = s.replace("#U", "Ŭ");
        s = s.replace("#u", "ŭ");
        */

        return s;
    }

    public static void modifyPoints(int lektion, int pointDifference, SharedPreferences sharedPreferences){

        //TODO: Should points be able to be negative?

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("Points_" + lektion,
                sharedPreferences.getInt("Points_" + lektion, 0) + pointDifference);

        editor.putInt("Points_All",
                sharedPreferences.getInt("Points_All", 0) + pointDifference);

        editor.apply();
    }

    public static int getPoints(int lektion, SharedPreferences sharedPreferences){
        return sharedPreferences.getInt("Points_" + lektion, 0);
    }

    public static int getPoints(SharedPreferences sharedPreferences){
        return sharedPreferences.getInt("Points_All", 0);
    }

    public static SharedPreferences getSharedPrefrences(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }

}
