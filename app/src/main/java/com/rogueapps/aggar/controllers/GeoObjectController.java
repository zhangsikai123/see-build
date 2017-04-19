package com.rogueapps.aggar.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;

import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.rogueapps.aggar.R;
import com.rogueapps.aggar.utils.JsonHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nahum on 17/11/2016.
 */

public class GeoObjectController {
    public static double lat = 30.6185836d, lon = -96.3384769d;
    public static GeoObject createGeoObject(Long obId, double latitude, double longitude,
                                            String name, int resource){
        GeoObject go = new GeoObject(obId);
        go.setGeoPosition(latitude, longitude);
        go.setImageResource(resource);
        go.setName(name);
        return go;
    }

    public static World fillWorld(World world, JSONObject jb){
        //load data of geo object
//        GeoObject object = GeoObjectController.createGeoObject(0L, lat+0.0001d,lon+0.0001d,"Doherty", R.drawable.doherty);
//        GeoObject object1 = GeoObjectController.createGeoObject(1L,lat+0.00023d,lon+0.0001d,"GeoScience", R.drawable.geoscience);
//        GeoObject object2 = GeoObjectController.createGeoObject(2L,lat+0.0000332d,lon+0.00015d,"Chemistry", R.drawable.chemistry);
//        GeoObject object3 = GeoObjectController.createGeoObject(3L,lat+0.0002d,lon+0.00014d,"JamesCain", R.drawable.jamescain);

        Iterator<String> objects = jb.keys();
        while(objects.hasNext()){
            JSONObject building = null;
            try {
                String key = objects.next();
               building = jb.getJSONObject(key);
               double latitude = (double) ((JSONArray)building.get("center")).get(0);
                double longtitude = (double) ((JSONArray)building.get("center")).get(1);
                String name = building.get("name").toString();
               GeoObject object =  GeoObjectController.createGeoObject(Long.parseLong(key),latitude,longtitude,name,R.drawable.doherty);
                world.addBeyondarObject(object,0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        world.addBeyondarObject(object,0);
//        world.addBeyondarObject(object1,0);
//        world.addBeyondarObject(object2,0);
//        world.addBeyondarObject(object3,0);

        return world;
    }
    public static GeoObject createGeoObject(Long obId, double latitude, double longitude,
                                            String name, String resource){
        GeoObject go = new GeoObject(obId);
        go.setGeoPosition(latitude, longitude);
        go.setName(name);
        Bitmap bitmap = BitmapFactory.decodeFile(resource);
        go.setImageUri(resource);
        return go;
    }
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (earthRadius * c);

        return dist;
    }
}
