package com.example.norablakaj.lateinapp.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.TableRow.LayoutParams;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.R;

public class Woerterbuch extends AppCompatActivity {

    TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woerterbuch);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        tl = findViewById(R.id.table_layout);

        int count = 0;
        //Count is being carried over so that the background color is changed every other row continuously
        Cursor cursorNomen = dbHelper.getAllEntriesNomen();
        count =addTableRows(tl, cursorNomen, count);
        cursorNomen.close();

        Cursor cursorVerb = dbHelper.getAllEntriesVerb();
        count = addTableRows(tl, cursorVerb, count);
        cursorVerb.close();

        dbHelper.closeDb();
        dbHelper.close();

    }

    private int addTableRows(TableLayout tl, Cursor cursor, int count){

        while (cursor.moveToNext()) {


            double id = cursor.getDouble(0);// get the first variable
            String latein = cursor.getString(1);// get the second variable
            String deutsch = cursor.getString(2); //get the third variable
            // Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0){
                tr.setBackgroundColor(Color.GRAY);
            }else {
                tr.setBackgroundColor(Color.DKGRAY);
            }
            tr.setId(100+count);

            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));

            //Create the columns to add as table data

            TextView labelID = new TextView(this);
            labelID.setId(200+count);
            labelID.setText("" + (int)id);
            labelID.setPadding(3,0,5,0);
            labelID.setTextColor(Color.WHITE);
            tr.addView(labelID);


            TextView labelLatein = new TextView(this);
            labelLatein.setId(200+count);
            labelLatein.setText(latein);
            labelLatein.setTextColor(Color.WHITE);
            labelID.setPadding(3,0,5,0);
            tr.addView(labelLatein);

            TextView labelDeutsch = new TextView(this);
            labelDeutsch.setId(200+count);
            labelDeutsch.setText(deutsch);
            labelDeutsch.setTextColor(Color.WHITE);
            labelID.setPadding(3,0,5,0);
            tr.addView(labelDeutsch);

            // finally add this to the table row
            tl.addView(tr);
            count++;
        }
        return count;
    }
}
