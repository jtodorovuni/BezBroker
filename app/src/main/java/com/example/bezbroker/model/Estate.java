package com.example.bezbroker.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Estate {
    public int id;
    public int categoryId;
    public String categoryName;
    public String title;
    public String description;
    public String city;
    public double price;
    public double area;
    public String address;
    public Double lat;
    public Double lont;
    public int userId;
    public String createdAt;
    public List<String> photos = new ArrayList<>();

    public String ownerEmail;
    public String ownerFirstName;
    public String ownerLastName;

    public static Estate fromJson(JSONObject o){
        Estate e = new Estate();
        e.id = o.optInt("id");
        e.categoryId = o.optInt("categoryId");
        e.categoryName = o.optString("categoryName");
        e.title = o.optString("title");
        e.description = o.optString("description");
        e.city = o.optString("city");
        e.price = o.optDouble("price", 0);
        e.area = o.optDouble("area", 0);
        e.address = o.optString("address");

        if(!o.isNull("lat")){
            e.lat = o.optDouble("lat");
        }

        if(!o.isNull("lont")){
            e.lont = o.optDouble("lont");
        }

        e.userId = o.optInt("userId");
        e.createdAt = o.optString("createdAt");
        e.ownerEmail = o.optString("ownerEmail");
        e.ownerFirstName = o.optString("ownerFirstName");
        e.ownerLastName = o.optString("ownerLastName");

        JSONArray photos = o.optJSONArray("photos");

        if(photos != null){
            for(int i = 0; i < photos.length(); i++){
                e.photos.add(photos.optString(i));
            }
        }
        return e;
    }

    public static List<Estate> listFromJson(JSONArray arr) throws JSONException {
        List<Estate> result = new ArrayList<>();

        for(int i = 0; i < arr.length(); i++){
            result.add(fromJson(arr.getJSONObject(i)));
        }

        return result;
    }

    public String firstPhoto(){

        if(photos.isEmpty())
            return null;

        return photos.get(0);
    }
}
