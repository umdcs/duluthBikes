package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Sam on 4/20/2017.
 */

public class PolyTabFragment extends Fragment {

    View myView;
    WebView myWebView;
    String PolylinePage = "http://ukko.d.umn.edu:23405/rides/";
    ProgressBar pb;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_poly_tab, container, false);
        myWebView = (WebView) myView.findViewById(R.id.webViewPolylineTab);
        myWebView.getSettings().setJavaScriptEnabled(true);
        pb = (ProgressBar) myView.findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);

        myWebView.setWebViewClient(new newWebClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                pb.setVisibility(View.INVISIBLE);
            }
        });
        myWebView.loadUrl(PolylinePage);
        return myView;
    }
    private class newWebClient extends WebViewClient { }
}