package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.TextViewCompat;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import android.location.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Main Activity Class
 */

public class MainActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener{

    Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView mLatitudeText;
    private TextView mLongitudeText;

    private Button button1;
    private int myRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Clark
       // mLatitudeText = (EditText) findViewById(R.id.lat);
       // button1 = (Button) findViewById(R.id.button1);

        mLatitudeText = (TextView) findViewById(R.id.lat);
        mLongitudeText = (TextView) findViewById(R.id.lon);

        button1 = (Button) findViewById(R.id.displayCoords);

        // Create an instance of GoogleAPIClient
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    public void displayMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    // connect to mGoogleAPIClient on start
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    //disconnect from mGoogleApiClient on Stop
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    /**
     * Get the last known location of a user's device
     * https://developer.android.com/training/location/retrieve-current.html
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        //check if the app is allowed to access location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // request permission to access location
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    myRequestCode);
            return;
        }

        // get the last location
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        //save the last location's latitude and longitude in a string
        if (mLastLocation != null) {
            String lat;
            lat = String.valueOf(mLastLocation.getLatitude());
            lat += (String.valueOf(mLastLocation.getLongitude()));
            mLatitudeText.setText(lat);
        }
    }
    /** Sams code
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                myRequestCode);
        return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }
    */

    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Creates the location request and sets parameters
     * https://developer.android.com/training/location/change-location-settings.html
     */
    protected void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }



    public Location getLastLocation(){ return mLastLocation; }
    public void setLastLocation(Location curr) { mLastLocation = curr; }
    public void displayLocation() { setLastLocation(mLastLocation); }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

