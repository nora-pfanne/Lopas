package com.lateinapp.noraalex.lopade;

import android.content.Context;
import android.widget.Toast;

public class General {

    public static void showMessage(String msg, Context context){

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(String msg, Context context, int duration){

        Toast.makeText(context, msg, duration).show();
    }
}
