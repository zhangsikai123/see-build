package com.rogueapps.aggar.models;

import android.location.Location;

import java.math.BigInteger;

/**
 * Created by zhangsikai on 11/17/16.
 */

public abstract class Object {


    private String name;

    private Location location;

    public Object(){}

    public Object(String name, Location location){

        this.location = location;

        this.name = name;
    }

    //getter
     String getName(){return name;}

     Location getLocation(){return location;}

    //setter
     void setName(String name){this.name = name;}

     void setLocation(Location location){this.location=location;}


}
