package com.example.sam.duluthbikes;

import android.content.Context;
import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by clark on 3/28/2017.
 */

public class LocationData {
    private static LocationData ourInstance;
    private static Context mContext;
    private PolylineOptions mPolylineOptions;
    private LatLngBounds.Builder builder;

    private LocationData(Context context) {

        mContext = context;
        mPolylineOptions = getPoints();
        builder = getBuilder();
    }

    public static LocationData getOurInstance(Context context){
        if(ourInstance==null){
            ourInstance = new LocationData(context);
        }
        return ourInstance;
    }

    public PolylineOptions getPoints(){
        if(mPolylineOptions==null)mPolylineOptions = new PolylineOptions()
                    .width(15)
                    .color(Color.BLUE);

        return mPolylineOptions;
    }

    public LatLngBounds.Builder getBuilder(){
        if(builder == null)builder=new LatLngBounds.Builder();
        return builder;
    }

    public void addPoint(LatLng p){
        mPolylineOptions.add(p);
        builder.include(p);
    }
}
