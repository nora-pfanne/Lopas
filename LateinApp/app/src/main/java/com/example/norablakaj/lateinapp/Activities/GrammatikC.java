package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.norablakaj.lateinapp.R;

public class GrammatikC extends AppCompatActivity {

    int lektion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_c);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);
    }
}
