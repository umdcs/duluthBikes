package com.example.sam.duluthbikes;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sam on 3/22/2017.
 */

public class ToursFragment extends Fragment implements ModelViewPresenterComponents.View {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_tours, container, false);
        return myView;
    }

    @Override
    public void locationChanged(Location location) {

    }
}
