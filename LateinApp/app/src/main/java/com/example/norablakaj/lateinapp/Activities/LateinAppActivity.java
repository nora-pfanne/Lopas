package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.norablakaj.lateinapp.R;

//TODO: Find a better name for this

//TODO: Use Fragments
public abstract class LateinAppActivity extends AppCompatActivity{

    private boolean onPause = false;

    private Menu menu;
    private MenuItem devDBHelper,
                    devVokCheat;

    //Make this accessible to all subclasses.
    public SharedPreferences sharedPref;
    //Add a for all subclasses accessible DBHelper
    //This DBHelper should automatically initalize in onCreate() and close()/closeDB() in onDestroy()

    /**
     * inflate your menu resource (defined in XML) into the Menu
     * @param menu the menu object
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_button, menu);

        this.menu = menu;
        devDBHelper = this.menu.findItem(R.id.action_dev_DB_Helper);
        devVokCheat = this.menu.findItem(R.id.action_dev_Vokabeltrainer_Cheat);

        sharedPref = getSharedPreferences("SharedPreferences", 0);

        adjustSettings();

        return true;
    }

    /**
     * This is called whenever an item in the options menu is selected
     * @param item The item selected by the user
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            //Opening the settings-activity
            case (R.id.action_settings):
                Intent settingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;

            //#DEVELOPER
            //Opening the dbManager-activity
            case (R.id.action_dev_DB_Helper):
                Intent dbManager = new Intent(this, AndroidDatabaseManager.class);
                startActivity(dbManager);
                break;

            //#DEVELOPER
            //toggles the DevCheatMode
            case (R.id.action_dev_Vokabeltrainer_Cheat):
                SharedPreferences.Editor editor = sharedPref.edit();
                Home.setDEV_CHEAT_MODE(!Home.isDEV_CHEAT_MODE());
                editor.putBoolean("DEV_CHEAT_MODE", Home.isDEV_CHEAT_MODE());
                editor.apply();
                adjustSettings();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called as part of the activity lifecycle when an activity is going into the background,
     * but has not (yet) been killed.
     */
    @Override
    protected void onPause() {
        super.onPause();

        onPause = true;
    }

    /**
     * called as part of the activity lifecycle when an activity is going back into the foreground
     * after it had been paused [->onPause()]
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (onPause){
            onPause = false;

            adjustSettings();
        }
    }

    private void adjustSettings(){

        //#DEVELOPER
        if (Home.isDEVELOPER()) {
            devDBHelper.setVisible(true);
            devVokCheat.setVisible(true);

            //Setting the text of the
            String temp;
            if (Home.isDEV_CHEAT_MODE()){
                temp = "ON";
            }else{
                temp = "OFF";
            }
            devVokCheat.setTitle("DEV: Cheat-Mode: " + temp);

        }else {
            devDBHelper.setVisible(false);
            devVokCheat.setVisible(false);
        }

    }

}
