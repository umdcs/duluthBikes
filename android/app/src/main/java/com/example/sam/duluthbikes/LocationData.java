package com.example.sam.duluthbikes;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by clark on 3/28/2017.
 */

public class LocationData {
    private static LocationData ourInstance;
    private static Context mContext;
    private PolylineOptions mPolylineOptions;

    private LocationData(Context context) {

        mContext = context;
        mPolylineOptions = getPoints();
    }

    public static LocationData getOurInstance(Context context){
        if(ourInstance==null){
            ourInstance = new LocationData(context);
        }
        return ourInstance;
    }

    public PolylineOptions getPoints(){
        if(mPolylineOptions==null)mPolylineOptions = new PolylineOptions()
                .width(5)
                .color(Color.BLUE);
        return mPolylineOptions;
    }

    public void addPoint(LatLng p){
        mPolylineOptions.add(p);
    }
}
