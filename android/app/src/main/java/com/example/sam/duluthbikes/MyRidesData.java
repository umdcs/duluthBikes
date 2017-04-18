package com.example.sam.duluthbikes;

import android.content.Context;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by clark on 4/15/2017.
 */

public class MyRidesData {
    private static MyRidesData ourInstance;
    private static Context mContext;
    private ArrayList<PolylineOptions> polylineArrayList;

    private MyRidesData(Context context) {
        mContext = context;
        polylineArrayList = getPolylineArrayList();
    }

    public static MyRidesData getInstance(Context context) {
        if(ourInstance==null)ourInstance = new MyRidesData(context);
        return ourInstance;
    }

    public ArrayList<PolylineOptions> getPolylineArrayList(){
        if(polylineArrayList==null)polylineArrayList = new ArrayList<>();
        return polylineArrayList;
    }

    public void addPolyline(PolylineOptions polyline) {
        polylineArrayList.add(polyline);
    }
}
