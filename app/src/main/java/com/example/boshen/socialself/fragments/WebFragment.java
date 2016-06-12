package com.example.boshen.socialself.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.boshen.socialself.R;


public class WebFragment extends Fragment {
    private WebView wv;
    private View rootview;
    private String url = "https://m.google.com";

    public WebFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_web,container,false);
        super.onActivityCreated(savedInstanceState);

        wv=(WebView) rootview.findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);

        //loading webpages
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;


        wv.setWebViewClient(new WebClient());
        wv.setWebChromeClient(new ChromeClient());

        url=getArguments().getString("url");
        wv.loadUrl(url);

        ViewGroup.LayoutParams wv_params = wv.getLayoutParams();
        wv_params.width = width;
        wv.requestLayout();

        return rootview;
    }



    private class ChromeClient extends WebChromeClient {

    }
    private class WebClient extends WebViewClient {

    }
}


