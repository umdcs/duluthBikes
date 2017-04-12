package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Ruta on 11.04.2017.
 */

public class ReportAppFragment extends Fragment {

    View myView;
    TextView feedbackLink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_reportapp, container, false);

        feedbackLink = (TextView) myView.findViewById(R.id.feedbackFormLink);
        feedbackLink.setMovementMethod(LinkMovementMethod.getInstance());

        return myView;
    }
}
