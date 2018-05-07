package com.lateinapp.noraalex.lopade.Activities.oldVersion.Einheiten;

import android.os.Bundle;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.R;

public class Satzglieder extends LateinAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_satzglieder);

        setup();
    }

    private void setup(){
    }

    /*
    //Src: https://www.uzh.ch/latinum/amann/Grammatikunterlagen/Satzteile.pdf
    private abstract class Satzglied{
        private String textLatein;
        private String textDeutsch;

        public Satzglied(){}
    }

    private class Praedikat extends Satzglied{}
    private class Subjekt extends Satzglied{}
    private class Objekt extends Satzglied{}
    private class Attribut extends Satzglied{}
    private class Abverbium extends Satzglied{}
    */

}
