package com.lateinapp.noraalex.lopade;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
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

    public static SharedPreferences getSharedPrefrences(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        }

        return sharedPreferences;
    }


    //https://en.wikipedia.org/wiki/Exponentiation_by_squaring
    //More efficient pow algorithm with O(log n) instead of O(n)
    public static float pow(float base, int exp){

        if (exp < 0) {
            base = 1 / base;
            exp *= -1;
        }else if(exp == 0){
            return 1;
        }

        int y = 1;

        while (exp > 1) {
            if(exp % 2 == 0){
                base *= base;
                exp /= 2;
            }else{
                y *= base;
                base *= base;
                exp = (exp - 1)/2;
            }
        }
        return base * y;

    }
}
