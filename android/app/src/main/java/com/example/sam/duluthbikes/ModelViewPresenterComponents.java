package com.example.sam.duluthbikes;

import android.location.Location;

import java.io.IOException;

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
        
        public void endRideButton();

        public void connectApi();
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

        void connectApiOnResume();
    }
}

