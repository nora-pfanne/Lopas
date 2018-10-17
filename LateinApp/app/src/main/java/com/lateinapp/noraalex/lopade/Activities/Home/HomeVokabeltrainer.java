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
import com.lateinapp.noraalex.lopade.General;
import com.lateinapp.noraalex.lopade.R;
import com.lateinapp.noraalex.lopade.Score;

public class HomeVokabeltrainer extends Fragment {

    private static final String TAG = "HomeVokabeltrainer";

    TextView tvScore1,
            tvScore2,
            tvScore3,
            tvScore4,
            tvScore5,
            tvScore6;
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

        sharedPrefrences = General.getSharedPrefrences(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        tvScore1.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,1) + "\nS: " + Score.getScoreVocabularyTrainer(1, sharedPrefrences));
        tvScore2.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,2) + "\nS: " + Score.getScoreVocabularyTrainer(2, sharedPrefrences));
        tvScore3.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,3) + "\nS: " + Score.getScoreVocabularyTrainer(3, sharedPrefrences));
        tvScore4.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,4) + "\nS: " + Score.getScoreVocabularyTrainer(4, sharedPrefrences));
        tvScore5.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,5) + "\nS: " + Score.getScoreVocabularyTrainer(5, sharedPrefrences));
        tvScore6.setText("HS: " + Score.getHighScoreVocabularyTrainer(sharedPrefrences,6) + "\nS: " + Score.getScoreVocabularyTrainer(6, sharedPrefrences));

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