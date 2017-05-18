package com.example.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangzongxiang on 2017/5/17.
 */

public class ParseJSON {

    public static List<String> parseJSONForPlace(String jsonData) {
        List<String> places = new ArrayList<String>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                places.add(jsonObject.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }
}
