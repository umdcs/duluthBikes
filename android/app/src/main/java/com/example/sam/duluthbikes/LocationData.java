package com.example.sam.duluthbikes;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Singleton for saving ride information when the screen is rotated
 */

public class LocationData {
    private static LocationData ourInstance;
    private static Context mContext;
    private PolylineOptions mPolylineOptions;
    private LatLngBounds.Builder builder;
    private JSONArray trip;
    private JSONArray latlng;

    private Location lastLocation;
    private double distance;
    private String startTime;

    private LocationData(Context context) {

        mContext = context;
        mPolylineOptions = getPoints();
        builder = getBuilder();
        lastLocation = getLastLocation();
        distance = getDistance();
        trip = getTrip();
        latlng = getLatlng();
        startTime = getStartTime();
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

    public String getStartTime(){
        if (startTime == null){
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(date.getTime());
            startTime = formattedDate;
        }
        return startTime;
    }

    public JSONArray getTrip(){
        if(trip==null)trip = new JSONArray();
        return trip;
    }

    public JSONArray getLatlng(){
        if(latlng==null)latlng = new JSONArray();
        return latlng;
    }

    public void addPoint(LatLng p,Location location){
        if(distance>0){
            mPolylineOptions.add(p);
            JSONObject arr = new JSONObject();
            try {
                arr.put("lat",location.getLatitude());
                arr.put("lng",location.getLongitude());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            trip.put(arr);
            latlng.put(p);
        }
        builder.include(p);
        if(getLastLocation()!=null)distance += getLastLocation().distanceTo(location);
        lastLocation = location;
    }
}
