package com.lateinapp.noraalex.lopade.Activities.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickKasusFragen;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.ClickPersonalendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.Satzglieder;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputDeklinationsendung;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputEsseVelleNolle;
import com.lateinapp.noraalex.lopade.Activities.Einheiten.UserInputPersonalendung;
import com.lateinapp.noraalex.lopade.R;

public class HomeGrammatik extends Fragment {

    private static final String TAG = "HomeGrammatik";

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


    public void navButtonClicked(View v) {
        Intent intent;

        switch (v.getId()) {

            case R.id.home_grammar_button_kasus_fragen:

                intent = new Intent(getActivity(), ClickKasusFragen.class);
                startActivity(intent);
                break;

            case R.id.home_grammar_button_click_deklination:

                intent = new Intent(getActivity(), ClickDeklinationsendung.class);
                intent.putExtra("ExtraClickDeklinationsendung", "GENITIV");
                startActivity(intent);
                break;

            case R.id.home_grammar_buttom_typing_deklinationsendung:

                intent = new Intent(getActivity(), UserInputDeklinationsendung.class);
                intent.putExtra("ExtraInputDeklinationsendung", "ALLE");
                startActivity(intent);

                break;

            case R.id.home_grammar_button_click_konjugation:
                intent = new Intent(getActivity(), ClickPersonalendung.class);
                intent.putExtra("ExtraClickPersonalendung", "DRITTE_PERSON");
                startActivity(intent);

                break;

            case R.id.home_grammar_button_typing_konjugation:
                intent = new Intent(getActivity(), UserInputPersonalendung.class);
                intent.putExtra("ExtraInputPersonalendung", "DEFAULT");
                startActivity(intent);
                break;

            case R.id.home_grammar_button_esse_velle_nolle:
                intent = new Intent(getActivity(), UserInputEsseVelleNolle.class);
                startActivity(intent);
                break;

            case R.id.home_grammar_button_satzglieder:
                intent = new Intent(getActivity(), Satzglieder.class);
                startActivity(intent);
                break;

            default:
                Log.e(TAG, "The button that called 'navButtonClicked' was not" +
                        " identified\nID: " + v.getId());
                break;
        }
    }
}
