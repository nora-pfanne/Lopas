package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputVokabeltrainer;
import com.lateinapp.noraalex.lopade.R;

public class HomeVokabeltrainer extends Fragment {

    private static final String TAG = "HomeVokabeltrainer";

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
        return inflater.inflate(R.layout.fragment_home_vokabeltrainer, container, false);
    }

    public void navButtonClicked(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.home_voc_1:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion",1);
                startActivity(intent);
                break;

            case R.id.home_voc_2:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 2);
                startActivity(intent);
                break;

            case R.id.home_voc_3:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 3);
                startActivity(intent);
                break;

            case R.id.home_voc_4:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 4);
                startActivity(intent);
                break;

            case R.id.home_voc_5:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 5);
                startActivity(intent);
                break;

            case R.id.home_voc_6:
                intent = new Intent(getActivity(), UserInputVokabeltrainer.class);
                intent.putExtra("lektion", 6);
                startActivity(intent);
                break;

            default:
                Log.e(TAG, "The button that called 'navButtonClicked' was not" +
                        " identified\nID: " + v.getId());
                break;
        }
    }
}