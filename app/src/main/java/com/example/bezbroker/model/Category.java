package com.example.bezbroker.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public int id;
    public String name;
    public String createdAt;

    public Category(int id, String name){
        this.name = name;
        this.id = id;
    }

    public static List<Category> listFromJson(JSONArray arr) throws JSONException {
        List<Category> result = new ArrayList<>();

        for(int i = 0; i < arr.length(); i++){
            JSONObject o = arr.getJSONObject(i);

            result.add(new Category(
                    o.optInt("id"),
                    o.optString("name")
             ));
        }

        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
