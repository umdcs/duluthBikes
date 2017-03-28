package com.example.sam.duluthbikes;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by clark on 3/28/2017.
 */

public class LocationData {
    private static LocationData ourInstance;
    private static Context mContext;
    private ArrayList<LatLng> points;

    private LocationData(Context context) {

        mContext = context;
        points = getPoints();
    }

    public static LocationData getOurInstance(Context context){
        if(ourInstance==null){
            ourInstance = new LocationData(context);
        }
        return ourInstance;
    }

    public ArrayList<LatLng> getPoints(){
        if(points==null)points = new ArrayList<>();
        return points;
    }

    public void addPoint(LatLng p){
        points.add(p);
    }
}
