package com.lateinapp.noraalex.lopade.Activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.General;

import java.util.Map;

public class DevSharedPrefManager extends LateinAppActivity{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ScrollView scrollView = new ScrollView(this);

        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        linLayout.setBackgroundColor(Color.WHITE);
        linLayout.setScrollContainer(true);
        scrollView.addView(linLayout);

        //all required layouts are created dynamically and added to the main scrollview
        setContentView(scrollView );


        SharedPreferences sharedPref = General.getSharedPrefrences(this);
        Map<String, ?> spContent = sharedPref.getAll();

        LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(0, 150);
        for(Map.Entry<String, ?> entry : spContent.entrySet()) {
            final LinearLayout row = new LinearLayout(DevSharedPrefManager.this);
            row.setPadding(0, 5, 0, 5);
            rowLp.weight = 1;

            TextView maintext = new TextView(DevSharedPrefManager.this);
            maintext.setText(entry.getKey() + "\n                        " + entry.getValue());
            maintext.setTextSize(22);
            maintext.setLayoutParams(rowLp);
            row.addView(maintext);

            if (linLayout.getChildCount() % 2 != 0) {
                row.setBackgroundColor(Color.GRAY);
            } else {
                row.setBackgroundColor(Color.WHITE);
            }

            linLayout.addView(row);
        }

    }
}
