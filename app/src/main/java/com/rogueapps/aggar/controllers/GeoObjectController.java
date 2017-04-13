package com.rogueapps.aggar.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.beyondar.android.world.GeoObject;
import com.beyondar.android.world.World;
import com.rogueapps.aggar.R;

/**
 * Created by Nahum on 17/11/2016.
 */

public class GeoObjectController {
    public static double lat = 30.6191422d, lon = -96.3407925d;
    public static GeoObject createGeoObject(Long obId, double latitude, double longitude,
                                            String name, int resource){
        GeoObject go = new GeoObject(obId);
        go.setGeoPosition(latitude, longitude);
        go.setImageResource(resource);
        go.setName(name);
        return go;
    }

    public static World fillWorld(World world){
        int unTouched = R.drawable.untouched;
        GeoObject object = GeoObjectController.createGeoObject(0L, lat+0.0001d,lon+0.0001d,"Doherty", R.drawable.doherty);
//        GeoObject object1 = GeoObjectController.createGeoObject(1L,30.6189662d,-96.3410415d,"GeoScience", R.drawable.geoscience);
//        GeoObject object2 = GeoObjectController.createGeoObject(2L,30.6186332d,-96.3402005d,"Chemistry", R.drawable.chemistry);
//        GeoObject object3 = GeoObjectController.createGeoObject(3L,30.6195692d,-96.3409875d,"JamesCain", R.drawable.jamescain);


        world.addBeyondarObject(object,0);
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
