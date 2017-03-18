package com.example.sam.duluthbikes;

import android.location.Location;

/**
 * Created by Sam on 3/18/2017.
 */

public interface ModelViewPresenterComponents {

    interface View {
        void notifyModelUpdated();
    }

    interface Presenter {
        ///
    }

    interface Model {
        Location getLocation();
        void setLocation(Location curr);
    }
}