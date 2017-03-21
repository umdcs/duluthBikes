package com.example.sam.duluthbikes;

import android.app.IntentService;
import android.content.Intent;
import android.location.Geocoder;

import java.util.Locale;

/**
 * This class is the address lookup service.
 * https://developer.android.com/training/location/display-address.html
 */

public class FetchAddressIntentService extends IntentService {

    //default constructor
    public FetchAddressIntentService(String name) {
        super(name);
    }

    /**
     * A locale represents a specific geographical or linguistic region.
     * Locale objects adjust the presentation of information,
     * such as numbers or dates, to suit the conventions in the region that is represented by the locale.
     * Pass a Locale object to the Geocoder object to ensure that the resulting address is localized to the user's geographic region.
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    }

}
