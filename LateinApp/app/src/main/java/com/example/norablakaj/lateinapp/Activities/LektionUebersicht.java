package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.GrammatikManager;
import com.example.norablakaj.lateinapp.R;

public class LektionUebersicht extends AppCompatActivity {

    int lektion;

    DBHelper dbHelper;

    TextView lektionsUeberschrift;
    TextView lektionsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lektion_uebersicht);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        dbHelper = new DBHelper(getApplicationContext());

        lektionsUeberschrift = findViewById(R.id.lektionsueberschrift);
        lektionsText = findViewById(R.id.lektionsText);

        lektionsUeberschrift.setText(dbHelper.getLektionsUeberschrift(lektion));
        lektionsText.setText(dbHelper.getLektionsText(lektion));
    }

    public void buttonClicked (View view){

        if(view.getId() == R.id.openVok){
            Intent startVokabeltrainer = new Intent(view.getContext(), Vokabeltrainer.class);
            startVokabeltrainer.putExtra("lektion", lektion);
            startActivity(startVokabeltrainer);
        }
        if(view.getId() == R.id.buttonA){
            GrammatikManager grammatikManager = new GrammatikManager('A', lektion);
        }
        if(view.getId() == R.id.buttonB){
            GrammatikManager grammatikManager = new GrammatikManager('B', lektion);
        }
        if (view.getId() == R.id.buttonC) {
            GrammatikManager grammatikManager = new GrammatikManager('C', lektion);
        }
    }
}
