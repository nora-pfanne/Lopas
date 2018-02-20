package com.example.norablakaj.lateinapp.Activities.Grammatikeinheiten;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.norablakaj.lateinapp.Databases.DBHelper;
import com.example.norablakaj.lateinapp.R;

public class GrammatikPersonalendung extends AppCompatActivity {

    TextView grammatikUeberschrift;
    TextView grammatikAufgabenstellung;
    TextView latein;

    Button erstePersSg, erstePersPl,
            zweitwPersSg, zweitePersPl,
            drittePersSg, drittePersPl;

    ProgressBar progressBar;

    DBHelper dbHelper;

    int lektion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammatik_personalendung);
    }
}
