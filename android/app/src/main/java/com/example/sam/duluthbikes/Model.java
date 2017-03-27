package com.example.sam.duluthbikes;

import android.location.Location;

import java.io.IOException;

/**
 * Created by Sam on 3/26/2017.
 */

public class Model implements ModelViewPresenterComponents.Model {

    private ModelViewPresenterComponents.PresenterContract mPresenter;
    private Location mLastLocation;

    @Override
    public void notifyRouteUpdate() {

    }

    @Override
    public String getUserName(String filePath) throws IOException {
        return null;
    }

    @Override
    public void initializeUser() {

    }


    /**
     * Getter and Setter methods for Location
     */
    public Location getLocation() { return mLastLocation; }
    public void setLocation(Location curr) { mLastLocation = curr; }

}
