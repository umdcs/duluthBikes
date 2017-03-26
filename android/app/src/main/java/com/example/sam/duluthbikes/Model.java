package com.example.sam.duluthbikes;

import android.location.Location;

/**
 * Created by Sam on 3/26/2017.
 */

public class Model implements ModelViewPresenterComponents.Model{

    private ModelViewPresenterComponents.PresenterContract mPresenter;
    private Location mLastLocation;


    /**
     * Getter and Setter methods for Location
     */
    public Location getLocation() { return mLastLocation; };
    public void setLocation(Location curr) { mLastLocation = curr; }

}
