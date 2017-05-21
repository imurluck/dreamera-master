package com.example.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dreamera_master.MyApplication;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangzongxiang on 2017/5/17.
 */

public class ParseJSON {
    public static List<String> handleJSONForPlaces(String jsonData) {
        List<String> places = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                places.add(jsonObject.getString("name"));
                SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("places",
                        Context.MODE_PRIVATE).edit();
                editor.putString(jsonObject.getString("name"), String.valueOf(jsonObject.getInt("id")));
                editor.apply();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    public static Place handleJSONForConcretePlace(String jsonData) {
        return new Gson().fromJson(jsonData, Place.class);
    }
}
