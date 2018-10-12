package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

import com.lateinapp.noraalex.lopade.Activities.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_DEV_CHEAT_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_DEV_MODE;
import static com.lateinapp.noraalex.lopade.Global.KEY_NOT_FIRST_STARTUP;

public class Home extends LateinAppActivity {

    private static final String TAG = "Home";
    private static final String FRAGMENT_VOCABULARY = "Vocabulary";
    private static final String FRAGMENT_GRAMMAR = "Grammar";
    private static final String FRAGMENT_WOERTERBUCH = "Woerterbuch";

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        String fragmentTag = "";

                        switch (item.getItemId()) {
                            case R.id.nav_vokabeltrainer:
                                selectedFragment = HomeVokabeltrainer.newInstance();
                                fragmentTag = FRAGMENT_VOCABULARY;
                                break;
                            case R.id.nav_grammatik:
                                selectedFragment = HomeGrammatik.newInstance();
                                fragmentTag = FRAGMENT_GRAMMAR;
                                break;
                            case R.id.nav_wörterbuch:
                                selectedFragment = HomeWoerterbuch.newInstance();
                                fragmentTag = FRAGMENT_WOERTERBUCH;
                                break;
                        }

                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment, fragmentTag);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeVokabeltrainer.newInstance(), FRAGMENT_VOCABULARY);
        transaction.commit();



        setup();
    }

    public void navButtonClicked(View v) {

        HomeGrammatik fragGramm = (HomeGrammatik)getSupportFragmentManager().findFragmentByTag(FRAGMENT_GRAMMAR);
        if(fragGramm != null){
            fragGramm.navButtonClicked(v);
            return;
        }

        HomeVokabeltrainer fragVoc = (HomeVokabeltrainer)getSupportFragmentManager().findFragmentByTag(FRAGMENT_VOCABULARY);
        if(fragVoc != null){
            fragVoc.navButtonClicked(v);
            return;
        }

        HomeWoerterbuch fragWoert = (HomeWoerterbuch)getSupportFragmentManager().findFragmentByTag(FRAGMENT_WOERTERBUCH);
        if(fragWoert != null){
            fragWoert.navButtonClicked(v);
            return;
        }
    }
}
