package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Main Activity Class
 * Displays a map with your current location and allows to start tracking
 */

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private GoogleMap mMap;
    private int myRequestCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeText = (TextView) findViewById(R.id.lat);
        mLongitudeText = (TextView) findViewById(R.id.lon);

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
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
     * Required by OnMapReadyCallback interface
     * Called when the map is ready to be used.
     * https://developers.google.com/android/reference/com/google/android/gms/maps/OnMapReadyCallback
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    /**
     * Required by GoogleApiClient.ConnectionCallbacks interface
     * After calling connect(), this method will be invoked asynchronously when the connect request has successfully completed.
     * https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient.ConnectionCallbacks
     *
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
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            //put a market in the last location and zoom camera
            LatLng currlocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currlocation).title("Marker in current Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currlocation));
            //MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(this, R.raw.stylenight);
            //googleMap.setMapStyle(style);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
        }
    }

    /**
     *  Required by GoogleApiClient.ConnectionCallbacks interface
     *  https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient.ConnectionCallbacks
     *  Called when the client is temporarily in a disconnected state.
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

    /**
     * Required by OnConnectionFailedListener
     * https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient.OnConnectionFailedListener
     * Called when there was an error connecting the client to the service.
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //Called wby Start button
    public void displayMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //called by get coordinates button
    public void displayLocation(View view) {
        setLastLocation(mLastLocation);
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

}