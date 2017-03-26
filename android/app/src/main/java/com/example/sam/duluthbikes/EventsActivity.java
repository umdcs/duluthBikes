package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Sam on 3/22/2017.
 */

public class EventsActivity extends AppCompatActivity implements ModelViewPresenterComponents.View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
    }

    @Override
    public void notifyUpdated() {

    }
}
