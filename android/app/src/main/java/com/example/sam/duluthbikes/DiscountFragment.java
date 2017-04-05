package com.example.sam.duluthbikes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * This fragment displays a webview with companies participating in Two Wheel Deal program
 * pulled from DuluthBikes website
 */

public class DiscountFragment extends Fragment {

    View myView;
    WebView myWebView;
    String DiscountPage = "http://www.duluthbikes.org/resources-and-partners/two-wheel-deals/";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activity_discount, container, false);

        myWebView = (WebView) myView.findViewById(R.id.webViewDiscount);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new newWebClient());
        myWebView.loadUrl(DiscountPage);
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

    }

}
