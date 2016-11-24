package com.dh.dhnews.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.dh.dhnews.R;
import com.dh.dhnews.utils.HttpUtil;
import com.dh.dhnews.utils.JsoupUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 端辉 on 2016/3/18.
 */
public class ScheduleFragment extends Fragment {

    private AppCompatSpinner mSpinner;
    private WebView webView;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.schedule_progress);
        mSpinner = (AppCompatSpinner) v.findViewById(R.id.schedule_spinner);
        new SpinnerTask().execute();
        webView = (WebView) v.findViewById(R.id.schedule_webview);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        new ScheduleTask().execute("");
    }

    private void setAdapter(final List<List<String>> lls) {


        ArrayAdapter mAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, lls.get(1));
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new ScheduleTask().execute(lls.get(0).get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class SpinnerTask extends AsyncTask<Void, Void, List<List<String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSpinner.setVisibility(View.GONE);
        }

        @Override
        protected List<List<String>> doInBackground(Void... params) {
            return JsoupUtil.getScheduleOptions(HttpUtil.getScheduleContent(""));
        }

        @Override
        protected void onPostExecute(List<List<String>> lists) {
            super.onPostExecute(lists);
            setAdapter(lists);


        }
    }

    class ScheduleTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            return JsoupUtil.getSchedule(HttpUtil.getScheduleContent(params[0]));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            webView.loadDataWithBaseURL("", s, "text/html", "utf-8", "");
            mSpinner.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
