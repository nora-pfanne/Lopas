package com.lateinapp.noraalex.lopade.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.Einheiten.GrammatikDeklinationErmitteln;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.GrammatikDekliniereVokabel;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.GrammatikPersonalendungErmitteln;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.Vokabeltrainer;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.LektionDB;
import com.lateinapp.noraalex.lopade.R;

public class LektionUebersicht extends LateinAppActivity {

    private int lektion;

    private DBHelper dbHelper;
    private SharedPreferences sharedPref;

    private TextView lektionsUeberschrift;
    private TextView lektionsText;

    private ProgressBar progressBarVok;
    private ProgressBar progressBarA;
    private ProgressBar progressBarB;
    private ProgressBar progressBarC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lektion_uebersicht);

        Intent intent = getIntent();
        lektion = intent.getIntExtra("lektion",0);

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        dbHelper = new DBHelper(getApplicationContext());

        lektionsUeberschrift = findViewById(R.id.textLektionsübersicht);
        lektionsText = findViewById(R.id.textLektionsbeschreibung);
        progressBarVok = findViewById(R.id.progressBarÜbersichtVokTrainer);
        progressBarA = findViewById(R.id.progressBarÜbersichtGrammarA);
        progressBarB = findViewById(R.id.progressBarÜbersichtGrammarB);
        progressBarC = findViewById(R.id.progressBarÜbersichtGrammarC);

        lektionsUeberschrift.setText(dbHelper.getColumnFromId(lektion,
                                                              LektionDB.FeedEntry.TABLE_NAME,
                                                              LektionDB.FeedEntry.COLUMN_TITEL));
        lektionsText.setText(dbHelper.getColumnFromId(lektion,
                                                      LektionDB.FeedEntry.TABLE_NAME,
                                                      LektionDB.FeedEntry.COLUMN_THEMA));


        /*
        //TODO: Add this for deployment until it is finished. For early pushing to user only
        TextView textC = findViewById(R.id.textÜbersichtGrammarC);
        textC.setVisibility(View.GONE);
        progressBarC.setVisibility(View.GONE);
        */

        adjustProgressBars();
    }

    private void adjustProgressBars(){
        //TODO: Setting this is acually more complicated as not every lektion has A->Deklination and so on

        progressBarVok.setMax(100);
        progressBarVok.setProgress((int)(dbHelper.getGelerntProzent(lektion)*100));

        progressBarA.setMax(20);

        int completedA = sharedPref.getInt("Deklination"+lektion, 0);
        progressBarA.setProgress(completedA);

        progressBarB.setMax(20);
        int completedB = sharedPref.getInt("Personalendung"+lektion, 0);
        progressBarB.setProgress(completedB);

        progressBarC.setMax(20);
        progressBarC.setProgress(0);

    }

    public void übersichtButtonClicked (View view){


        switch (view.getId()) {

            case (R.id.progressBarÜbersichtVokTrainer):
                Intent startVokabeltrainer = new Intent(view.getContext(), Vokabeltrainer.class);
                startVokabeltrainer.putExtra("lektion", lektion);
                startActivity(startVokabeltrainer);
                break;

            case (R.id.progressBarÜbersichtGrammarA):
                grammarPartA(lektion);
                break;

            case (R.id.progressBarÜbersichtGrammarB):
                grammarPartB(lektion);
                break;

            case (R.id.progressBarÜbersichtGrammarC):
                grammarPartC(lektion);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        adjustProgressBars();
    }

    private void grammarPartA(int lektion){

        switch (lektion){

            case 1:
                Intent intent1 = new Intent(getApplicationContext(), GrammatikDeklinationErmitteln.class);
                intent1.putExtra("lektion",lektion);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent(getApplicationContext(), GrammatikDeklinationErmitteln.class);
                intent2.putExtra("lektion",lektion);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(getApplicationContext(), GrammatikDeklinationErmitteln.class);
                intent3.putExtra("lektion",lektion);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent(getApplicationContext(), GrammatikDeklinationErmitteln.class);
                intent4.putExtra("lektion",lektion);
                startActivity(intent4);
                break;

            case 5:
                Intent intent5 = new Intent(getApplicationContext(), GrammatikDeklinationErmitteln.class);
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
                Intent intent1 = new Intent(getApplicationContext(), GrammatikPersonalendungErmitteln.class);
                intent1.putExtra("lektion",lektion);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent(getApplicationContext(), GrammatikPersonalendungErmitteln.class);
                intent2.putExtra("lektion",lektion);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(getApplicationContext(), GrammatikPersonalendungErmitteln.class);
                intent3.putExtra("lektion",lektion);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent(getApplicationContext(), GrammatikPersonalendungErmitteln.class);
                intent4.putExtra("lektion",lektion);
                startActivity(intent4);
                break;

            case 5:
                Intent intent5 = new Intent(getApplicationContext(), GrammatikPersonalendungErmitteln.class);
                intent5.putExtra("lektion",lektion);
                startActivity(intent5);
                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'B' was passed");
        }

    }

    private void grammarPartC(int lektion){

        switch (lektion){

            case 1:
                Intent intent1 = new Intent(getApplicationContext(), GrammatikDekliniereVokabel.class);
                intent1.putExtra("lektion",lektion);
                startActivity(intent1);
                break;

            case 2:
                Intent intent2 = new Intent(getApplicationContext(), GrammatikDekliniereVokabel.class);
                intent2.putExtra("lektion",lektion);
                startActivity(intent2);
                break;

            case 3:
                Intent intent3 = new Intent(getApplicationContext(), GrammatikDekliniereVokabel.class);
                intent3.putExtra("lektion",lektion);
                startActivity(intent3);
                break;

            case 4:
                Intent intent4 = new Intent(getApplicationContext(), GrammatikDekliniereVokabel.class);
                intent4.putExtra("lektion",lektion);
                startActivity(intent4);
                break;

            case 5:
                Intent intent5 = new Intent(getApplicationContext(), GrammatikDekliniereVokabel.class);
                intent5.putExtra("lektion",lektion);
                startActivity(intent5);
                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'C' was passed");
        }

    }
}
