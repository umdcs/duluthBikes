package com.example.sam.duluthbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * End of the Ride activity
 * Displays statistics of the ride and allows to return to the home screen
 */

public class EndRideActivity extends AppCompatActivity{

    Bundle data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_ride);

        TextView rideDate = (TextView)findViewById(R.id.dateLabel);
        TextView dist = (TextView)findViewById(R.id.distance);
        TextView timeLapsed = (TextView) findViewById(R.id.timeLapsed);
        TextView avSpeed = (TextView)findViewById(R.id.averageSpeed);
        TextView startTime = (TextView)findViewById(R.id.startTime);
        TextView endTime = (TextView)findViewById(R.id.endTime);

        data = getIntent().getExtras();
        Long sTime =  data.getLong("startTime");
        Long fTime = data.getLong("endTime");

        //retrieve current time and format start and current time
        //Date fDate = new Date();

        //data format definitions
        //SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss"); //military time
        SimpleDateFormat timef = new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat datef = new SimpleDateFormat("MM-dd-yyyy");
        DecimalFormat df = new DecimalFormat("#.###");

        Long timelapse = fTime - sTime;

        int sec = (int) (timelapse / 1000) % 60 ;
        int min = (int) ((timelapse / (1000*60)) % 60);
        int hours = (int) ((timelapse / (1000*60*60)) % 24);

        Double distance = data.getDouble("dis");
        distance = distance/1000;
        Double average = (distance/(timelapse/1000))*3600;

        //format data entries
        Double distKM = Double.valueOf(df.format(distance));
        Double averKmH = Double.valueOf(df.format(average));
        String dateOfRide = datef.format(fTime);
        String timeFinish = timef.format(fTime);
        String timeStart = timef.format(sTime);

        rideDate.setText(dateOfRide);
        dist.setText(Double.toString(distKM));
        timeLapsed.setText(Integer.toString(hours)+"h "+Integer.toString(min)+"min "+Integer.toString(sec)+ "sec ");
        avSpeed.setText(Double.toString(averKmH));
        startTime.setText(timeStart);
        endTime.setText(timeFinish);

    }

    public void doneWithRide(View view){
        Intent menu = new Intent(this.getApplicationContext(),MenuActivity.class);
        startActivity(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}

