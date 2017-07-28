package com.example.android.foodwhips.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.android.foodwhips.R;

public class WebActivity extends BaseActivity {
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
        mWebView.getSettings().setDomStorageEnabled(true);
        //mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed(){
        if(getFragmentManager().getBackStackEntryCount() == 0){
            this.finish();
        }
        else{
            getFragmentManager().popBackStack();
        }
//        if(mWebView.canGoBack()){
//            //mWebView.goBack();
//            startActivity(new Intent(this, RecipeDetailsActivity.class));
//        }
//        else{
//            super.onBackPressed();
//        }
    }


}
