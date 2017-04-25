package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sam on 3/22/2017.
 */

public class AboutFragment extends Fragment {

    View myView;
    TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_about, container, false);

        description = (TextView) myView.findViewById(R.id.aboutDescription);
        description.setText(String.format(getString(R.string.About1)) + "\n\n" + String.format(getString(R.string.About2)) + "\n" +
        String.format(getString(R.string.About3))+ "\n\n"+
        String.format(getString(R.string.About4))+"\n" + String.format(getString(R.string.About5)) + "\n\n"+
        String.format(getString(R.string.About6))+"\n" + String.format(getString(R.string.About7)) + "\n\n"+
        String.format(getString(R.string.About8))+"\n" + String.format(getString(R.string.About9)) + "\n\n"+
        String.format(getString(R.string.About10))+"\n" + String.format(getString(R.string.About11)) + "\n\n"+
        String.format(getString(R.string.About12))+"\n" + String.format(getString(R.string.About13))
        );

        return myView;
    }
}
