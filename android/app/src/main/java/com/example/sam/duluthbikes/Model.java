package com.example.sam.duluthbikes;

import android.location.Location;

/**
 * Created by Sam on 3/18/2017.
 */

public class Model {

    private Location mLastLocation;




    Location getLocation(){ return mLastLocation; }

    void setLocation(Location curr) { mLastLocation = curr; }

}
