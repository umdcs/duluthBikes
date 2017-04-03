package com.example.sam.duluthbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Sam on 3/29/2017.
 */

public class EndRideActivity extends AppCompatActivity{

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_ride);

        TextView tv = (TextView)findViewById(R.id.distance);
        data = getIntent().getExtras();
        tv.setText(Double.toString(data.getDouble("dis")));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

