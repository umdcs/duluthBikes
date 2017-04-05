package com.example.sam.duluthbikes;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by clark on 3/28/2017.
 */

public class LocationData {
    private static LocationData ourInstance;
    private static Context mContext;
    private PolylineOptions mPolylineOptions;
    private LatLngBounds.Builder builder;

    private Location lastLocation;
    private double distance;

    private LocationData(Context context) {

        mContext = context;
        mPolylineOptions = getPoints();
        builder = getBuilder();
        lastLocation = getLastLocation();
        distance = getDistance();
    }


    // Called when finish button is pressed. Removed polylines from the map
    public void resetData() {
        ourInstance = null;
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

    public Location getLastLocation(){
        if(lastLocation==null)distance =-1;
        return lastLocation;
    }

    public double getDistance(){
        if(distance==-1)distance=0;
        return distance;
    }

    public void addPoint(LatLng p,Location location){
        if(distance>0)mPolylineOptions.add(p);
        builder.include(p);
        if(getLastLocation()!=null)distance += getLastLocation().distanceTo(location);
        lastLocation = location;
    }
}
