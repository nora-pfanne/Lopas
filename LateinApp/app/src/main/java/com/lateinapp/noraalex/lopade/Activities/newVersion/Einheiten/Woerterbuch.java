package com.lateinapp.noraalex.lopade.Activities.newVersion.Einheiten;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.lateinapp.noraalex.lopade.Activities.oldVersion.LateinAppActivity;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.Databases.Tables.AdverbDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.DeklinationsendungDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.PräpositionDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SprichwortDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.SubstantivDB;
import com.lateinapp.noraalex.lopade.Databases.Tables.VerbDB;
import com.lateinapp.noraalex.lopade.R;

public class Woerterbuch extends LateinAppActivity implements AdapterView.OnItemSelectedListener{

    private TableLayout tl;
    private Spinner spinner;
    private int currentSpinnerPos;
    private String[] columns;
    private DBHelper dbHelper;


    /**
     * Configuration the spinner
     * @param savedInstanceState Used for passing data between activities
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woerterbuch);

        tl = findViewById(R.id.table_layout);

        dbHelper = new DBHelper(getApplicationContext());

        //Configurating the spinner
        //TODO: Set spinner text color to something readable
        spinner = findViewById(R.id.lektionSpinner);
        spinner.setOnItemSelectedListener(this);
        String[] spinnerElements = {
                "Lektion 1",
                "Lektion 2",
                "Lektion 3",
                "Lektion 4",
                "Lektion 5",
                "Lektion 6"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerElements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        currentSpinnerPos = -1;

    }

    /**
     * Adding new rows to a already existing TableLayout.
     * @param tl TableLayout
     * @param values 2-dimensional-array:
     *               first dimension:
     *                  Every entry corresponds to one vocabulary-entry
     *               second dimension:
     *                  [i][0]: german-translation-column of the vocabulary-entry
     *                  [i][1]: latin-column of the vocabulary-entry
     */
    private void addTableRows(TableLayout tl, String[][] values) {

        //TODO: What do we do if the line to be printed is longer than the smartphone width

        //Going through every row of the values[][]-array and adding one TableRow for each
        for (String[] strings : values){

            String deutsch = strings[0];
            String latein = strings[1];

            // Create the table row
            TableRow tr = new TableRow(this);

            //Changing the appearance to a alternating grey and black pattern for better readability
            if (tl.getChildCount() % 2 != 0) {
                tr.setBackgroundColor(Color.GRAY);
            } else {
                tr.setBackgroundColor(Color.DKGRAY);
            }
            tr.setId(100 + tl.getChildCount());

            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            //Creating the label for the latin word
            TextView labelLatein = new TextView(this);
            labelLatein.setId(200 + tl.getChildCount());
            labelLatein.setText(latein + "\t");
            labelLatein.setTextColor(Color.WHITE);
            //Creating the label for the german translation
            TextView labelDeutsch = new TextView(this);
            labelDeutsch.setId(200 + tl.getChildCount());
            labelDeutsch.setText(deutsch);
            labelDeutsch.setTextColor(Color.WHITE);

            //Adding the created labels to the TableRow new
            tr.addView(labelLatein);
            tr.addView(labelDeutsch);

            //Adding the created TableRow to the TableLayout
            tl.addView(tr);
        }
    }

    /**
     * Handling the calls to 'addTableRow(..)' depending on the newly selected item of the spinner
     * @param parent
     * @param view
     * @param pos position of the newly selected item
     * @param id
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

        if (currentSpinnerPos != pos) {
            currentSpinnerPos = pos;

            //Removing all rows
            for (int i = 0; i < tl.getChildCount(); i++) {
                View child = tl.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }


            //Adding entries to the table layout

            //Adding values for 'Verb'
            columns = new String[]{
                    VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH,
                    VerbDB.FeedEntry._ID
            };
            String[][] valuesVerb = dbHelper.getColumns(VerbDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            //replacing the _ID column with the right conjugation of the corresponding verb
            for (int i = 0; i < valuesVerb.length; i++){
                valuesVerb[i][1] = dbHelper.getKonjugiertesVerb(
                        Integer.parseInt(valuesVerb[i][1]),
                        "inf");
            }
            addTableRows(tl, valuesVerb);

            //Adding values for "Substantiv"
            columns = new String[]{
                    SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH,
                    SubstantivDB.FeedEntry._ID
            };
            String[][] valuesSubstantiv = dbHelper.getColumns(SubstantivDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            //replacing the _ID column with the right declination of the corresponding subjective
            for (int i = 0; i < valuesSubstantiv.length; i++){
                valuesSubstantiv[i][1] = dbHelper.getDekliniertenSubstantiv(
                        Integer.parseInt(valuesSubstantiv[i][1]),
                        DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG);
            }
            addTableRows(tl, valuesSubstantiv);

            //Adding values for everything but "Substantiv" and "Verb"
            columns = new String[]{
                    "Deutsch",
                    "Latein"
            };

            String[][] valuesAdverb = dbHelper.getColumns(AdverbDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            addTableRows(tl, valuesAdverb);

            String[][] valuesPräposition = dbHelper.getColumns(PräpositionDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            addTableRows(tl, valuesPräposition);

            String[][] valuesSprichwort = dbHelper.getColumns(SprichwortDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            addTableRows(tl, valuesSprichwort);


            dbHelper.closeDb();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dbHelper.closeDb();
    }
}



