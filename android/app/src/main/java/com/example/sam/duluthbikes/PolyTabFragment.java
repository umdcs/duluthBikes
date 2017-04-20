package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Sam on 4/20/2017.
 */

public class PolyTabFragment extends Fragment {

    View myView;
    WebView myWebView = null;
    String PolylinePage = "http://ukko.d.umn.edu:23405/rides/";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_poly_tab, container, false);
        return myView;
    }
}


