package com.example.norablakaj.lateinapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.R;

public class Grammatik1A extends AppCompatActivity {

    TextView Aufgabenstellung;
    TextView latein;

    Button bestaetigung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik1_a);

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
