package com.lateinapp.noraalex.lopade.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickKasusFragen;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.Satzglieder;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputAdjektive;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputVokabeltrainer;
import com.lateinapp.noraalex.lopade.R;

public class EinheitenUebersicht extends LateinAppActivity {


    //#DEVELOPER
    public static boolean DEVELOPER = true;
    public static boolean DEV_CHEAT_MODE = true;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visuell_einheiten_uebersicht);

        setup();
    }

    private void setup(){

        //Sets the DEVELOPER state according to a variable saved in a previous instance of the app
        //#DEVELOPER
        sharedPref = getSharedPreferences("SharedPreferences", 0);
        DEVELOPER = sharedPref.getBoolean("DEVELOPER", DEVELOPER);
        DEV_CHEAT_MODE = sharedPref.getBoolean("DEV_CHEAT_MODE", DEV_CHEAT_MODE);
    }

    public void uebersichtButtonClicked(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.uebersicht_woerterbuch:
                intent = new Intent(this, Woerterbuch.class);
                startActivity(intent);
                break;

            case R.id.uebersicht_vokabel_1:
                intent = new Intent(this, UserInputVokabeltrainer.class);
                intent.putExtra("lektion",1);
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

            case R.id.uebersicht_satzglieder:
                intent = new Intent(this, Satzglieder.class);
                startActivity(intent);
                break;

            case R.id.uebersicht_adjektiv_1:
                intent = new Intent(this, UserInputAdjektive.class);
                startActivity(intent);


            default:
                Log.e("ButtonNotFound",
                        "The button that called 'uebersichtButtonClicked' was not" +
                                "identified");
                break;
        }
    }
}
