package com.example.sam.duluthbikes;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;

/**
 * Created by Sam on 3/26/2017.
 */

public interface ModelViewPresenterComponents {

    interface View {

        void locationChanged(Location location);

        void userResults(String results);

        void setClient(GoogleApiClient googleApiClient);

        GoogleApiClient getClient();
    }

    interface PresenterContract {

        Location getLocationForCamera();

        void updateMapLocation();

        void clickStart();

        void pauseRideButton();

        void finishRideButton();

        void connectApi();

        void notifyRoute(JSONArray fullRide,JSONArray l);

        void loginUser(String userName,String passWord);

        void sendPictureToServer(String loc, String description, String encodedImage);

        void returnLogin(String result);

        void setOurClient(GoogleApiClient googleApiClient);

        GoogleApiClient getOurClient();
    }

    interface Model {

        //Set Location
        void setLocation(Location curr);

        //Get Location
        Location getLocation();


        void stopLocationUpdates();

        void disconnectApiOnFinish();

        void connectApiOnResume();

        void notifyFinishRoute(JSONArray r,JSONArray l);

        void loginAttempt(String user,String pass);

        void sendPicture(String loc, String description, String encodedImage);
    }
}

