package com.lateinapp.noraalex.lopade.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;

import static com.lateinapp.noraalex.lopade.Global.DEVELOPER;
import static com.lateinapp.noraalex.lopade.Global.DEV_CHEAT_MODE;

public abstract class LateinAppActivity extends AppCompatActivity{

    private static final String TAG = "LateinAppActivity";

    private boolean onPause = false;

    private Menu menu;
    private MenuItem devDBHelper,
                    devVokCheat,
                    devReloadDatabaseAssets,
                    devReloadDatabaseIterative;

    //Make this accessible to all subclasses.
    protected SharedPreferences sharedPref;
    //Add a for all subclasses accessible DBHelper
    //This DBHelper should automatically initalize in onCreate() and close()/closeDB() in onDestroy()


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * inflate your menu resource (defined in XML) into the Menu
     * @param menu the menu object
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_button, menu);

        //If we aren't in the home activity we want to enable a back/up navigation button
        if (!this.getClass().getName().equals(EinheitenUebersicht.class.getName())){
            ActionBar ab = getSupportActionBar();
            // Enable the Up button
            if (ab != null) ab.setDisplayHomeAsUpEnabled(true);
        }

        //If the Activity is in the package "Einheiten" we want to display the info-slide box
        if (getClass().getPackage().getName().contains("Einheiten")){
            getMenuInflater().inflate(R.menu.info_button, menu);
        }

        this.menu = menu;
        devDBHelper = this.menu.findItem(R.id.action_dev_DB_Helper);
        devVokCheat = this.menu.findItem(R.id.action_dev_Vokabeltrainer_Cheat);
        devReloadDatabaseAssets = this.menu.findItem(R.id.action_dev_reload_database_from_assets);
        devReloadDatabaseIterative = this.menu.findItem(R.id.action_dev_reload_database_iterative);


        sharedPref = General.getSharedPrefrences(getApplicationContext());

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


            //Opening a popup window
            case (R.id.action_info):

                openInfoPopup();
                break;

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
                DEV_CHEAT_MODE = !DEV_CHEAT_MODE;
                editor.putBoolean("DEV_CHEAT_MODE", DEV_CHEAT_MODE);
                editor.apply();
                adjustSettings();

                General.showMessage("Cheat Mode " + (DEV_CHEAT_MODE ? "Activated" : "Deactivated"), getApplicationContext());
                break;

            //#DEVELOPER
            //Reloading the database -> copy from assets
            case (R.id.action_dev_reload_database_from_assets):
                DBHelper dbHelper = new DBHelper(getApplicationContext());

                dbHelper.copyDataBaseFromAssets();

                dbHelper.close();
                break;

            case (R.id.action_dev_reload_database_iterative):
                DBHelper dbHelper1 = new DBHelper(getApplicationContext());
                dbHelper1.fillDatabaseFromCsv();
                dbHelper1.close();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Opens a PopUp window with a description of what the user should do in this class
     * Will only be called in classes in the package "Einheiten"
     *
     * This method should never be called but only the corresponding overriden method in the
     * subclasses.
     * This only opens a placeholder PopUp
     */
    public void openInfoPopup(){

        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView;

        popupView = layoutInflater.inflate(R.layout.popup_info_default, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        Button btnDismiss = popupView.findViewById(R.id.popup_info_default_dismiss);
        btnDismiss.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0,0);
    }

    /**
     * Called as part of the activity lifecycle when an activity is going into the background,
     * but has not (yet) been killed.
     */
    @Override
    public void onPause() {
        super.onPause();

        onPause = true;
    }

    /**
     * called as part of the activity lifecycle when an activity is going back into the foreground
     * after it had been paused [->onPause()]
     */
    @Override
    public void onResume() {
        super.onResume();

        if (onPause){
            onPause = false;

            adjustSettings();
        }
    }

    private void adjustSettings(){

        //FIXME: Why does this sometimes crash with "couldnt invoke method on null object"?
        try {
            //#DEVELOPER
            if (DEVELOPER) {
                devDBHelper.setVisible(true);
                devVokCheat.setVisible(true);
                devReloadDatabaseAssets.setVisible(true);
                devReloadDatabaseIterative.setVisible(true);

                devVokCheat.setTitle("DEV: Cheat-Mode: " + (DEV_CHEAT_MODE ? "ON" : "OFF"));

            } else {
                devDBHelper.setVisible(false);
                devVokCheat.setVisible(false);
                devReloadDatabaseAssets.setVisible(false);
                devReloadDatabaseIterative.setVisible(false);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void hideKeyboard(){
        try {
            //Hiding the Keyboard.
            View v = getWindow().getDecorView().getRootView();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
    }

    public void showKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }


    public void animationFadeOut(View view){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        view.startAnimation(anim);
    }

    public void animationFadeIn(View view){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(anim);
    }

    public void animationMove(View view){
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        view.startAnimation(anim);
    }
}
