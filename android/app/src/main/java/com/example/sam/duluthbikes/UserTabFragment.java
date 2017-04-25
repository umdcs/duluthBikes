package com.example.sam.duluthbikes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Sam on 4/20/2017.
 */

public class UserTabFragment extends Fragment {

    View myView;
    TextView totalDist;
    TextView totalTime;
    TextView rideData;
    Float totDistance;
    Long totTime;
    int numberOfRides;
    UnitConverter converter;
    DecimalFormat df;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_user_tab, container, false);

        converter = new UnitConverter();

        totalDist = (TextView)myView.findViewById(R.id.homeTotalDistance);
        totalTime = (TextView)myView.findViewById(R.id.homeTotalTime);
        rideData = (TextView)myView.findViewById(R.id.allRideDataGoesHere);

        df = new DecimalFormat("#.##");

        initializeTotals();

        totalDist.setText(df.format(totDistance.doubleValue()/1000).toString() + " km");
        totalTime.setText(converter.convertHoursMinSecToString(totTime));

        retrieveRideData();

        return myView;
    }


    private void initializeTotals(){

        SharedPreferences totalstats = getActivity().getSharedPreferences(getString(R.string.lifetimeStats_file_key), 0);
        totDistance = totalstats.getFloat(getString(R.string.lifetimeStats_totDist), 0);
        totTime = totalstats.getLong(getString(R.string.lifetimeStats_totTime), 0);
        numberOfRides = totalstats.getInt(getString(R.string.lifetimeStats_rideNumber), 0);

    }

    private void retrieveRideData(){

        SharedPreferences totalstats = getActivity().getSharedPreferences(getString(R.string.lifetimeStats_file_key), 0);

        for (int num = numberOfRides; num >= 1;  num--){
            String rideTime = "ride" + num + "Time";
            String rideDist = "ride" + num + "Distance";

            Float rideD = totalstats.getFloat(rideDist, 0);
            Long rideT = totalstats.getLong(rideTime, 0);

            rideData.append("Ride " + num + ": \n" +
                    "Distance: " +
                    df.format(converter.getDistInKm(rideD.doubleValue())).toString() + " km" + "\n" +
                    "Time: " + converter.convertHoursMinSecToString(rideT) + "\n\n");
        }

    }
}
