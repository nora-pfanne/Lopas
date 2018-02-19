package com.example.norablakaj.lateinapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.example.norablakaj.lateinapp.Activities.Home;
import com.example.norablakaj.lateinapp.R;

public class SettingsActivity extends AppCompatActivity {

    Switch toggleDevModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toggleDevModeSwitch = findViewById(R.id.toggleDevModeSwitch);

        toggleDevModeSwitch.setChecked(Home.getDeveloper());
    }

    public void switchPressed(View v){

        if (v.getId() == R.id.toggleDevModeSwitch){
            Home.setDeveloper(toggleDevModeSwitch.isChecked());
        }
    }
}
