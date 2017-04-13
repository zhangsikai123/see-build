package com.rogueapps.aggar.models;

import android.location.Location;

/**
 * Created by zhangsikai on 11/17/16.
 */

public class Landmark extends Object {
    private String history;

    //constructor
    public Landmark() {
    }

    public Landmark(String name, Location location, String history) {

        super(name, location);

        this.history = history;
    }


    //getter & setter
    String getHistory() {
        return history;
    }

    void setHistory(String history) {
        this.history = history;
    }

}

