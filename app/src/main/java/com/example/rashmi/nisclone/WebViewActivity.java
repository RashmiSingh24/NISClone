package com.example.rashmi.nisclone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    WebView myWebViewActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");

        myWebViewActivity =(WebView)findViewById(R.id.webview);
        myWebViewActivity.setWebViewClient(new MyBrowser());
        myWebViewActivity.loadUrl(url);
        WebSettings webSettings=myWebViewActivity.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= 19) {
            myWebViewActivity.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}

