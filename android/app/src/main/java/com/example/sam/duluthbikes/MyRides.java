package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by clark on 4/15/2017.
 */

public class MyRides extends FragmentActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private ArrayList<PolylineOptions> polylineArrayList;
    private MyRidesData ridesData;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myrides);

        mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.myMaps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        bundle.putString("RIDE",polylineArrayList.toString());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        polylineArrayList = ridesData.getInstance(this.getApplicationContext()).getPolylineArrayList();

        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(46.7867,-92.1005),11f);
        map.moveCamera(cu);
        for(int i=0;i<polylineArrayList.size();i++){
            PolylineOptions p = polylineArrayList.get(i);
            map.addPolyline(p);
        }

    }
}
