package com.lateinapp.noraalex.lopade.Activities.newVersion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.ClickDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.ClickKasusFragen;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.ClickPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.Satzglieder;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.UserInputAdjektive;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.UserInputDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.UserInputPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.UserInputVokabeltrainer;
import com.lateinapp.noraalex.lopade.R;

/*
***************************************************************************************************
* The following class is part of the visual overhaul of the app
***************************************************************************************************
*/

public class VisuellEinheitenÜbersicht extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visuell_einheiten_uebersicht);

        setup();
    }

    private void setup(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_visuell_einheiten_uebersicht, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void uebersichtButtonClicked(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.uebersicht_kasus_fragen:

                intent = new Intent(this, ClickKasusFragen.class);
                startActivity(intent);
                break;

            case R.id.uebersicht_nominativ:

                intent = new Intent(this, ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "NOMINATIV");
                startActivity(intent);
                break;

            case R.id.uebersicht_akkusativ:

                intent = new Intent(this, ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "AKKUSATIV");
                startActivity(intent);
                break;

            case R.id.uebersicht_dativ:

                intent = new Intent(this, ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "DATIV");
                startActivity(intent);
                break;

            case R.id.uebersicht_ablativ:

                intent = new Intent(this, ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "ABLATIV");
                startActivity(intent);
                break;

            case R.id.uebersicht_genitiv:

                intent = new Intent(this, ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "GENITIV");
                startActivity(intent);
                break;

            case R.id.uebersicht_checkpoint_deklinationsendung_1:

                intent = new Intent(this, UserInputDeklinationsendung.class);
                intent.putExtra("ExtraInputDeklinationsendung", "ALLE");
                startActivity(intent);

                break;

            case R.id.uebersicht_dritte_pers_praesens:
                intent = new Intent(this, ClickPersonalendung.class);
                intent.putExtra("ExtraClickPersonalendung", "DRITTE_PERSON");
                startActivity(intent);

                break;

            case R.id.uebersicht_erste_zweite_pers_praesens:
                intent = new Intent(this, ClickPersonalendung.class);
                intent.putExtra("ExtraClickPersonalendung", "ERSTE_ZWEITE_PERSON");
                startActivity(intent);

                break;

            case R.id.uebersicht_checkpoint_praesens_1:
                intent = new Intent(this, UserInputPersonalendung.class);
                intent.putExtra("ExtraInputPersonalendung", "DEFAULT");
                startActivity(intent);
                break;

            case R.id.uebersicht_esse_velle_nolle:

                break;

            case R.id.uebersicht_kons_konjugation:

                break;

            case R.id.uebersicht_checkpoint_praesens_2:

                break;

            case R.id.uebersicht_satzglieder:
                intent = new Intent(this, Satzglieder.class);
                startActivity(intent);
                break;

            case R.id.uebersicht_adjektiv_1:
                intent = new Intent(this, UserInputAdjektive.class);
                startActivity(intent);

            case R.id.uebersicht_vokabel_1:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 1);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_2:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 2);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_3:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 3);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_4:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 4);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_5:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 5);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_6:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 6);
                startActivity(intent);
                break;

            default:
                Log.e("ButtonNotFound",
                        "The button that called 'uebersichtButtonClicked' was not" +
                                "identified");
                break;
        }
    }
}
