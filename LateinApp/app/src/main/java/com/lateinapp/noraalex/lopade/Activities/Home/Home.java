package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickKasusFragen;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.Satzglieder;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputAdjektive;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputEsseVelleNolle;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputVokabeltrainer;
import com.lateinapp.noraalex.lopade.Activities.Woerterbuch;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_DEV_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_NOT_FIRST_STARTUP;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_vokabeltrainer:

                    return true;
                case R.id.nav_grammatik:
                    return true;
                case R.id.nav_wörterbuch:
                    return true;
            }
            return false;
        }
    };

    private SharedPreferences sharedPref;

    private void setup(){

        //Sets the DEVELOPER modes state according to a variable saved in a previous instance of the app
        //#DEVELOPER
        sharedPref = General.getSharedPrefrences(getApplicationContext());
        DEVELOPER = sharedPref.getBoolean(KEY_DEV_MODE, DEVELOPER);
        DEV_CHEAT_MODE = sharedPref.getBoolean(KEY_DEV_CHEAT_MODE, DEV_CHEAT_MODE);

        //First startup settings
        SharedPreferences sharedPreferences = General.getSharedPrefrences(this);
        boolean notFirstStartup = sharedPreferences.getBoolean(KEY_NOT_FIRST_STARTUP, false);
        if(!notFirstStartup){

            //First setup of DBHelper
            DBHelper dbHelper = new DBHelper(this);
            dbHelper.firstStartup();
            dbHelper.close();

            //First setup of Scores
            Score.firstStartup(sharedPreferences);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_NOT_FIRST_STARTUP, true);
            editor.apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_vokabeltrainer:
                                selectedFragment = HomeVokabeltrainer.newInstance();
                                break;
                            case R.id.nav_grammatik:
                                selectedFragment = HomeGrammatik.newInstance();
                                break;
                            case R.id.nav_wörterbuch:
                                selectedFragment = HomeWoerterbuch.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeVokabeltrainer.newInstance());
        transaction.commit();



        setup();
    }

    public void navButtonClicked(View v) {

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

            case R.id.uebersicht_esse_velle_nolle:
                intent = new Intent(this, UserInputEsseVelleNolle.class);
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
                Log.e(TAG, "The button that called 'uebersichtButtonClicked' was not" +
                        "identified\nID: " + v.getId());
                break;
        }
    }
}
