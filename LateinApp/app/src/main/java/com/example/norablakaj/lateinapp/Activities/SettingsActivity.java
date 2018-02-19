package com.example.norablakaj.lateinapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.norablakaj.lateinapp.R;

public class SettingsActivity extends DevActivity {

    Switch toggleDevModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toggleDevModeSwitch = findViewById(R.id.toggleDevModeSwitch);

        toggleDevModeSwitch.setChecked(Home.DEVELOPER);
    }

    public void switchPressed(View v){

        if (v.getId() == R.id.toggleDevModeSwitch){
            Home.DEVELOPER = (toggleDevModeSwitch.isChecked());
        }
    }
}
