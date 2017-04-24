
package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
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
    private ToggleButton pauseToggle;

    private LinearLayout tv;
    private SupportMapFragment mapFragment;
    private TextView tvSpeed;
    private TextView tvDistance;
    private FrameLayout linearLayout;
    private LinearLayout greyScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharSequence text ="Must click finish to end location tracking! Make sure location is enabled on your device.";
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

        //toggle button initializer
        addListenerOnToggle();

        //add listener to toggle button
        pauseToggle.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!pauseToggle.isChecked()){
                            pauseToggle.setChecked(false);
                            linearLayout.setVisibility(View.VISIBLE);
                            greyScreen.setVisibility(View.VISIBLE);
                            try {
                                mMap.setMyLocationEnabled(false);
                            }catch (SecurityException e){
                                e.printStackTrace();
                            }
                            pauseRide();
                        } else if(pauseToggle.isChecked()){
                            pauseToggle.setChecked(true);
                            linearLayout.setVisibility(View.GONE);
                            greyScreen.setVisibility(View.GONE);
                            try{
                                mMap.setMyLocationEnabled(true);
                            }catch (SecurityException e){
                                e.printStackTrace();
                            }
                            mPresenter.connectApi();
                        }
                    }
                }
        );

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tv = (LinearLayout) findViewById(R.id.tvStats);
        tv.setVisibility(View.VISIBLE);
        tvDistance = (TextView) findViewById(R.id.distanceMain);
        tvSpeed = (TextView) findViewById(R.id.speed);
        linearLayout = (FrameLayout) findViewById(R.id.cancelButton);
        linearLayout.setVisibility(View.GONE);
        greyScreen = (LinearLayout)findViewById(R.id.cancelGrey);
        greyScreen.setVisibility(View.GONE);
    }

    private void addListenerOnToggle() {
        pauseToggle = (ToggleButton)findViewById(R.id.togglePause);
        pauseToggle.setTextOn(getString(R.string.Pause));
        pauseToggle.setTextOff(getString(R.string.Resume));
        pauseToggle.setChecked(true);
    }


    public void pictureButton() {
        Intent intent = new Intent(this.getApplicationContext(),ReportFragment.class);
        startActivity(intent);
    }

    public void pauseRide() {
        mPresenter.pauseRideButton();
    }

    public void cancelTheRide(View view){
        locationData.getOurInstance(this).resetData();
        mPresenter.finishRideButton();
        Intent i = new Intent(this,MenuActivity.class);
        startActivity(i);
    }

    public void endRide(View view) {
        mPresenter.finishRideButton();
        Intent endIntent = new Intent(this.getApplicationContext(),EndRideActivity.class);
        Date thisDate = new Date();

        Long endTime = thisDate.getTime();
        Long startTime = LocationData.getOurInstance(this.getBaseContext()).getStartTime();
        Double distance = LocationData.getOurInstance(this.getBaseContext()).getDistance();

        updateTotals(distance, endTime-startTime);

        endIntent.putExtra("dis",distance);
        endIntent.putExtra("startTime", startTime);
        endIntent.putExtra("endTime", endTime);

        mPresenter.notifyRoute(LocationData.getOurInstance(this.getBaseContext()).getTrip(),
                locationData.getOurInstance(this.getBaseContext()).getLatlng());
        LocationData.getOurInstance(this.getBaseContext()).resetData();
        startActivity(endIntent);
    }

    public void changeUI(View view){
        if(tv.getVisibility()==View.GONE){
            tv.setVisibility(View.VISIBLE);

        }else{
            tv.setVisibility(View.GONE);

        }
    }

    @Override
    public void onResume() {
        mPresenter.connectApi();
        super.onResume();

    }

    private void updateTotals(Double distance, Long timelapse){

        SharedPreferences totalstats = getSharedPreferences(getString(R.string.lifetimeStats_file_key), 0);
        Float totDistance = totalstats.getFloat(getString(R.string.lifetimeStats_totDist), 0) +
                distance.floatValue();

        Long totTime = totalstats.getLong(getString(R.string.lifetimeStats_totTime), 0) + timelapse;
        int rideNumber = totalstats.getInt(getString(R.string.lifetimeStats_rideNumber), 0) + 1;

        String newRideID = "ride"+ rideNumber;
        String newRideDistance = newRideID + "Distance";
        String newRideTime = newRideID + "Time";

        SharedPreferences.Editor editor = totalstats.edit();
        editor.putFloat(getString(R.string.lifetimeStats_totDist), totDistance);
        editor.putLong(getString(R.string.lifetimeStats_totTime), totTime);
        editor.putInt(getString(R.string.lifetimeStats_rideNumber), rideNumber);
        editor.putFloat(newRideDistance, distance.floatValue());
        editor.putLong(newRideTime, timelapse);

        editor.apply();

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
        if(location!=null) {
            setLastLocation(location);
            LatLng latLng =
                    new LatLng(getLastLocation().getLatitude(), getLastLocation().getLongitude());
            points.add(latLng);
            if (mMap.isMyLocationEnabled() == false && pauseToggle.isChecked()) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.setMaxZoomPreference(18);
                }
            }
            locationData.getOurInstance(this.getBaseContext()).addPoint(latLng, location);
            polylineOptions = locationData.getOurInstance(this.getBaseContext()).getPoints();
            LatLngBounds.Builder bounds = LocationData.getOurInstance(this.getBaseContext()).getBuilder();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds.build(), 100);
            if (animate) {
                animate = false;
                mMap.animateCamera(cu);
            } else mMap.moveCamera(cu);

            DecimalFormat df = new DecimalFormat("0.00");
            Polyline p = mMap.addPolyline(polylineOptions);
            String sd = df.format(location.getSpeed()*3.6);
            tvSpeed.setText(sd+" KM/H");
            sd = df.format(locationData.getOurInstance(this.getBaseContext()).getDistance()/1000);
            tvDistance.setText(sd+" KM");
        }
    }

    @Override
    public void userResults(String results) {

    }

    @Override
    public void setClient(GoogleApiClient googleApiClient) {
        LocationData.getOurInstance(this).setGoogleClient(googleApiClient);
    }

    @Override
    public GoogleApiClient getClient() {
        return LocationData.getOurInstance(this).getGoogleClient();
    }
}