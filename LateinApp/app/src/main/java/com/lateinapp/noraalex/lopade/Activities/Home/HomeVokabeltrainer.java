package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputVokabeltrainer;
import com.lateinapp.noraalex.lopade.Databases.DBHelper;
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

import static com.lateinapp.noraalex.lopade.Databases.SQL_DUMP.allVocabularyTables;

public class HomeVokabeltrainer extends Fragment {

    private static final String TAG = "HomeVokabeltrainer";

    TextView tvScore1,
            tvScore2,
            tvScore3,
            tvScore4,
            tvScore5,
            tvScore6,
            tvScore7,
            tvScore8,
            tvScore9,
            tvScore10;
    SharedPreferences sharedPrefrences;

    public static HomeVokabeltrainer newInstance() {
        HomeVokabeltrainer fragment = new HomeVokabeltrainer();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_vokabeltrainer, container, false);

        tvScore1 = view.findViewById(R.id.home_voc_text_1);
        tvScore2 = view.findViewById(R.id.home_voc_text_2);
        tvScore3 = view.findViewById(R.id.home_voc_text_3);
        tvScore4 = view.findViewById(R.id.home_voc_text_4);
        tvScore5 = view.findViewById(R.id.home_voc_text_5);
        tvScore6 = view.findViewById(R.id.home_voc_text_6);
        tvScore7 = view.findViewById(R.id.home_voc_text_7);
        tvScore8 = view.findViewById(R.id.home_voc_text_8);
        tvScore9 = view.findViewById(R.id.home_voc_text_9);
        tvScore10 = view.findViewById(R.id.home_voc_text_10);

        sharedPrefrences = General.getSharedPrefrences(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DBHelper dbhelper = DBHelper.getInstance(getActivity().getApplicationContext());

        int progress1 = (int)(dbhelper.getGelerntProzent(1) * 100);
        int progress2 = (int)(dbhelper.getGelerntProzent(2) * 100);
        int progress3 = (int)(dbhelper.getGelerntProzent(3) * 100);
        int progress4 = (int)(dbhelper.getGelerntProzent(4) * 100);
        int progress5 = (int)(dbhelper.getGelerntProzent(5) * 100);
        int progress6 = (int)(dbhelper.getGelerntProzent(6) * 100);
        int progress7 = (int)(dbhelper.getGelerntProzent(7) * 100);
        int progress8 = (int)(dbhelper.getGelerntProzent(8) * 100);
        int progress9 = (int)(dbhelper.getGelerntProzent(9) * 100);

        String bestGrade1 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 1), Score.getLowestMistakesVoc(1, sharedPrefrences));
        String bestGrade2 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 2), Score.getLowestMistakesVoc(2, sharedPrefrences));
        String bestGrade3 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 3), Score.getLowestMistakesVoc(3, sharedPrefrences));
        String bestGrade4 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 4), Score.getLowestMistakesVoc(4, sharedPrefrences));
        String bestGrade5 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 5), Score.getLowestMistakesVoc(5, sharedPrefrences));
        String bestGrade6 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 6), Score.getLowestMistakesVoc(6, sharedPrefrences));
        String bestGrade7 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 7), Score.getLowestMistakesVoc(7, sharedPrefrences));
        String bestGrade8 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 8), Score.getLowestMistakesVoc(8, sharedPrefrences));
        String bestGrade9 = Score.getGradeFromMistakeAmount(dbhelper.countTableEntries(allVocabularyTables, 9), Score.getLowestMistakesVoc(9, sharedPrefrences));

        tvScore1.setText("Fortschritt:  " + progress1 + "%\nBeste Note: " + bestGrade1);
        tvScore2.setText("Fortschritt:  " + progress2 + "%\nBeste Note: " + bestGrade2);
        tvScore3.setText("Fortschritt:  " + progress3 + "%\nBeste Note: " + bestGrade3);
        tvScore4.setText("Fortschritt:  " + progress4 + "%\nBeste Note: " + bestGrade4);
        tvScore5.setText("Fortschritt:  " + progress5 + "%\nBeste Note: " + bestGrade5);
        tvScore6.setText("Fortschritt:  " + progress6 + "%\nBeste Note: " + bestGrade6);
        tvScore7.setText("Fortschritt:  " + progress7 + "%\nBeste Note: " + bestGrade7);
        tvScore8.setText("Fortschritt:  " + progress8 + "%\nBeste Note: " + bestGrade8);
        tvScore9.setText("aaa");
        tvScore10.setText("aaa");

    }

    public void navButtonClicked(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.home_voc_button_1:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion",1);
                startActivity(intent);
                break;

            case R.id.home_voc_button_2:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 2);
                startActivity(intent);
                break;

            case R.id.home_voc_button_3:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 3);
                startActivity(intent);
                break;

            case R.id.home_voc_button_4:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 4);
                startActivity(intent);
                break;

            case R.id.home_voc_button_5:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 5);
                startActivity(intent);
                break;

            case R.id.home_voc_button_6:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 6);
                startActivity(intent);
                break;

            case R.id.home_voc_button_7:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 7);
                startActivity(intent);
                break;

            case R.id.home_voc_button_8:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 8);
                startActivity(intent);
                break;

            case R.id.home_voc_button_9:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 9);
                startActivity(intent);
                break;

            case R.id.home_voc_button_10:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 10);
                startActivity(intent);
                break;

            default:
                Log.e(TAG, "The button that called 'navButtonClicked' was not" +
                        " identified\nID: " + v.getId());
                break;
        }
    }
}