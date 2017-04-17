package com.example.sam.duluthbikes;

import android.content.Context;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by clark on 4/15/2017.
 */

public class myRidesData {
    private static myRidesData ourInstance;
    private static Context mContext;
    private ArrayList<PolylineOptions> polylineArrayList;

    private myRidesData(Context context) {
        mContext = context;
        polylineArrayList = getPolylineArrayList();
    }

    public static myRidesData getInstance(Context context) {
        if(ourInstance==null)ourInstance = new myRidesData(context);
        return ourInstance;
    }

    public ArrayList<PolylineOptions> getPolylineArrayList(){
        return polylineArrayList;
    }

    public void addPolyline(PolylineOptions polyline) {
        polylineArrayList.add(polyline);
    }
}
