package com.example.sam.duluthbikes;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sam on 3/26/2017.
 */

public interface ModelViewPresenterComponents {

    interface View {

        public void locationChanged(Location location);
    }

    interface PresenterContract {

        public void updateMapLocation();

        public void clickStart();
        
        public void pauseRideButton();

        public void finishRideButton();

        public void connectApi();

        public void notifyRoute(JSONArray fullRide,JSONArray l);
    }

    interface Model {

        //Set Location
        void setLocation(Location curr);

        //Get Location
        Location getLocation();

        //Notify Route Update
        void notifyRouteUpdate();

        //Gets User name
        String getUserName (String filePath) throws IOException;

        //Initializes user
        void initializeUser();

        void stopLocationUpdates();

        void disconnectApiOnFinish();

        void connectApiOnResume();

        void notifyFinishRoute(JSONArray r,JSONArray l);
    }
}

