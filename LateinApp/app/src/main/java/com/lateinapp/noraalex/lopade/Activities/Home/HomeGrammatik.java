package com.lateinapp.noraalex.lopade.Activities.Home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lateinapp.noraalex.lopade.R;

public class HomeGrammatik extends Fragment {

    public static HomeGrammatik newInstance() {
        HomeGrammatik fragment = new HomeGrammatik();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_grammatik, container, false);
    }
}
