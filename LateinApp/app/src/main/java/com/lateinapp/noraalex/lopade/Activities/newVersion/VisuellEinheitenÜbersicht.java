package com.lateinapp.noraalex.lopade.Activities.newVersion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.ClickDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten.ClickKasusFragen;
import com.lateinapp.noraalex.lopade.Activities.oldVersion.LektionUebersicht;
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

            case R.id.uebersicht_infinitiv:

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

                break;

            case R.id.uebersicht_dritte_pers_praesens:

                break;

            case R.id.uebersicht_erste_zweite_pers_praesens:

                break;

            case R.id.uebersicht_checkpoint_praesens_1:

                break;

            case R.id.uebersicht_esse_velle_nolle:

                break;

            case R.id.uebersicht_kons_konjugation:

                break;

            case R.id.uebersicht_checkpoint_praesens_2:

                break;

            default:
                Log.e("ButtonNotFound",
                        "The button that called 'uebersichtButtonClicked' was not" +
                                "identified");
                break;
        }
    }
}
