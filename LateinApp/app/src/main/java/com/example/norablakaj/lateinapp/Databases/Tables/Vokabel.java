package com.example.norablakaj.lateinapp.Databases.Tables;

abstract public class Vokabel {

    private final int id;
    private final String latein,
            deutsch;

    public Vokabel(int id, String lateinInf, String deutsch){
        this.id = id;
        this.latein = lateinInf;
        this.deutsch = deutsch;
    }

    public int getId() {
        return id;
    }
    public String getLatein() {
        return latein;
    }
    public String getDeutsch() {
        return deutsch;
    }
}
