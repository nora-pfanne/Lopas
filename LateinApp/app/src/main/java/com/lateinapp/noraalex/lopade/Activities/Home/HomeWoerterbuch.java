package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lateinapp.noraalex.lopade.Activities.Woerterbuch;
import com.lateinapp.noraalex.lopade.R;

public class HomeWoerterbuch extends Fragment {

    private static final String TAG = "HomeWörterbuch";

    public static HomeWoerterbuch newInstance() {
        HomeWoerterbuch fragment = new HomeWoerterbuch();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_woerterbuch, container, false);
    }

    public void navButtonClicked(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.home_woerterbuch_button:
                intent = new Intent(getActivity(), Woerterbuch.class);
                startActivity(intent);
                break;

            default:
                Log.e(TAG, "The button that called 'navButtonClicked' was not" +
                        " identified\nID: " + v.getId());
                break;
        }

    }
}
