package com.example.sam.duluthbikes;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sam on 3/22/2017.
 */

public class ToursActivity extends AppCompatActivity implements ModelViewPresenterComponents.View {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);
    }


    @Override
    public void locationChanged(Location location) {

    }
}
