package com.example.sam.duluthbikes;

import android.content.Intent;
import android.content.SharedPreferences;
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

    public static final String TOTAL_DIST = "TotalDistance";

    Bundle data;
    int sec;
    int min;
    int hours;

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
        Double distance = data.getDouble("dis");

        //data format definitions
        //SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss"); //military time
        SimpleDateFormat timef = new SimpleDateFormat("hh:mm:ss a");
        SimpleDateFormat datef = new SimpleDateFormat("MM-dd-yyyy");
        DecimalFormat df = new DecimalFormat("#.###");

        Long timelapse = fTime - sTime;
        setSecMinHours(timelapse);

        //format data entries
        Double distKM = Double.valueOf(df.format(getDistInKm(distance)));
        Double averKmH = Double.valueOf(df.format(getKmPerHour(distance, timelapse)));
        String dateOfRide = datef.format(fTime);
        String timeFinish = timef.format(fTime);
        String timeStart = timef.format(sTime);

        rideDate.setText(dateOfRide);
        dist.setText(Double.toString(distKM));
        timeLapsed.setText(Integer.toString(hours)+"h "+Integer.toString(min)+"min "+Integer.toString(sec)+ "sec ");
        avSpeed.setText(Double.toString(averKmH));
        startTime.setText(timeStart);
        endTime.setText(timeFinish);


        // WORKING ON SHARED PREFERENCES - RUTA
        SharedPreferences totalstats = getSharedPreferences(TOTAL_DIST, 0);
        float totdist = totalstats.getFloat("totalDistance", 0);
        
        // = new FloatingDecimal(totdist.floatValue()).doubleValue());
        //totdist += (Float)distance;



    }

    public void setSecMinHours(Long timelapse){
        sec = (int) (timelapse / 1000) % 60 ;
        min = (int) ((timelapse / (1000*60)) % 60);
        hours = (int) ((timelapse / (1000*60*60)) % 24);
    }

    public double getDistInKm(Double distance){
        return distance/1000;
    }

    public double getDistInMiles(Double distance){
        return distance*0.000621371;
    }

    public double getKmPerHour(Double distance, Long timelapse){

        return (getDistInKm(distance)/(timelapse/1000))*3600;
    }

    public double getMilesPerHour(Double distance, Long timelapse){

        return (getDistInMiles(distance)/(timelapse/1000))*3600;
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

