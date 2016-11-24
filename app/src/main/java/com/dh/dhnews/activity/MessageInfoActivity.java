package com.dh.dhnews.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dh.dhnews.R;
import com.dh.dhnews.bean.NewsTitle;
import com.dh.dhnews.utils.JsoupUtil;

/**
 * Created by 端辉 on 2016/3/17.
 */
public class MessageInfoActivity extends AppCompatActivity {

    private WebView mWebView;

    public static final String INTENT_MESSAGE_URL = "INTENT_MESSAGE_URL";

    private NewsTitle newsTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_info);
        initView();
    }

    private void initView(){
        mWebView = (WebView) findViewById(R.id.message_info_webview);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        newsTitle = (NewsTitle) getIntent().getSerializableExtra(INTENT_MESSAGE_URL);
        getSupportActionBar().setTitle(newsTitle.getNewsType());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        new GetHtmlTask().execute(newsTitle.getUrl());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class GetHtmlTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.message_info_progress).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("GetHtmlTask", params[0]);
            return JsoupUtil.getMassageInfo(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
       //     mWebView.loadData(s,"text/html","gb2312");
            mWebView.loadDataWithBaseURL(newsTitle.getBaseUrl(),s,"text/html","utf-8",null);
            findViewById(R.id.message_info_progress).setVisibility(View.GONE);
        }
    }

}

