package com.example.sam.duluthbikes;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Activity to display events
 * Check: https://developer.android.com/guide/webapps/webview.html
 */

public class EventsFragment extends Fragment {

    View myView;
    WebView myWebView;
    private ProgressBar progressbar;
    String EventsPage = "http://www.duluthbikes.org/news-events/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_events, container, false);

        progressbar = (ProgressBar)myView.findViewById(R.id.ventilator_progress);

        myWebView = (WebView) myView.findViewById(R.id.webViewEvents);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new newWebClient(){
            @Override
            public void onPageFinished(WebView view,String url)
            {
                myWebView.loadUrl("javascript:(function() { " +
                        "document.getElementsByTagName('footer')[0].style.display='none'; " +
                        "document.getElementsByTagName('header')[0].style.display='none'; " +
                        "document.getElementById('main-content').style.marginTop='-5em';" +
                        "})()");
            }
        });
        //for progress bar
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressbar.setVisibility(View.VISIBLE);
                progressbar.setProgress(progress);
                if (progress == 100) {
                    progressbar.setVisibility(View.GONE); // Make the bar disappear after URL is loaded
                }
            }
        });


        progressbar.setVisibility(View.VISIBLE);

        myWebView.loadUrl(EventsPage);
        //this allows to navigate back in website using Back key
        myWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) view;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });


        return myView;
    }


    private class newWebClient extends WebViewClient {

        /**
        private ProgressBar progressBar;

        public newWebClient(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
        */

    }

}
