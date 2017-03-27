package com.example.sam.duluthbikes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Main Activity Class
 * Displays a map with your current location and allows to start tracking
 */

public class MainActivity extends FragmentActivity
        implements OnMapReadyCallback,ModelViewPresenterComponents.View {


    private Presenter mPresenter;
    public static String userName = null;
    private Location mLastLocation;
    private TextView mLatitudeText;
    private TextView mLongitudeText;
    private GoogleMap mMap;
    private int myRequestCode;
    private ArrayList<LatLng> points;
    private PolylineOptions polylineOptions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeText = (TextView) findViewById(R.id.lat);
        mLongitudeText = (TextView) findViewById(R.id.lon);

        mPresenter = new Presenter(this.getApplicationContext(),this,this);
        mPresenter.clickStart();

        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }


    // connect to mGoogleAPIClient on start
    protected void onStart() {


        super.onStart();
    }

    //disconnect from mGoogleApiClient on Stop
    protected void onStop() {

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
    }



    //Called by Start button
    public void displayMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    //called by get coordinates button
    public void displayLocation(View view) {
        setLastLocation(mLastLocation);
    }

    public Location getLastLocation(){ return mLastLocation; }

    public void setLastLocation(Location curr) { mLastLocation = curr; }
    /**
     * Gets the account username from file.
     * @param filePath The filepath of the file the username is stored on
     * @return username The username of the account
     * @throws IOException Just in case there's an issue with the buffered reader
     */
    public String getUserName (String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fin = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        sb.append(line);
        String username = sb.toString();
        reader.close();
        fin.close();

        return username;
    }

    /**
     * Checks if a user account exists, otherwise starts the CreateAccount activity.
     */
    public void initializeUser() {
        File file = this.getFileStreamPath("account.txt");
        if(file == null || !file.exists()) {
            Intent createAccount = new Intent(this, CreateAccountActivity.class);
            startActivity(createAccount);
        }
        else {
            try {
                userName = getUserName(file.toString());
                Log.d("username on main", userName);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void locationChanged(Location location) {
        setLastLocation(location);
        LatLng latLng =
                new LatLng(getLastLocation().getLatitude(),getLastLocation().getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.0f));
    }
}