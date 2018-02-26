package com.example.norablakaj.lateinapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.norablakaj.lateinapp.R;

public class Home extends DevActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //#DEVELOPER
    private static boolean DEVELOPER = true;
    private static boolean DEV_CHEAT_MODE = false;

    private SharedPreferences sharedPref;

    /**
     * Creating drawer
     *
     * @param savedInstanceState Used for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Sets the DEVELOPER state according to a variable saved in a previous instance of the app
        //#DEVELOPER
        sharedPref = getSharedPreferences("SharedPreferences", 0);
        DEVELOPER = sharedPref.getBoolean("DEVELOPER", false);
        DEV_CHEAT_MODE = sharedPref.getBoolean("DEV_CHEAT_MODE", false);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handling what happens when the user clicks on a item in the side-drawer.
     * @param item The item selected by the user
     * @return true
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()){

            //Opening the activity 'Woerterbuch'

            case (R.id.nav_wörterbuch):
                Intent openWörterbuch = new Intent(this, Woerterbuch.class);
                startActivity(openWörterbuch);
                break;

                default:
                    Log.e("NavigationItemNotFound", "The selected navigation item was not found.\n" +
                            "id: " + item.getItemId() + ".");
                    break;
        }

        //Closing the drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Handles the button presses
     * @param view Contains information about which Button was pressed
     */
    public void homeButtonClicked(View view){

        Intent startLektionUebersicht = new Intent(view.getContext(), LektionUebersicht.class);

        //passing over which 'lektion' was selected depending on the button clicked
        switch (view.getId()){

            case (R.id.buttonOpenLektion1):
                startLektionUebersicht.putExtra("lektion",1);
                break;

            case (R.id.buttonOpenLektion2):
                startLektionUebersicht.putExtra("lektion", 2);
                break;

            case (R.id.buttonOpenLektion3):
                startLektionUebersicht.putExtra("lektion", 3);
                break;

            case (R.id.buttonOpenLektion4):
                startLektionUebersicht.putExtra("lektion", 4);
                break;

            case (R.id.buttonOpenLektion5):
                startLektionUebersicht.putExtra("lektion", 5);
                break;
        }

        //starting the activity
        startActivity(startLektionUebersicht);

    }

    public static boolean isDEV_CHEAT_MODE() {
        return DEV_CHEAT_MODE;
    }
    public static void setDEV_CHEAT_MODE(boolean DEV_CHEAT_MODE) {
        Home.DEV_CHEAT_MODE = DEV_CHEAT_MODE;
    }
    public static boolean isDEVELOPER() {
        return DEVELOPER;
    }
    public static void setDEVELOPER(boolean DEVELOPER) {
        Home.DEVELOPER = DEVELOPER;
    }
}