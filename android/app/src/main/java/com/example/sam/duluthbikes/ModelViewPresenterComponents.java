package com.example.sam.duluthbikes;

import android.location.Location;

import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Sam on 3/26/2017.
 */

public interface ModelViewPresenterComponents {

    interface View {

        void locationChanged(Location location);

        void userResults(String results);
    }

    interface PresenterContract {

        void updateMapLocation();

        void clickStart();

        void pauseRideButton();

        void finishRideButton();

        void connectApi();

        void notifyRoute(JSONArray fullRide,JSONArray l);

        void loginUser(String userName,String passWord);

        void returnLogin(String result);
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

        void loginAttempt(String user,String pass);
    }
}

