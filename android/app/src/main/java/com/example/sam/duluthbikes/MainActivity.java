package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;

/**
 * Main Activity Class
 * Displays a map with your current location and allows to start tracking
 */

public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback,ModelViewPresenterComponents.View {

    //variable declaration area
    private Presenter mPresenter;
    public static String userName = null;
    private Location mLastLocation;
    private GoogleMap mMap;
    private ArrayList<LatLng> points;
    private PolylineOptions polylineOptions;
    private LocationData locationData;
    private boolean animate;

    private LinearLayout tv;
    private SupportMapFragment mapFragment;
    private TextView tvSpeed;
    private TextView tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharSequence text ="Must click finish to end location tracking!";
        Toast toast = Toast.makeText(
                getApplicationContext(), text,Toast.LENGTH_LONG
        );
        toast.show();
        points = new ArrayList<>();
        polylineOptions = new PolylineOptions()
                .width(15)
                .color(Color.BLUE);

        mPresenter = new Presenter(this.getApplicationContext(),this,this);
        mPresenter.clickStart();
        animate = true;

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tv = (LinearLayout) findViewById(R.id.tvStats);
        tv.setVisibility(View.GONE);
        tvDistance = (TextView) findViewById(R.id.distanceMain);
        tvSpeed = (TextView) findViewById(R.id.speed);
    }

    /**
     * Method for pausing the current ride, will start a new activity.
     * @param view
     */
    public void pauseRide(View view) {
        mPresenter.pauseRideButton();
        //Intent pauseIntent = new Intent(this, PauseActivity.class);
        //startActivity(pauseIntent);
    }

    public void endRide(View view) {
        mPresenter.finishRideButton();
        Intent endIntent = new Intent(this.getApplicationContext(),EndRideActivity.class);

        Date thisDate = new Date();
        Long endTime = thisDate.getTime();

        endIntent.putExtra("dis",LocationData.getOurInstance(this.getBaseContext()).getDistance());
        endIntent.putExtra("startTime", LocationData.getOurInstance(this.getBaseContext()).getStartTime());
        endIntent.putExtra("endTime", endTime);

        startActivity(endIntent);
        mPresenter.notifyRoute(LocationData.getOurInstance(this.getBaseContext()).getTrip(),
                locationData.getOurInstance(this.getBaseContext()).getLatlng());
        LocationData.getOurInstance(this.getBaseContext()).resetData();
    }

    public void changeUI(View view){
        if(tv.getVisibility()==View.GONE){
            tv.setVisibility(View.VISIBLE);
            mapFragment.getView().setVisibility(View.GONE);
            String sd = Float.toString(getLastLocation().getSpeed());
            tvSpeed.setText(sd);
            sd = Double.toString(locationData.getOurInstance(this.getBaseContext()).getDistance());
            tvDistance.setText(sd);
        }else{
            tv.setVisibility(View.GONE);
            mapFragment.getView().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.connectApi();
    }

     /**
     * Required by OnMapReadyCallback interface
     * Called when the map is ready to be used.
     * https://developers.google.com/android/reference/com/google/android/gms/maps/OnMapReadyCallback
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    //get location and set location methods
    public Location getLastLocation(){ return mLastLocation; }
    public void setLastLocation(Location curr) { mLastLocation = curr; }

    @Override
    public void locationChanged(Location location) {
        setLastLocation(location);
        LatLng latLng =
                new LatLng(getLastLocation().getLatitude(),getLastLocation().getLongitude());
        points.add(latLng);
        if(mMap.isMyLocationEnabled()==false){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mMap.setMyLocationEnabled(true);
                mMap.setMaxZoomPreference(18);
            }
        }
        locationData.getOurInstance(this.getBaseContext()).addPoint(latLng,location);
        polylineOptions=locationData.getOurInstance(this.getBaseContext()).getPoints();
        LatLngBounds.Builder bounds = LocationData.getOurInstance(this.getBaseContext()).getBuilder();
        CameraUpdate cu =  CameraUpdateFactory.newLatLngBounds(bounds.build(),100);
        if(animate)
        {
            animate = false;
            mMap.animateCamera(cu);
        }
        else mMap.moveCamera(cu);
        Polyline p = mMap.addPolyline(polylineOptions);
        String sd = Float.toString(location.getSpeed());
        tvSpeed.setText(sd);
        sd = Double.toString(locationData.getOurInstance(this.getBaseContext()).getDistance());
        tvDistance.setText(sd);
    }

    @Override
    public void userResults(String results) {

    }
}