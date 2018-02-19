package com.example.norablakaj.lateinapp.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.TableRow.LayoutParams;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.Databases.Tables.AdverbDB;
import com.example.norablakaj.lateinapp.Databases.Tables.DeklinationsendungDB;
import com.example.norablakaj.lateinapp.Databases.Tables.Personalendung_PräsensDB;
import com.example.norablakaj.lateinapp.Databases.Tables.PräpositionDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SprichwortDB;
import com.example.norablakaj.lateinapp.Databases.Tables.SubstantivDB;
import com.example.norablakaj.lateinapp.Databases.Tables.VerbDB;
import com.example.norablakaj.lateinapp.R;

public class Woerterbuch extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TableLayout tl;
    LinearLayout linearLayout;
    Spinner spinner;
    int currentSpinnerPos;
    String[] columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woerterbuch);

        tl = findViewById(R.id.table_layout);
        linearLayout = findViewById(R.id.linear_layout);

        spinner = findViewById(R.id.lektionSpinner);
        spinner.setOnItemSelectedListener(this);
        String[] spinnerElements = {
                "Lektion 1",
                "Lektion 2",
                "Lektion 3",
                "Lektion 4",
                "Lektion 5"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerElements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        currentSpinnerPos = -1;

    }


    private void addTableRows(TableLayout tl, String[][] values) {

        //TODO: What do we do if the line to be printed is longer than the smartphone width



        for(int i = 0; i < values.length; i++) {

            String deutsch = values[i][0];
            String latein = values[i][1];

            // Create the table row

            //Changing the appearance to a alternating grey and black pattern for better readability
            TableRow tr = new TableRow(this);
            if (tl.getChildCount() % 2 != 0) {
                tr.setBackgroundColor(Color.GRAY);
            } else {
                tr.setBackgroundColor(Color.DKGRAY);
            }
            tr.setId(100 + tl.getChildCount());

            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));

            //Create the columns to add as table data

            TextView labelLatein = new TextView(this);
            labelLatein.setId(200 + tl.getChildCount());
            labelLatein.setText(latein + "\t");
            labelLatein.setTextColor(Color.WHITE);
            tr.addView(labelLatein);

            TextView labelDeutsch = new TextView(this);
            labelDeutsch.setId(200 + tl.getChildCount());
            labelDeutsch.setText(deutsch);
            labelDeutsch.setTextColor(Color.WHITE);
            tr.addView(labelDeutsch);

            // finally add this to the table row
            tl.addView(tr);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {

        if (currentSpinnerPos != pos) {
            currentSpinnerPos = pos;

            //remove all rows
            for (int i = 0; i < tl.getChildCount(); i++) {
                View child = tl.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }

            //Adding entries to the table layout
            DBHelper dbHelper = new DBHelper(getApplicationContext());

            //Adding values for "Substantiv"
            columns = new String[]{
                    SubstantivDB.FeedEntry.COLUMN_NOM_SG_DEUTSCH,
                    SubstantivDB.FeedEntry.COLUMN_WORTSTAMM,
                    SubstantivDB.FeedEntry._ID
            };

            String[][] valuesSubstantiv = dbHelper.getColumns(SubstantivDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            for (int i = 0; i < valuesSubstantiv.length; i++){
                valuesSubstantiv[i][1] = dbHelper.getDekliniertenSubstantiv(
                        Integer.parseInt(valuesSubstantiv[i][2]),
                        DeklinationsendungDB.FeedEntry.COLUMN_NOM_SG);
            }
            addTableRows(tl, valuesSubstantiv);

            //Adding values for 'Verb'
            columns = new String[]{
                    VerbDB.FeedEntry.COLUMN_INFINITIV_DEUTSCH,
                    VerbDB.FeedEntry.COLUMN_WORTSTAMM,
                    VerbDB.FeedEntry._ID
            };
            String[][] valuesVerb = dbHelper.getColumns(VerbDB.FeedEntry.TABLE_NAME, columns, pos + 1);
            for (int i = 0; i < valuesVerb.length; i++){
                valuesVerb[i][1] += "re";
            }
            addTableRows(tl, valuesVerb);


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
            dbHelper.close();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



