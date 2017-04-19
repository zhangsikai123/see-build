package com.rogueapps.aggar.utils;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.android.gms.wearable.Asset;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.IOException;
/**
 * Created by zhangsikai on 4/16/17.
 */
public class JsonHandler {
    public JSONObject jb;
    public JsonHandler(String address, Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(address);
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            this.jb = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
