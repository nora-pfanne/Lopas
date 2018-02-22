package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.norablakaj.lateinapp.Activities.Einheiten.Vokabeltrainer;
import com.example.norablakaj.lateinapp.R;

//TODO: Find a better name for this

//TODO: Use Fragments
public abstract class DevActivity extends AppCompatActivity{

    private boolean onPause = false;

    private Menu menu;
    private MenuItem devDBHelper,
                    devVokCheat;
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

            //Opening the dbManager-activity
            case (R.id.action_dev_DB_Helper):
                Intent dbManager = new Intent(this, AndroidDatabaseManager.class);
                startActivity(dbManager);
                break;

            //toggles the DevCheatMode
            case (R.id.action_dev_Vokabeltrainer_Cheat):
                Home.setDevCheatMode(!Home.isDevCheatMode());
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

        if (Home.isDEVELOPER()) {
            devDBHelper.setVisible(true);
            devVokCheat.setVisible(true);

            //Setting the text of the
            String temp;
            if (Home.isDevCheatMode()){
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
