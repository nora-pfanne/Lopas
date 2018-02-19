package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Activities.Grammatikeinheiten.GrammatikDeklination;
import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.R;

public class LektionUebersicht extends DevActivity {

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
            grammarPartA(lektion);
        }
        if(view.getId() == R.id.buttonB){
            grammarPartB(lektion);
        }
        if (view.getId() == R.id.buttonC) {
            grammarPartC(lektion);
        }
    }

    private void grammarPartA(int lektion){

        switch (lektion){

            case 1:
                Intent intent1 = new Intent(getApplicationContext(), GrammatikDeklination.class);
                intent1.putExtra("lektion",lektion);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent(getApplicationContext(), GrammatikDeklination.class);
                intent2.putExtra("lektion",lektion);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(getApplicationContext(), GrammatikDeklination.class);
                intent3.putExtra("lektion",lektion);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent(getApplicationContext(), GrammatikDeklination.class);
                intent4.putExtra("lektion",lektion);
                startActivity(intent4);
                break;

            case 5:
                Intent intent5 = new Intent(getApplicationContext(), GrammatikDeklination.class);
                intent5.putExtra("lektion",lektion);
                startActivity(intent5);
                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'A' was passed");
        }

    }

    private void grammarPartB(int lektion){

        switch (lektion){

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'B' was passed");
        }

    }

    private void grammarPartC(int lektion){

        switch (lektion){

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'C' was passed");
        }

    }
}
