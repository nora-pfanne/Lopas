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
import android.widget.Button;

import com.example.norablakaj.lateinapp.R;

public class Home extends DevActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean DEVELOPER = true;

    /**
     * Creating drawer
     * Initializing buttons
     * @param savedInstanceState Used for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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

        //Logs what Vokabeltrainer-lektions are completed
        SharedPreferences sharedPref = getSharedPreferences("SharedPreferences", 0);

        //Sets the DEVELOPER state according to a variable saved in a previous instance of the app
        DEVELOPER = sharedPref.getBoolean("DEVELOPER", false);

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

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //TODO: Maybe remove this item if unused?

        } else if (id == R.id.nav_wörterbuch) {
            //Opening the activity 'Woerterbuch'
            Intent openWörterbuch = new Intent(this, Woerterbuch.class);
            startActivity(openWörterbuch);
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
    public void buttonClicked(View view){

        Intent startLektionUebersicht= new Intent(view.getContext(), LektionUebersicht.class);

        //opens a new activity
        if (view.getId() == R.id.lektion1){

            startLektionUebersicht.putExtra("lektion",1);

        }else if (view.getId() == R.id.lektion2){

            startLektionUebersicht.putExtra("lektion",2);

        }else if (view.getId() == R.id.lektion3){

            startLektionUebersicht.putExtra("lektion",3);

        }else if (view.getId() == R.id.lektion4){

            startLektionUebersicht.putExtra("lektion",4);

        }else if (view.getId() == R.id.lektion5){

            startLektionUebersicht.putExtra("lektion", 5);
        }

        startActivity(startLektionUebersicht);

    }
}