package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.norablakaj.lateinapp.R;

public class Lektion1 extends AppCompatActivity {

    Button vokabeltrainerLektion1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lektion1);

        //Defining the buttons that belong to Lektion1
        vokabeltrainerLektion1Button = findViewById(R.id.vokabeltrainerLektion1Button);

    }

    public void buttonClicked (View view){

        if(view.getId() == R.id.vokabeltrainerLektion1Button){
            Intent startVokabeltrainerLektion1 = new Intent(view.getContext(), Vokabeltrainer_Lektion_1.class);
            startActivity(startVokabeltrainerLektion1);
        }
        if(view.getId() == R.id.button1A){
            Intent startGrammatik1A = new Intent (view.getContext(), Grammatik1A.class);
            startActivity(startGrammatik1A);
        }
        if(view.getId() == R.id.button1B){
            Intent startGrammatik1B = new Intent(view.getContext(), Grammatik1B.class);
            startActivity(startGrammatik1B);
        }
        if (view.getId() == R.id.button1C) {
            Intent startGrammatik1C = new Intent(view.getContext(),Grammatik1C.class);
            startActivity(startGrammatik1C);
        }
    }
}
