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
    Float totDistance;
    Long totTime;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_user_tab, container, false);

        totalDist = (TextView)myView.findViewById(R.id.homeTotalDistance);
        totalTime = (TextView)myView.findViewById(R.id.homeTotalTime);
        initializeTotals();
        DecimalFormat df = new DecimalFormat("#.##");
        totalDist.setText(df.format(totDistance.doubleValue()/1000).toString() + " km");
        totalTime.setText(totTime.toString());

        return myView;
    }


    private void initializeTotals(){

        SharedPreferences totalstats = getActivity().getSharedPreferences(getString(R.string.lifetimeStats_file_key), 0);
        totDistance = totalstats.getFloat(getString(R.string.lifetimeStats_totDist), 0);
        totTime = totalstats.getLong(getString(R.string.lifetimeStats_totTime), 0);

    }
}
