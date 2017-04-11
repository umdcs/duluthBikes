package com.example.sam.duluthbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


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
        TextView startTime = (TextView)findViewById(R.id.startTime);
        TextView endTime = (TextView)findViewById(R.id.endTime);
        TextView timeLapsed = (TextView) findViewById(R.id.timeLapsed);

        data = getIntent().getExtras();

        tv.setText(Double.toString(data.getDouble("dis")));

        //retrieve current time and format start and current time
        Date fDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedEnd = df.format(fDate.getTime());
        String formattedStart = df.format(data.getString("startTime"));

        endTime.setText(formattedEnd);
        startTime.setText(formattedStart);

        Long sTime =  data.getLong("startTime");
        Long fTime = fDate.getTime();
        Long timelapse = fTime - sTime;

        timeLapsed.setText(Long.toString(timelapse));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

