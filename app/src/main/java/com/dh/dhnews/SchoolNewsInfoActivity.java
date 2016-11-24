package com.dh.dhnews;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dh.dhnews.utils.JsoupUtil;
import com.dh.dhnews.R;
import com.dh.dhnews.bean.News;
import com.dh.dhnews.bean.NewsTitle;

import java.net.URL;

/**
 * Created by 端辉 on 2016/3/12.
 */
public class SchoolNewsInfoActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_date;
    private TextView tv_content;
    private ProgressBar pb_load;
    private NewsTitle newsTitle;

    public static final String INTENT_SCHOOL_NEWS_INFO = "INTENT_SCHOOL_NEWS_INFO";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_news_info);
        initView();
        startTask();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_school_news_title);
        tv_date = (TextView) findViewById(R.id.tv_school_news_date);
        tv_content = (TextView) findViewById(R.id.tv_school_news_content);
        pb_load = (ProgressBar)findViewById(R.id.pb_school_news_load);
        newsTitle = (NewsTitle) getIntent().getSerializableExtra(INTENT_SCHOOL_NEWS_INFO);
        getSupportActionBar().setTitle(newsTitle.getNewsType());
    }

    private void startTask() {
        String url = newsTitle.getUrl();
        new MyAsyncTask().execute(url);
    }

    class MyAsyncTask extends AsyncTask<String, Void, News> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected News doInBackground(String... params) {
            return JsoupUtil.getOneNews(params[0]);
        }

        @Override
        protected void onPostExecute(News news) {
            super.onPostExecute(news);
            tv_title.setText(newsTitle.getTitle());
            tv_date.setText(newsTitle.getDate());
            new SecondAsyncTask().execute(news.getHtml());
        }
    }

    class SecondAsyncTask extends AsyncTask<String,Void,CharSequence>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb_load.setVisibility(View.VISIBLE);
        }

        @Override
        protected CharSequence doInBackground(String... params) {
            Html.ImageGetter imageGetter = new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    URL url;
                    Drawable drawable = null;
                    try {
                        url = new URL(source);
                        drawable = Drawable.createFromStream(
                                url.openStream(), null);
                        drawable.setBounds(64,0,600,300);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return drawable;
                }
            };
            CharSequence content = Html.fromHtml(params[0],imageGetter,null);
            return content;
        }

        @Override
        protected void onPostExecute(CharSequence charSequence) {
            super.onPostExecute(charSequence);
            pb_load.setVisibility(View.GONE);
            tv_content.setText(charSequence);
        }
    }
}
