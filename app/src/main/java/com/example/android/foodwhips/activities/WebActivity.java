package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.foodwhips.R;

public class WebActivity extends BaseActivity {
    final static String TAG = "WEBACTIVITY";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView) findViewById(R.id.webView);

        Bundle bundle = this.getIntent().getExtras();
        String sourceUrl = bundle.getString("source_url");

        webView(sourceUrl);
    }

    private void webView(String url){
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

    }

    @Override
    public void onBackPressed(){
        Log.v(TAG, "PRINT NUMBER OF BACKSTACK ENTRIES: " + getFragmentManager().getBackStackEntryCount());

        if(mWebView.canGoBack()){
            startActivity(new Intent(this, RecipeDetailsActivity.class));
        }
        else{
            super.onBackPressed();
        }
    }
}
