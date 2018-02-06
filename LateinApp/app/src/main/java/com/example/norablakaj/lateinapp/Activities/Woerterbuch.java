package com.example.norablakaj.lateinapp.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.norablakaj.lateinapp.R;

public class Woerterbuch extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TableLayout tl;
    LinearLayout linearLayout;
    int currentSpinnerPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woerterbuch);

        tl = findViewById(R.id.table_layout);
        linearLayout = findViewById(R.id.linear_layout);

        Spinner spinner = findViewById(R.id.lektionSpinner);
        spinner.setOnItemSelectedListener(this);
        String[] spinnerElements = {
                "Lektion 1",
                "Lektion 2",
                "Lektion 3"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerElements);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        currentSpinnerPos = -1;

    }

    /**
     * For every row in the cursor one row is inserted into the TableLayout
     * containing the data in set cursor columns
     *
     * @param tl     The TableLayout
     * @param cursor the Cursor with the items to be added
     * @return The amount of rows in the TableLayout after executing the method
     */
    private void addTableRows(TableLayout tl, Cursor cursor) {

        //TODO: set space between items on rows


        while (cursor.moveToNext()) {

            double id = cursor.getDouble(0);// get the first variable
            String latein = cursor.getString(1);// get the second variable
            String deutsch = cursor.getString(2); //get the third variable
            // Create the table row
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

            TextView labelID = new TextView(this);
            labelID.setId(200 + tl.getChildCount());
            //labelID.setText("" + (int)id);
            labelID.setText(tl.getChildCount() + "      ");
            labelID.setTextColor(Color.WHITE);
            tr.addView(labelID);

            TextView labelLatein = new TextView(this);
            labelLatein.setId(200 + tl.getChildCount());
            labelLatein.setText(latein);
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
        System.out.println("" + pos);

        if (currentSpinnerPos != pos){
            currentSpinnerPos = pos;

            //remove all rows
            for (int i = 0; i < tl.getChildCount(); i++) {
                View child = tl.getChildAt(i);
                if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
            }
            //Adding entries to the table layout
            /*
            DBHelper dbHelper = new DBHelper(getApplicationContext());


            Cursor cursorNomen = dbHelper.getAllEntriesNomen(currentSpinnerPos + 1);
            addTableRows(tl, cursorNomen);
            cursorNomen.close();

            Cursor cursorVerb = dbHelper.getAllEntriesVerb(currentSpinnerPos + 1);
            addTableRows(tl, cursorVerb);
            cursorVerb.close();

            dbHelper.closeDb();
            dbHelper.close();
            */
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



