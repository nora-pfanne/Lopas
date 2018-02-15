package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.R;

public class GrammatikA extends AppCompatActivity {

    TextView Aufgabenstellung;
    TextView latein;

    Button bestaetigung;

    int lektion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_a);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);


        Aufgabenstellung = findViewById(R.id.textViewAufgabenstellung);
        latein = findViewById(R.id.lateinVokabel);

        bestaetigung = findViewById(R.id.eingabeBestaetigungLektion1);
        bestaetigung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
