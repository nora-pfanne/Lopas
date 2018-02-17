package com.example.norablakaj.lateinapp;


import android.util.Log;

public class GrammatikManager {

    public GrammatikManager(char grammarPart, int lektion){

        if (grammarPart == 'A'){
            grammarPartA(lektion);
        }else if (grammarPart == 'B'){
            grammarPartB(lektion);
        }else if (grammarPart == 'C'){
            grammarPartC(lektion);
        }else {
            Log.e("GrammarPartConstructor", "Grammar part not found, only 'A', 'B' and 'C' " +
                    "allowed, but " + grammarPart + " was found");
        }
    }

    private void grammarPartA(int lektion){

        switch (lektion){

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'A' was passed");
        }

    }

    private void grammarPartB(int lektion){

        switch (lektion){

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'B' was passed");
        }

    }

    private void grammarPartC(int lektion){

        switch (lektion){

            case 1:

                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            default:
                Log.e("LektionNotFound", "Lektion in 'GrammatikManager.class' was not found." +
                        " LektionNr " + lektion + " with GrammarPart 'C' was passed");
        }

    }

}
