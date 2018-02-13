package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.norablakaj.lateinapp.R;

public class LektionUebersicht extends AppCompatActivity {

    int lektion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lektion_uebersicht);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

    }

    public void buttonClicked (View view){

        if(view.getId() == R.id.openVok){
            Intent startVokabeltrainer = new Intent(view.getContext(), Vokabeltrainer.class);
            startVokabeltrainer.putExtra("lektion", lektion);
            startActivity(startVokabeltrainer);
        }
        if(view.getId() == R.id.buttonA){
            Intent startGrammatikA = new Intent (view.getContext(), GrammatikA.class);
            startGrammatikA.putExtra("lektion", lektion);
            startActivity(startGrammatikA);
        }
        if(view.getId() == R.id.buttonB){
            Intent startGrammatikB = new Intent(view.getContext(), GrammatikB.class);
            startGrammatikB.putExtra("lektion", lektion);
            startActivity(startGrammatikB);
        }
        if (view.getId() == R.id.buttonC) {
            Intent startGrammatikC = new Intent(view.getContext(),GrammatikC.class);
            startGrammatikC.putExtra("lektion", lektion);
            startActivity(startGrammatikC);
        }
    }
}
