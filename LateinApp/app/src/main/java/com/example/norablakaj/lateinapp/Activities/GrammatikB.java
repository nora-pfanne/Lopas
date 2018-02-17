package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.norablakaj.lateinapp.R;

public class GrammatikB extends AppCompatActivity {

    int lektion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_b);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);
    }
}
